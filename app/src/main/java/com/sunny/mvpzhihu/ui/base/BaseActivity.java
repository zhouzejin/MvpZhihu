package com.sunny.mvpzhihu.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.sunny.mvpzhihu.BoilerplateApplication;
import com.sunny.mvpzhihu.injection.component.ConfigPersistentComponent;
import com.sunny.mvpzhihu.injection.component.DaggerConfigPersistentComponent;
import com.sunny.mvpzhihu.utils.LogUtil;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private ConfigPersistentComponent mConfigPersistentComponent;
    private long mActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the MainComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        if (!sComponentsMap.containsKey(mActivityId)) {
            LogUtil.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            mConfigPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(BoilerplateApplication.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, mConfigPersistentComponent);
        } else {
            LogUtil.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            mConfigPersistentComponent = sComponentsMap.get(mActivityId);
        }
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
        super.onDestroy();
    }

    public ConfigPersistentComponent configPersistentComponent() {
        return mConfigPersistentComponent;
    }

}
