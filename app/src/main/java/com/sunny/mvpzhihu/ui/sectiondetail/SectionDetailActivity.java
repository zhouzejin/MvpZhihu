package com.sunny.mvpzhihu.ui.sectiondetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Section;
import com.sunny.mvpzhihu.data.model.bean.SectionStory;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.ui.dailydetail.DailyDetailActivity;
import com.sunny.mvpzhihu.ui.base.AutoLoadOnScrollListener;
import com.sunny.mvpzhihu.widget.CircleProgressView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * The type Section detail activity.
 * Created by Zhou Zejin on 2017/6/12.
 */
public class SectionDetailActivity extends BaseActivity implements SectionDetailMvpView {

    private static final String EXTRA_SECTION = "section";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_section_daily)
    RecyclerView mRecyclerSectionDaily;
    @BindView(R.id.circle_progress_section_detail)
    CircleProgressView mCircleProgressSectionDetail;

    @Inject
    @ActivityContext
    Context mContext;
    @Inject
    SectionDetailPresenter mSectionDetailPresenter;
    @Inject
    SectionDailyAdapter mSectionDailyAdapter;

    private AutoLoadOnScrollListener mAutoLoadOnScrollListener;

    private Section mSection;

    /**
     * Return an Intent to start this Activity.
     */
    public static Intent getStartIntent(Context context, Section section) {
        Intent intent = new Intent(context, SectionDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_SECTION, section);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_section_detail;
    }

    @Override
    public void initToolBar() {
        mSection = getIntent().getParcelableExtra(EXTRA_SECTION);

        mToolbar.setTitle(mSection.name());
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
        mSectionDetailPresenter.attachView(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mSectionDetailPresenter.loadMoreSectionDetail(mSection.id());
            }
        };
        mRecyclerSectionDaily.addOnScrollListener(mAutoLoadOnScrollListener);
        mRecyclerSectionDaily.setHasFixedSize(true);
        mRecyclerSectionDaily.setLayoutManager(layoutManager);
        mSectionDailyAdapter.setItemListener(new SectionDailyAdapter.SectionDailyItemListener() {
            @Override
            public void onItemClick(SectionStory sectionStory) {
                startActivity(DailyDetailActivity.getStartIntent(mContext, sectionStory.id()));
            }
        });
        mRecyclerSectionDaily.setAdapter(mSectionDailyAdapter);

        mSectionDetailPresenter.loadSectionDetail(mSection.id());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSectionDetailPresenter.detachView();
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showProgress() {
        mCircleProgressSectionDetail.setVisibility(View.VISIBLE);
        mCircleProgressSectionDetail.spin();
        mRecyclerSectionDaily.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mCircleProgressSectionDetail.setVisibility(View.GONE);
        mCircleProgressSectionDetail.stopSpinning();
        mRecyclerSectionDaily.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSectionDetail(List<SectionStory> sectionStories) {
        mSectionDailyAdapter.addData(sectionStories);
        mSectionDailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRecyclerScrollLoading(boolean isLoading) {
        mAutoLoadOnScrollListener.setLoading(isLoading);
    }

}
