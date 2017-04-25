package com.sunny.mvpzhihu.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sunny.mvpzhihu.ZhiHuApplication;
import com.sunny.mvpzhihu.injection.component.ActivityComponent;
import com.sunny.mvpzhihu.injection.component.ConfigPersistentComponent;
import com.sunny.mvpzhihu.injection.component.DaggerConfigPersistentComponent;
import com.sunny.mvpzhihu.injection.module.ActivityModule;
import com.sunny.mvpzhihu.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);

    private static Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private ConfigPersistentComponent mConfigPersistentComponent;
    private ActivityComponent mActivityComponent;
    private long mActivityId;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createComponent(savedInstanceState);
        mActivityComponent = mConfigPersistentComponent.activityComponent(new ActivityModule(this));
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initViews(savedInstanceState);
        initToolBar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            LogUtil.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }

        mUnbinder.unbind();
        super.onDestroy();
    }

    /**
     * 获取布局文件
     * @return 布局文件ID
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化ToolBar
     */
    public abstract void initToolBar();

    /**
     * Create the ConfigPersistentComponent and reuses cached ConfigPersistentComponent if this is
     * being called after a configuration change.
     */
    private void createComponent(Bundle savedInstanceState) {
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        if (!sComponentsMap.containsKey(mActivityId)) {
            LogUtil.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            mConfigPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(ZhiHuApplication.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, mConfigPersistentComponent);
        } else {
            LogUtil.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            mConfigPersistentComponent = sComponentsMap.get(mActivityId);
        }
    }

    public ConfigPersistentComponent configPersistentComponent() {
        return mConfigPersistentComponent;
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

}
