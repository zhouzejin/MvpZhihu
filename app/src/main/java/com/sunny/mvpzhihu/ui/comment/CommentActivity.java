package com.sunny.mvpzhihu.ui.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.entity.StoryExtraEntity;
import com.sunny.mvpzhihu.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * The type Comment activity.
 * Created by Zhou Zejin on 2017/6/1.
 */
public class CommentActivity extends BaseActivity {

    private static final String EXTRA_DAILY_ID = "daily_id";
    private static final String EXTRA_DAILY_EXTRA = "daily_extra";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private int mDailyId;
    private StoryExtraEntity mStoryExtra;

    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    /**
     * Return an Intent to start this Activity.
     */
    public static Intent getStartIntent(Context context, int dailyId, StoryExtraEntity storyExtra) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(EXTRA_DAILY_ID, dailyId);
        intent.putExtra(EXTRA_DAILY_EXTRA, storyExtra);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void initToolBar() {
        mStoryExtra = getIntent().getParcelableExtra(EXTRA_DAILY_EXTRA);
        mToolbar.setTitle(getString(R.string.comment_num, mStoryExtra.comments()));
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
        setSwipeBackEnable(true);

        mDailyId = getIntent().getIntExtra(EXTRA_DAILY_ID, -1);
        mTitles.add(getString(R.string.long_comment_num, mStoryExtra.long_comments()));
        mTitles.add(getString(R.string.short_comment_num, mStoryExtra.short_comments()));

        CommentPagerAdapter pagerAdapter = new CommentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private final class CommentPagerAdapter extends FragmentStatePagerAdapter {

        public CommentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
