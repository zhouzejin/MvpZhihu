package com.sunny.mvpzhihu.ui.themedetail;

import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.entity.ThemeDetailEntity;
import com.sunny.mvpzhihu.injection.scope.ConfigPersistent;
import com.sunny.mvpzhihu.ui.base.BasePresenter;
import com.sunny.mvpzhihu.utils.LogUtil;
import com.sunny.mvpzhihu.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * The type Theme detail presenter.
 * Created by Zhou Zejin on 2017/6/7.
 */

@ConfigPersistent
public class ThemeDetailPresenter extends BasePresenter<ThemeDetailMvpView> {

    private final DataManager mDataManager;

    private Subscription mSubscription;

    @Inject
    public ThemeDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ThemeDetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadThemeDetail(int themeId) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);

        mSubscription = mDataManager.getThemeDetail(themeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().showProgress();
                    }
                })
                .subscribe(new Subscriber<ThemeDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载ThemeDetail数据出错。");
                        getMvpView().hideProgress();
                    }

                    @Override
                    public void onNext(ThemeDetailEntity themeDetailEntity) {
                        getMvpView().hideProgress();
                        getMvpView().showThemeDetail(themeDetailEntity);
                    }
                });
    }

}
