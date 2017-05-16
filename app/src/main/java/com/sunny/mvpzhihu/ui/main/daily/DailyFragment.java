package com.sunny.mvpzhihu.ui.main.daily;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.ui.base.BaseFragment;
import com.sunny.mvpzhihu.ui.main.AutoLoadOnScrollListener;
import com.sunny.mvpzhihu.widget.CircleProgressView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * The type Story fragment.
 * Created by Zhou Zejin on 2017/4/24.
 */
public class DailyFragment extends BaseFragment implements DailyMvpView {

    @BindView(R.id.recycler_daily)
    RecyclerView mRecyclerDaily;
    @BindView(R.id.circle_progress_daily)
    CircleProgressView mCircleProgressDaily;
    @BindView(R.id.swipe_refresh_daily)
    SwipeRefreshLayout mSwipeRefreshDaily;

    @Inject
    DailyPresenter mDailyPresenter;
    @Inject
    DailyAdapter mDailyAdapter;
    @Inject
    @FragmentContext
    Context mContext;

    private LinearLayoutManager mLinearLayoutManager;
    private AutoLoadOnScrollListener mAutoLoadOnScrollListener;

    private final Handler mHandler = new MyHandler(this);

    private static final int MSG_ID_START_REFRESH = 0;
    private static final int MSG_ID_LOAD_OVER = 1;

    public DailyFragment() {
        // Requires empty public constructor
    }

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject instance for fragment
        fragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mDailyPresenter.attachView(this);

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mDailyPresenter.loadMoreDailies();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 根据Scroll位置决定是否Enable刷新控件
                boolean isEnabled =
                        mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
                mSwipeRefreshDaily.setEnabled(isEnabled);
            }
        };
        mRecyclerDaily.setHasFixedSize(true);
        mRecyclerDaily.setLayoutManager(mLinearLayoutManager);
        mRecyclerDaily.addOnScrollListener(mAutoLoadOnScrollListener);

        mDailyAdapter.setItemListener(new DailyAdapter.DailyItemListener() {
            @Override
            public void onDailyClick(DailyModel model) {
                mDailyPresenter.openDailyDetail(model);
            }
        });
        mRecyclerDaily.setAdapter(mDailyAdapter);

        mSwipeRefreshDaily.setColorSchemeResources(R.color.primary);
        mSwipeRefreshDaily.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(MSG_ID_START_REFRESH, 1000);
            }
        });

        mDailyPresenter.loadDailies(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDailyPresenter.detachView();
    }

    // 使用静态内部类，避免直接引用OuterClass
    private final static class MyHandler extends Handler {
        // 使用弱引用，避免Handler阻止Fragment被回收，造成内存泄露
        private final WeakReference<DailyFragment> mFragmentWeakReference;

        MyHandler(DailyFragment fragment) {
            mFragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mFragmentWeakReference.get() == null) {
                // 引用被回收
                return;
            }

            if (msg.what == MSG_ID_START_REFRESH) {
                mFragmentWeakReference.get().mDailyPresenter.loadDailies(true);
            } else if (msg.what == MSG_ID_LOAD_OVER) {
                mFragmentWeakReference.get().hideProgress();
            }
        }
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showDailies(List<DailyModel> dailyModels) {
        mDailyAdapter.addData(dailyModels);
        mDailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDailiesEmpty() {
        mDailyAdapter.clearData();
        mDailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        mCircleProgressDaily.setVisibility(View.VISIBLE);
        mCircleProgressDaily.spin();
        mRecyclerDaily.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mCircleProgressDaily.setVisibility(View.GONE);
        mCircleProgressDaily.stopSpinning();
        mRecyclerDaily.setVisibility(View.VISIBLE);
        mSwipeRefreshDaily.setRefreshing(false);
    }

    @Override
    public void showTaskDetail(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRecyclerScrollLoading(boolean isLoading) {
        mAutoLoadOnScrollListener.setLoading(isLoading);
    }

    @Override
    public void loadDailiesOver() {
        mHandler.sendEmptyMessageDelayed(MSG_ID_LOAD_OVER, 2000);
    }

}
