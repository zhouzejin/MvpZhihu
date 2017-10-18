package com.sunny.mvpzhihu.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ZhiHuApplication;
import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.bean.Creative;
import com.sunny.mvpzhihu.injection.component.ConfigPersistentComponent;
import com.sunny.mvpzhihu.injection.component.DaggerConfigPersistentComponent;
import com.sunny.mvpzhihu.injection.module.ActivityModule;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.ui.main.MainActivity;
import com.sunny.mvpzhihu.utils.LogUtil;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import java.lang.ref.WeakReference;

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
    @ActivityContext
    Context mContext;
    @Inject
    DataManager mDataManager;
    @Inject
    ImageLoader mImageLoader;

    private static final int MSG_ID_ANIMATE_IMAGE = 0;
    private static final String RESOLUTION = "1080*1776";
    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;

    private final Handler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);

        // Inject instance for activity
        ConfigPersistentComponent configPersistentComponent = DaggerConfigPersistentComponent
                .builder()
                .applicationComponent(ZhiHuApplication.get(this).getComponent())
                .build();
        configPersistentComponent.activityComponent(new ActivityModule(this)).inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 让Layout延伸到StatusBar和NavigationBar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    protected void onResume() {
        getLaunchImage();

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 当Activity finish后，handler对象还在Message中排队，还会处理消息，
        // 不过，这时已经没必要对消息进行处理，应该取消掉Handler对象的Message和Runnable.
        mHandler.removeCallbacksAndMessages(null);
        unbinder.unbind();
    }

    private void getLaunchImage() {
        mDataManager.getCreate(RESOLUTION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Creative>() {
                    @Override
                    public void call(Creative creative) {
                        String text = "StartTime:" + creative.start_time();
                        mImageLoader.displayUrlImage(mContext, mIvSplash, creative.url(),
                                new ImageLoader.DisplayOption.Builder()
                                        .placeHolder(R.drawable.splash_default).build());
                        mTvForm.setText(text);
                        mHandler.sendEmptyMessageDelayed(MSG_ID_ANIMATE_IMAGE, 1000);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.w(throwable, "获取启动页面失败，使用默认启动图片。");

                        mImageLoader.displayResImage(mContext, mIvSplash,
                                R.drawable.splash_default);
                        mHandler.sendEmptyMessageDelayed(MSG_ID_ANIMATE_IMAGE, 1000);
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
                startActivity(MainActivity.getStartIntent(mContext));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    // 使用静态内部类，避免直接引用OuterClass
    private final static class MyHandler extends Handler {
        // 使用弱引用，避免Handler阻止Activity被回收，造成内存泄露
        private final WeakReference<SplashActivity> mActivityWeakReference;

        MyHandler(SplashActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mActivityWeakReference.get() == null) {
                // 引用被回收
                return;
            }

            if (msg.what == MSG_ID_ANIMATE_IMAGE) {
                mActivityWeakReference.get().animateImage();
            }
        }
    }

}
