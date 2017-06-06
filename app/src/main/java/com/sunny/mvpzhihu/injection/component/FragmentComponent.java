package com.sunny.mvpzhihu.injection.component;

import com.sunny.mvpzhihu.ZhiHuApplication;
import com.sunny.mvpzhihu.injection.module.ActivityModule;
import com.sunny.mvpzhihu.injection.module.FragmentModule;
import com.sunny.mvpzhihu.injection.scope.InFragment;
import com.sunny.mvpzhihu.ui.comment.LongCommentFragment;
import com.sunny.mvpzhihu.ui.comment.ShortCommentFragment;
import com.sunny.mvpzhihu.ui.example.MainFragment;
import com.sunny.mvpzhihu.ui.main.daily.DailyFragment;
import com.sunny.mvpzhihu.ui.main.theme.ThemeFragment;

import dagger.Subcomponent;

/**
 * This is a Dagger component. Refer to {@link ZhiHuApplication} for the list of Dagger components
 * used in this application.
 */
@InFragment
@Subcomponent(modules = {ActivityModule.class, FragmentModule.class})
public interface FragmentComponent {

    void inject(MainFragment mainFragment);

    void inject(DailyFragment dailyFragment);

    void inject(LongCommentFragment longCommentFragment);

    void inject(ShortCommentFragment shortCommentFragment);

    void inject(ThemeFragment themeFragment);

}
