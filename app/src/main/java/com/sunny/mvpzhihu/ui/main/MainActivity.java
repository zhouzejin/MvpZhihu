package com.sunny.mvpzhihu.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.ui.main.daily.DailyFragment;
import com.sunny.mvpzhihu.ui.main.section.SectionFragment;
import com.sunny.mvpzhihu.ui.main.theme.ThemeFragment;
import com.sunny.mvpzhihu.utils.ActivityUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Main Activity
 * Created by Zhou Zejin on 2016/10/24.
 */
public class MainActivity extends BaseActivity {

    private static final String DAILY_TAG = "daily_fragment";
    private static final String THEME_TAG = "theme_fragment";
    private static final String SECTION_TAG = "section_fragment";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;

    private Map<String, Fragment> mFragments;
    private String mCurrentTag;

    /**
     * Return an Intent to start this Activity.
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        addFragments();
        initBottomNavigation();

        // 显示默认页面
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content, mFragments.get(DAILY_TAG)).commit();
        mCurrentTag = DAILY_TAG;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                Toast.makeText(this, "更多选项", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFragments() {
        mFragments = new HashMap<>();
        mFragments.put(DAILY_TAG, DailyFragment.newInstance());
        mFragments.put(THEME_TAG, ThemeFragment.newInstance());
        mFragments.put(SECTION_TAG, SectionFragment.newInstance());
    }

    private void initBottomNavigation() {
        mBottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_daily:
                                showFragment(DAILY_TAG);
                                break;
                            case R.id.item_theme:
                                showFragment(THEME_TAG);
                                break;
                            case R.id.item_section:
                                showFragment(SECTION_TAG);
                                break;
                            case R.id.item_news:
                                Toast.makeText(MainActivity.this, "文章", Toast.LENGTH_LONG).show();
                                break;
                        }
                        return true;
                    }
                });
        mBottomNavigation.setOnNavigationItemReselectedListener(
                new BottomNavigationView.OnNavigationItemReselectedListener() {
                    @Override
                    public void onNavigationItemReselected(@NonNull MenuItem item) {
                        // Do nothing!
                    }
                });
    }

    private void showFragment(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment hideFragment = mFragments.get(mCurrentTag);
        Fragment showFragment = mFragments.get(tag);
        if (!showFragment.isAdded()) {
            ActivityUtil.addFragmentToActivity(fm, showFragment, R.id.frame_content, tag);
        }
        ActivityUtil.hideAndShowFragment(fm, hideFragment, showFragment);
        mCurrentTag = tag;
    }

    public void setOnClickToolbar(final ClickToolbar clickToolbar) {
        if (clickToolbar == null) return;
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToolbar.onClickToolbar();
            }
        });
    }

    public interface ClickToolbar {

        void onClickToolbar();
    }

}
