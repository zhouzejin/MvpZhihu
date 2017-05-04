package com.sunny.mvpzhihu.ui.main;

import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.injection.scope.ConfigPersistent;
import com.sunny.mvpzhihu.ui.base.BasePresenter;
import com.sunny.mvpzhihu.utils.LogUtil;
import com.sunny.mvpzhihu.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * The type Story presenter.
 * Created by Zhou Zejin on 2017/4/24.
 */

@ConfigPersistent
public class DailyPresenter extends BasePresenter<DailyMvpView> {

    private final DataManager mDataManager;

    private Subscription mSubscription;

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

        mSubscription = mDataManager.getDailies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isSwipeRefresh) getMvpView().showProgress();
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
                        if (dailyModels.isEmpty()) {
                            getMvpView().showDailiesEmpty();
                        } else {
                            getMvpView().hideProgress();
                            getMvpView().showDailies(dailyModels);
                        }
                    }
                });
    }

    public void openDailyDetail(DailyModel model) {
        getMvpView().showTaskDetail(model.getStory().title());
    }

}
