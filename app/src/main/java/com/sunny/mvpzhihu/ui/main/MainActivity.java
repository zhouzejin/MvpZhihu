package com.sunny.mvpzhihu.ui.main;

import android.os.Bundle;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseActivity;

/**
 * Main Activity
 * Created by Zhou Zejin on 2016/10/24.
 */
public class MainActivity extends BaseActivity {

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

    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
