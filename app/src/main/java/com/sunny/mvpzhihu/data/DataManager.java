package com.sunny.mvpzhihu.data;

import com.sunny.mvpzhihu.data.local.DatabaseHelper;
import com.sunny.mvpzhihu.data.local.PreferencesHelper;
import com.sunny.mvpzhihu.data.model.bean.Creative;
import com.sunny.mvpzhihu.data.model.bean.Story;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.data.model.entity.InTheatersEntity;
import com.sunny.mvpzhihu.data.model.entity.PrefetchLaunchImagesEntity;
import com.sunny.mvpzhihu.data.model.entity.StoriesBeforeEntity;
import com.sunny.mvpzhihu.data.model.entity.StoriesLastEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryExtraEntity;
import com.sunny.mvpzhihu.data.remote.RetrofitService;
import com.sunny.mvpzhihu.data.remote.ZhihuService;
import com.sunny.mvpzhihu.ui.main.daily.DailyModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

@Singleton
public class DataManager {

    private final ZhihuService mZhihuService;
    private final RetrofitService mRetrofitService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(RetrofitService retrofitService, ZhihuService zhihuService,
                       PreferencesHelper preferencesHelper, DatabaseHelper databaseHelper) {
        mZhihuService = zhihuService;
        mRetrofitService = retrofitService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Subject> syncSubjects() {
        return mRetrofitService.getSubjects()
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

    public Observable<Creative> getCreate(String res) {
        return mZhihuService.getPrefetchLaunchImages(res)
                .map(new Func1<PrefetchLaunchImagesEntity, Creative>() {
                    @Override
                    public Creative call(PrefetchLaunchImagesEntity prefetchLaunchImagesEntity) {
                        return prefetchLaunchImagesEntity.creatives().get(0);
                    }
                });
    }

    public Observable<StoriesLastEntity> getLastStories() {
        return mZhihuService.getStoriesLast();
    }

    public Observable<List<DailyModel>> getDailies(StoriesLastEntity entity) {
        return mDatabaseHelper.setDailies(entity.stories(), entity.date());
    }

    public Observable<List<DailyModel>> getMoreDailies(String date) {
        return mZhihuService.getStoriesBefore(date)
                .concatMap(new Func1<StoriesBeforeEntity, Observable<? extends List<DailyModel>>>() {
                    @Override
                    public Observable<? extends List<DailyModel>> call(
                            StoriesBeforeEntity storiesBeforeEntity) {
                        return mDatabaseHelper.setDailies(storiesBeforeEntity.stories(),
                                storiesBeforeEntity.date());
                    }
                });
    }

    public Observable<Long> saveStory(Story story) {
        return mDatabaseHelper.insertStory(story);
    }

    public Observable<StoryEntity> getDailyDetail(int dailyId) {
        return mZhihuService.getStory(dailyId);
    }

    public Observable<StoryExtraEntity> getDailyExtra(int dailyId) {
        return mZhihuService.getStoryExtra(dailyId);
    }

    public void setShowSwipeBasckHint(boolean value) {
        mPreferencesHelper.putBoolean(PreferencesHelper.IS_SHOW_SWIPE_BACK_HINT, value);
    }

    public boolean isShowSwipeBackHint() {
        return mPreferencesHelper.getBoolean(PreferencesHelper.IS_SHOW_SWIPE_BACK_HINT, true);
    }

}
