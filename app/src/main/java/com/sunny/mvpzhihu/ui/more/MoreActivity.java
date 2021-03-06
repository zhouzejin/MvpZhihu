package com.sunny.mvpzhihu.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The type More activity.
 * Created by Zhou Zejin on 2017/7/14.
 */
public class MoreActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_more;
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
        getSupportActionBar().setTitle(getString(R.string.action_more));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setSwipeBackEnable(true);
    }

    @OnClick({R.id.rl_my_info, R.id.tv_feedback, R.id.tv_setting, R.id.tv_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_my_info:
                startActivity(new Intent(this, MyInfoActivity.class));
                break;
            case R.id.tv_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.tv_setting:
                Toast.makeText(this, "敬请期待！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }

}
