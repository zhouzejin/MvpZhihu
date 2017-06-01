package com.sunny.mvpzhihu.injection.component;

import com.sunny.mvpzhihu.ZhiHuApplication;
import com.sunny.mvpzhihu.injection.module.ActivityModule;
import com.sunny.mvpzhihu.injection.scope.InActivity;
import com.sunny.mvpzhihu.ui.dailydetail.DailyDetailActivity;
import com.sunny.mvpzhihu.ui.recommenders.RecommendersActivity;
import com.sunny.mvpzhihu.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * This is a Dagger component. Refer to {@link ZhiHuApplication} for the list of Dagger components
 * used in this application.
 */
@InActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(DailyDetailActivity dailyDetailActivity);

    void inject(RecommendersActivity recommendersActivity);

}
