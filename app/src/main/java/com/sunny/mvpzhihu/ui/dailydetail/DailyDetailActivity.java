package com.sunny.mvpzhihu.ui.dailydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseActivity;

/**
 * The type Daily detail activity.
 * Created by Zhou Zejin on 2017/5/18.
 */
public class DailyDetailActivity extends BaseActivity {

    /**
     * Return an Intent to start this Activity.
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DailyDetailActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

}
