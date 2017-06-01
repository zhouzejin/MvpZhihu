package com.sunny.mvpzhihu.ui.recommenders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Editor;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.widget.CircleProgressView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * The type Recommenders activity.
 * Created by Zhou Zejin on 2017/5/31.
 */
public class RecommendersActivity extends BaseActivity implements RecommendersMvpView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_recommenders)
    RecyclerView mRecyclerRecommenders;
    @BindView(R.id.circle_progress_recommenders)
    CircleProgressView mCircleProgressRecommenders;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;

    @Inject
    @ActivityContext
    Context mContext;
    @Inject
    RecommendersPresenter mRecommendersPresenter;
    @Inject
    RecommendersAdapter mRecommendersAdapter;

    private static final String EXTRA_DAILY_ID = "daily_id";

    /**
     * Return an Intent to start this Activity.
     */
    public static Intent getStartIntent(Context context, int dailyId) {
        Intent intent = new Intent(context, RecommendersActivity.class);
        intent.putExtra(EXTRA_DAILY_ID, dailyId);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recommenders;
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(R.string.daily_recommenders);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        activityComponent().inject(this);
        setSwipeBackEnable(true);
        mRecommendersPresenter.attachView(this);

        mRecyclerRecommenders.setHasFixedSize(true);
        mRecyclerRecommenders.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerRecommenders.setAdapter(mRecommendersAdapter);

        int dailyId = getIntent().getIntExtra(EXTRA_DAILY_ID, -1);
        mRecommendersPresenter.loadEditors(dailyId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecommendersPresenter.detachView();
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showProgress() {
        mCircleProgressRecommenders.setVisibility(View.VISIBLE);
        mCircleProgressRecommenders.spin();
        mRecyclerRecommenders.setVisibility(View.GONE);
        mTvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mCircleProgressRecommenders.setVisibility(View.GONE);
        mCircleProgressRecommenders.stopSpinning();
        mRecyclerRecommenders.setVisibility(View.VISIBLE);
        mTvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEditorsEmpty() {
        mRecommendersAdapter.clearData();
        mRecommendersAdapter.notifyDataSetChanged();
        mTvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEditors(List<Editor> editors) {
        mRecommendersAdapter.addData(editors);
        mRecommendersAdapter.notifyDataSetChanged();
        mTvEmpty.setVisibility(View.GONE);
    }

}
