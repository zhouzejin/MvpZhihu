package com.sunny.mvpzhihu.ui.dailydetail;

import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.entity.StoryEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryExtraEntity;
import com.sunny.mvpzhihu.injection.scope.ConfigPersistent;
import com.sunny.mvpzhihu.ui.base.BasePresenter;
import com.sunny.mvpzhihu.utils.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * The type Daily detail presenter.
 * Created by Zhou Zejin on 2017/5/26.
 */

@ConfigPersistent
public class DailyDetailPresenter extends BasePresenter<DailyDetailMvpView> {

    private final DataManager mDataManager;

    private Subscription mSubscription;

    @Inject
    public DailyDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DailyDetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadDailyDetail(int dailyId) {
        checkViewAttached();
        mSubscription = mDataManager.getDailyDetail(dailyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().showProgress();
                    }
                })
                .subscribe(new Subscriber<StoryEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载Daily Detail数据出错。");
                        getMvpView().hideProgress();
                    }

                    @Override
                    public void onNext(StoryEntity storyEntity) {
                        getMvpView().hideProgress();
                        getMvpView().showDailyDetail(storyEntity);
                    }
                });
    }

    public void loadDailyExtra(int dailyId) {
        checkViewAttached();
        mSubscription = mDataManager.getDailyExtra(dailyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StoryExtraEntity>() {
                    @Override
                    public void call(StoryExtraEntity storyExtraEntity) {
                        getMvpView().showDailyExtra(storyExtraEntity);
                    }
                });
    }

    public boolean getIsShowSwipeBackHint() {
        boolean isShow = mDataManager.isShowSwipeBackHint();
        mDataManager.setIsShowSwipeBasckHint(false);
        return isShow;
    }

}
