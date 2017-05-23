package com.sunny.mvpzhihu.ui.main.daily;

import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.entity.StoriesLastEntity;
import com.sunny.mvpzhihu.injection.scope.ConfigPersistent;
import com.sunny.mvpzhihu.ui.base.BasePresenter;
import com.sunny.mvpzhihu.utils.LogUtil;
import com.sunny.mvpzhihu.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * The type Story presenter.
 * Created by Zhou Zejin on 2017/4/24.
 */

@ConfigPersistent
public class DailyPresenter extends BasePresenter<DailyMvpView> {

    private static final int SIZE_TO_LOAD_MORE = 8;

    private final DataManager mDataManager;

    private Subscription mSubscription;
    private String mCurrentDate;

    @Inject
    public DailyPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DailyMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadDailies(final boolean isSwipeRefresh) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getLastStories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isSwipeRefresh) getMvpView().showProgress();
                    }
                })
                .concatMap(new Func1<StoriesLastEntity, Observable<List<DailyModel>>>() {
                    @Override
                    public Observable<List<DailyModel>> call(StoriesLastEntity entity) {
                        getMvpView().showTopDailiesEmpty(); // 避免刷新时产生冗余数据
                        getMvpView().showTopDailies(entity.top_stories());
                        return mDataManager.getDailies(entity);
                    }
                })
                .subscribe(new Subscriber<List<DailyModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载Daily数据出错。");
                        getMvpView().hideProgress();
                    }

                    @Override
                    public void onNext(List<DailyModel> dailyModels) {
                        getMvpView().showDailiesEmpty(); // 避免刷新时产生冗余数据
                        getMvpView().showDailies(dailyModels);
                        getMvpView().loadDailiesOver();

                        if (!dailyModels.isEmpty()) {
                            mCurrentDate = dailyModels.get(dailyModels.size() - 1).getDate();
                            // 预加载前一天的数据
                            if (dailyModels.size() < SIZE_TO_LOAD_MORE)
                                loadMoreDailies();
                        }
                    }
                });

    }

    public void loadMoreDailies() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getMoreDailies(mCurrentDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DailyModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载更多Daily数据出错。");
                        getMvpView().setRecyclerScrollLoading(false);
                    }

                    @Override
                    public void onNext(List<DailyModel> dailyModels) {
                        getMvpView().setRecyclerScrollLoading(false);
                        getMvpView().showDailies(dailyModels);
                        mCurrentDate = dailyModels.get(dailyModels.size() - 1).getDate();
                    }
                });
    }

    public void openDailyDetail(final DailyModel model) {
        if (!model.getRead()) {
            model.setRead(true);

            checkViewAttached();
            RxUtil.unsubscribe(mSubscription);
            mDataManager.saveStory(model.getStory())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            LogUtil.e(throwable, "Story插入失败，Title-" + model.getStory().title());
                        }
                    });
        }
        getMvpView().showDailyDetail(model.getStory().id());
    }

}
