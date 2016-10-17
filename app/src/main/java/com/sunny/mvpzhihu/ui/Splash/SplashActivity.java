package com.sunny.mvpzhihu.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ZhiHuApplication;
import com.sunny.mvpzhihu.data.model.pojo.LaunchImage;
import com.sunny.mvpzhihu.data.remote.ZhihuService;
import com.sunny.mvpzhihu.ui.main.MainActivity;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * App启动页面
 * 该页面不要继承AppCompatActivity, 因为加载主题会导致界面启动卡顿。
 * <p>
 * Created by Zhou Zejin on 2016/10/14.
 */

public class SplashActivity extends Activity {

    private Unbinder unbinder;

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.tv_form)
    TextView mTvForm;

    @Inject
    ZhihuService mZhihuService;
    @Inject
    ImageLoader mImageLoader;

    private static final String RESOLUTION = "1080*1776";
    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                animateImage();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        ZhiHuApplication.get(this).getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        getLaunchImage();

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacksAndMessages(null);
        unbinder.unbind();
    }

    private void getLaunchImage() {
        mZhihuService.getLaunchImage(RESOLUTION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LaunchImage>() {
                    @Override
                    public void call(LaunchImage launchImage) {
                        mImageLoader.displayUrlImage(SplashActivity.this, mIvSplash,
                                launchImage.img(),
                                new ImageLoader.DisplayOption.Builder()
                                        .placeHolder(R.drawable.splash_default)
                                        .build());
                        mTvForm.setText(launchImage.text());
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mImageLoader.displayResImage(SplashActivity.this, mIvSplash,
                                R.drawable.splash_default);
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                });
    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvSplash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvSplash, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

}
