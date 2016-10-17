package com.sunny.mvpzhihu.injection.module;

import android.app.Application;
import android.content.Context;

import com.sunny.mvpzhihu.data.remote.RetrofitService;
import com.sunny.mvpzhihu.data.remote.ZhihuService;
import com.sunny.mvpzhihu.injection.qualifier.ApplicationContext;
import com.sunny.mvpzhihu.utils.imageloader.GlideImageLoader;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    RetrofitService provideRetrofitService() {
        return RetrofitService.Creator.newRetrofitService();
    }

    @Provides
    @Singleton
    ZhihuService provideZhihuService() {
        return ZhihuService.Creator.newZhihuService(provideContext());
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader() {
        return new GlideImageLoader();
    }

}
