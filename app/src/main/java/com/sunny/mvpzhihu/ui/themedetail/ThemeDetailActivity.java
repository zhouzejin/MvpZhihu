package com.sunny.mvpzhihu.ui.themedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Story;
import com.sunny.mvpzhihu.data.model.bean.Theme;
import com.sunny.mvpzhihu.data.model.entity.ThemeDetailEntity;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.ui.base.HeaderAndFooterWrappedAdapter;
import com.sunny.mvpzhihu.ui.dailydetail.DailyDetailActivity;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;
import com.sunny.mvpzhihu.widget.CircleProgressView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * The type Theme detail activity.
 * Created by Zhou Zejin on 2017/6/7.
 */
public class ThemeDetailActivity extends BaseActivity implements ThemeDetailMvpView {

    private static final String EXTRA_THEME = "theme";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_theme_daily)
    RecyclerView mRecyclerThemeDaily;
    @BindView(R.id.circle_progress_theme_detail)
    CircleProgressView mCircleProgressThemeDetail;

    ImageView mIvImage;
    TextView mTvDescription;
    RecyclerView mRecyclerThemeEditor;

    @Inject
    @ActivityContext
    Context mContext;
    @Inject
    ThemeDetailPresenter mThemeDetailPresenter;
    @Inject
    ThemeDailyAdapter mThemeDailyAdapter;
    @Inject
    ThemeEditorAdapter mThemeEditorAdapter;
    @Inject
    ImageLoader mImageLoader;

    private Theme mTheme;

    /**
     * Return an Intent to start this Activity.
     */
    public static Intent getStartIntent(Context context, Theme theme) {
        Intent intent = new Intent(context, ThemeDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_THEME, theme);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_theme_detail;
    }

    @Override
    public void initToolBar() {
        mTheme = getIntent().getParcelableExtra(EXTRA_THEME);

        mToolbar.setTitle(mTheme.name());
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
        mThemeDetailPresenter.attachView(this);

        mRecyclerThemeDaily.setHasFixedSize(true);
        mRecyclerThemeDaily.setLayoutManager(new LinearLayoutManager(mContext));
        initAdapter();
        mThemeDetailPresenter.loadThemeDetail(mTheme.id());
    }

    private void initAdapter() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View headerImage = inflater.inflate(R.layout.layout_header_theme_image, mRecyclerThemeDaily, false);
        mIvImage = (ImageView) headerImage.findViewById(R.id.iv_theme_detail_image);
        mTvDescription = (TextView) headerImage.findViewById(R.id.tv_theme_detail_description);
        View headerEditor = inflater.inflate(R.layout.layout_header_theme_editor, mRecyclerThemeDaily, false);
        mRecyclerThemeEditor = (RecyclerView) headerEditor.findViewById(R.id.recycler_theme_editor);

        mRecyclerThemeEditor.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerThemeEditor.setLayoutManager(layoutManager);

        mRecyclerThemeEditor.setAdapter(mThemeEditorAdapter);
        mThemeDailyAdapter.setThemeDailyListener(new ThemeDailyAdapter.ThemeDailyListener() {
            @Override
            public void onThemeDailyClick(Story story) {
                startActivity(DailyDetailActivity.getStartIntent(mContext, story.id()));
            }
        });
        HeaderAndFooterWrappedAdapter wrappedAdapter = new HeaderAndFooterWrappedAdapter(mThemeDailyAdapter);
        wrappedAdapter.addHeaderView(headerImage);
        wrappedAdapter.addHeaderView(headerEditor);
        mRecyclerThemeDaily.setAdapter(wrappedAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThemeDetailPresenter.detachView();
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showProgress() {
        mCircleProgressThemeDetail.setVisibility(View.VISIBLE);
        mCircleProgressThemeDetail.spin();
        mRecyclerThemeDaily.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mCircleProgressThemeDetail.setVisibility(View.GONE);
        mCircleProgressThemeDetail.stopSpinning();
        mRecyclerThemeDaily.setVisibility(View.VISIBLE);
    }

    @Override
    public void showThemeDetail(ThemeDetailEntity themeDetailEntity) {
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, mIvImage, themeDetailEntity.background(), option);
        mTvDescription.setText(themeDetailEntity.description());

        mThemeEditorAdapter.addData(themeDetailEntity.editors());
        mThemeEditorAdapter.notifyDataSetChanged();
        mThemeDailyAdapter.addData(themeDetailEntity.stories());
        mThemeDailyAdapter.notifyDataSetChanged();
    }

}
