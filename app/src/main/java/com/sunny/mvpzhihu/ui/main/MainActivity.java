package com.sunny.mvpzhihu.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Main Activity
 * Created by Zhou Zejin on 2016/10/24.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initBottomNavigation();
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
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

    private void initBottomNavigation() {
        mBottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_answer:
                                Toast.makeText(MainActivity.this, "日报", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.item_article:
                                Toast.makeText(MainActivity.this, "主题", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.item_column:
                                Toast.makeText(MainActivity.this, "专栏", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.item_favorite:
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

}
