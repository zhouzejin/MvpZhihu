package com.sunny.mvpzhihu.injection.scope;

import com.sunny.mvpzhihu.injection.component.ActivityComponent;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * {@link ActivityComponent} is a scoped component ({@code @Singleton}, we create a custom
 * scope to be used by all activity components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface InActivity {
}
