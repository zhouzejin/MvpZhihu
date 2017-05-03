package com.sunny.mvpzhihu.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.ui.base.BaseFragment;
import com.sunny.mvpzhihu.widget.CircleProgressView;

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

            }
        };
        mRecyclerDaily.setHasFixedSize(true);
        mRecyclerDaily.setLayoutManager(mLinearLayoutManager);
        mRecyclerDaily.addOnScrollListener(mAutoLoadOnScrollListener);
        mRecyclerDaily.setAdapter(mDailyAdapter);
        mDailyPresenter.loadDailies(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDailyPresenter.detachView();
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
    }

}
