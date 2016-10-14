package com.sunny.mvpzhihu.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.sunny.mvpzhihu.injection.component.ApplicationComponent;
import com.sunny.mvpzhihu.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
