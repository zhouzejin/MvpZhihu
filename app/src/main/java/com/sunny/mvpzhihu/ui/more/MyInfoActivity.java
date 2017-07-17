package com.sunny.mvpzhihu.ui.more;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * The type My Info activity.
 * Created by Zhou Zejin on 2017/7/17.
 */
public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info;
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
        getSupportActionBar().setTitle(getString(R.string.about_me));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setSwipeBackEnable(true);
    }

}
