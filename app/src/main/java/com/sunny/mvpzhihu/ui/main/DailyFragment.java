package com.sunny.mvpzhihu.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseFragment;
import com.sunny.mvpzhihu.widget.CircleProgressView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * The type Daily fragment.
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDailyPresenter.detachView();
    }

    /*****
     * MVP View methods implementation
     *****/

}
