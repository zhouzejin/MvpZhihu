package com.sunny.mvpzhihu.ui.dailydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.entity.StoryEntity;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.utils.HtmlUtil;
import com.sunny.mvpzhihu.utils.LogUtil;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;
import com.sunny.mvpzhihu.widget.CircleProgressView;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * The type Daily detail activity.
 * Created by Zhou Zejin on 2017/5/18.
 */
public class DailyDetailActivity extends BaseActivity {

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

    @Inject
    @ActivityContext
    Context mContext;
    @Inject
    DataManager mDataManager;
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
    public void initViews(Bundle savedInstanceState) {
        activityComponent().inject(this);
        loadDailyDetail(getIntent().getIntExtra(EXTRA_DAILY_ID, -1));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void loadDailyDetail(int dailyId) {
        mDataManager.getDailyDetail(dailyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new Subscriber<StoryEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载Daily Detail数据出错。");
                        hideProgress();
                    }

                    @Override
                    public void onNext(StoryEntity storyEntity) {
                        hideProgress();
                        showDailyDetail(storyEntity);
                    }
                });
    }

    private void showProgress() {
        mCircleProgressDetail.setVisibility(View.VISIBLE);
        mCircleProgressDetail.spin();
    }

    private void hideProgress() {
        mCircleProgressDetail.setVisibility(View.INVISIBLE);
        mCircleProgressDetail.stopSpinning();
    }

    private void showDailyDetail(StoryEntity story) {
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, mIvImage, story.image(), option);
        mTvTitle.setText(story.title());
        mTvSource.setText(story.image_source());
        String htmlData = HtmlUtil.createHtmlData(story.body(),
                HtmlUtil.createCssTag(story.css()), HtmlUtil.createJsTag(story.js()));
        mWebViewDetail.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

}
