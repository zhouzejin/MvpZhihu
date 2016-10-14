package com.sunny.mvpzhihu.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import com.sunny.mvpzhihu.data.local.DatabaseHelper;
import com.sunny.mvpzhihu.data.local.PreferencesHelper;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.data.model.entity.InTheatersEntity;
import com.sunny.mvpzhihu.data.remote.SubjectsService;

@Singleton
public class DataManager {

    private final SubjectsService mSubjectsService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(SubjectsService subjectsService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mSubjectsService = subjectsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Subject> syncSubjects() {
        return mSubjectsService.getSubjects()
                .concatMap(new Func1<InTheatersEntity, Observable<? extends Subject>>() {
                    @Override
                    public Observable<? extends Subject> call(InTheatersEntity inTheatersEntity) {
                        return mDatabaseHelper.setSubjects(inTheatersEntity.subjects());
                    }
                });
    }

    public Observable<List<Subject>> getSubjects() {
        return mDatabaseHelper.getSubjects().distinct();
    }

}
