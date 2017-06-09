package com.sunny.mvpzhihu.data;

import com.sunny.mvpzhihu.data.local.DatabaseHelper;
import com.sunny.mvpzhihu.data.local.PreferencesHelper;
import com.sunny.mvpzhihu.data.model.bean.Comment;
import com.sunny.mvpzhihu.data.model.bean.Creative;
import com.sunny.mvpzhihu.data.model.bean.Editor;
import com.sunny.mvpzhihu.data.model.bean.Section;
import com.sunny.mvpzhihu.data.model.bean.Story;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.data.model.bean.Theme;
import com.sunny.mvpzhihu.data.model.entity.CommentsEntity;
import com.sunny.mvpzhihu.data.model.entity.InTheatersEntity;
import com.sunny.mvpzhihu.data.model.entity.PrefetchLaunchImagesEntity;
import com.sunny.mvpzhihu.data.model.entity.SectionsEntity;
import com.sunny.mvpzhihu.data.model.entity.StoriesBeforeEntity;
import com.sunny.mvpzhihu.data.model.entity.StoriesLastEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryExtraEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryRecommendersEntity;
import com.sunny.mvpzhihu.data.model.entity.ThemeDetailEntity;
import com.sunny.mvpzhihu.data.model.entity.ThemesEntity;
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

    public Observable<List<Editor>> getEditors(int dailyId) {
        return mZhihuService.getStoryRecommenders(dailyId)
                .map(new Func1<StoryRecommendersEntity, List<Editor>>() {
                    @Override
                    public List<Editor> call(StoryRecommendersEntity storyRecommendersEntity) {
                        return storyRecommendersEntity.editors();
                    }
                });
    }

    public Observable<List<Comment>> getLongComments(int commentId) {
        return mZhihuService.getStoryLongComments(commentId)
                .map(new Func1<CommentsEntity, List<Comment>>() {
                    @Override
                    public List<Comment> call(CommentsEntity commentsEntity) {
                        return commentsEntity.comments();
                    }
                });
    }

    public Observable<List<Comment>> getShortComments(int commentId) {
        return mZhihuService.getStoryShortComments(commentId)
                .map(new Func1<CommentsEntity, List<Comment>>() {
                    @Override
                    public List<Comment> call(CommentsEntity commentsEntity) {
                        return commentsEntity.comments();
                    }
                });
    }

    public Observable<List<Theme>> getThemes() {
        return mZhihuService.getThemes()
                .map(new Func1<ThemesEntity, List<Theme>>() {
                    @Override
                    public List<Theme> call(ThemesEntity themesEntity) {
                        return themesEntity.others();
                    }
                });
    }

    public Observable<ThemeDetailEntity> getThemeDetail(int themeId) {
        return mZhihuService.getThemeDetail(themeId);
    }

    public Observable<List<Section>> getSections() {
        return mZhihuService.getSections()
                .map(new Func1<SectionsEntity, List<Section>>() {
                    @Override
                    public List<Section> call(SectionsEntity sectionsEntity) {
                        return sectionsEntity.sections();
                    }
                });
    }

}
