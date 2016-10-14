package com.sunny.mvpzhihu.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.SyncService;
import com.sunny.mvpzhihu.data.local.DatabaseHelper;
import com.sunny.mvpzhihu.data.local.PreferencesHelper;
import com.sunny.mvpzhihu.data.remote.SubjectsService;
import com.sunny.mvpzhihu.injection.qualifier.ApplicationContext;
import com.sunny.mvpzhihu.injection.module.ApplicationModule;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;
import com.sunny.mvpzhihu.utils.singleton.RxBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    SubjectsService subjectsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxBus eventBus();
    ImageLoader imageLoader();

}
