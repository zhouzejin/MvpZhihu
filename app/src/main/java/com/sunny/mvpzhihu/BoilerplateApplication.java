package com.sunny.mvpzhihu;

import android.app.Application;
import android.content.Context;

import com.sunny.mvpzhihu.injection.component.ApplicationComponent;
import com.sunny.mvpzhihu.injection.component.DaggerApplicationComponent;
import com.sunny.mvpzhihu.injection.module.ApplicationModule;
import com.sunny.mvpzhihu.utils.LogUtil;

public class BoilerplateApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.initLog();
    }

    public static BoilerplateApplication get(Context context) {
        return (BoilerplateApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
