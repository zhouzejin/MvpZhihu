package com.sunny.mvpzhihu.ui.splash;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.remote.ZhihuService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * App启动页面
 * 该页面不要继承AppCompatActivity, 因为加载主题会导致界面启动卡顿。
 * <p>
 * Created by Zhou Zejin on 2016/10/14.
 */

public class SplashActivity extends Activity {

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.tv_form)
    TextView mTvForm;

    @Inject
    ZhihuService mZhihuService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        getLaunchImage();

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getLaunchImage() {

    }

}
