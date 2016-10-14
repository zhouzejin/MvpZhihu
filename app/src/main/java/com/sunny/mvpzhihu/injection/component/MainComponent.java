package com.sunny.mvpzhihu.injection.component;

import dagger.Subcomponent;
import com.sunny.mvpzhihu.BoilerplateApplication;
import com.sunny.mvpzhihu.injection.scope.InFragment;
import com.sunny.mvpzhihu.injection.module.ActivityModule;
import com.sunny.mvpzhihu.injection.module.FragmentModule;
import com.sunny.mvpzhihu.ui.main.MainFragment;

/**
 * This is a Dagger component. Refer to {@link BoilerplateApplication} for the list of Dagger components
 * used in this application.
 */
@InFragment
@Subcomponent(modules = {ActivityModule.class, FragmentModule.class})
public interface MainComponent {

    void inject(MainFragment mainFragment);

}
