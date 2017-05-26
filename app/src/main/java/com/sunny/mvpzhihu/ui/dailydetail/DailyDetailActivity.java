package com.sunny.mvpzhihu.ui.dailydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.entity.StoryEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryExtraEntity;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.utils.HtmlUtil;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;
import com.sunny.mvpzhihu.widget.CircleProgressView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * The type Daily detail activity.
 * Created by Zhou Zejin on 2017/5/18.
 */
public class DailyDetailActivity extends BaseActivity implements DailyDetailMvpView {

    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_source)
    TextView mTvSource;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view_detail)
    WebView mWebViewDetail;
    @BindView(R.id.circle_progress_detail)
    CircleProgressView mCircleProgressDetail;

    MenuItem mMenuComment;
    MenuItem mMenuPraise;

    @Inject
    @ActivityContext
    Context mContext;
    @Inject
    DailyDetailPresenter mDailyDetailPresenter;
    @Inject
    ImageLoader mImageLoader;

    private static final String EXTRA_DAILY_ID = "daily_id";

    /**
     * Return an Intent to start this Activity.
     */
    public static Intent getStartIntent(Context context, int dailyId) {
        Intent intent = new Intent(context, DailyDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_DAILY_ID, dailyId);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_detail;
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDailyDetailPresenter.detachView();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        activityComponent().inject(this);
        setSwipeBackEnable(true);
        mDailyDetailPresenter.attachView(this);

        showSwipeBackHint();
        int dailyId = getIntent().getIntExtra(EXTRA_DAILY_ID, -1);
        mDailyDetailPresenter.loadDailyDetail(dailyId);
        mDailyDetailPresenter.loadDailyExtra(dailyId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily_detail, menu);
        mMenuComment = menu.findItem(R.id.action_comment);
        mMenuPraise = menu.findItem(R.id.action_praise);
        mMenuComment.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(mMenuComment);
            }
        });
        mMenuPraise.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(mMenuComment);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showProgress() {
        mCircleProgressDetail.setVisibility(View.VISIBLE);
        mCircleProgressDetail.spin();
    }

    @Override
    public void hideProgress() {
        mCircleProgressDetail.setVisibility(View.INVISIBLE);
        mCircleProgressDetail.stopSpinning();
    }

    @Override
    public void showDailyDetail(StoryEntity story) {
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, mIvImage, story.image(), option);
        mTvTitle.setText(story.title());
        mTvSource.setText(story.image_source());
        String htmlData = HtmlUtil.createHtmlData(story.body(),
                HtmlUtil.createCssTag(story.css()), HtmlUtil.createJsTag(story.js()));
        mWebViewDetail.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showDailyExtra(StoryExtraEntity storyExtra) {
        TextView tvComment = (TextView) mMenuComment.getActionView();
        TextView tvPraise = (TextView) mMenuPraise.getActionView();
        tvComment.setText(String.valueOf(storyExtra.comments()));
        tvPraise.setText(String.valueOf(storyExtra.popularity()));
    }

    @Override
    public void showSwipeBackHint() {
        if (mDailyDetailPresenter.getIsShowSwipeBackHint()) {
            Snackbar.make(mToolbar, R.string.swipe_back_hint, Snackbar.LENGTH_LONG).show();
        }
    }

}
