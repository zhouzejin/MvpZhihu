package com.sunny.mvpzhihu.injection.component;

import com.sunny.mvpzhihu.ZhiHuApplication;
import com.sunny.mvpzhihu.injection.module.ActivityModule;
import com.sunny.mvpzhihu.injection.module.FragmentModule;
import com.sunny.mvpzhihu.injection.scope.InFragment;
import com.sunny.mvpzhihu.ui.example.MainFragment;

import dagger.Subcomponent;

/**
 * This is a Dagger component. Refer to {@link ZhiHuApplication} for the list of Dagger components
 * used in this application.
 */
@InFragment
@Subcomponent(modules = {ActivityModule.class, FragmentModule.class})
public interface FragmentComponent {

    void inject(MainFragment mainFragment);

}
