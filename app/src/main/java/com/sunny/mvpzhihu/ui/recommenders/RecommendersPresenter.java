package com.sunny.mvpzhihu.ui.recommenders;

import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.bean.Editor;
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
 * The type Recommenders presenter.
 * Created by Zhou Zejin on 2017/5/31.
 */

@ConfigPersistent
public class RecommendersPresenter extends BasePresenter<RecommendersMvpView> {

    private final DataManager mDataManager;

    private Subscription mSubscription;

    @Inject
    public RecommendersPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(RecommendersMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadEditors(final int dailyId) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);

        mSubscription = mDataManager.getEditors(dailyId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().showProgress();
                    }
                })
                .subscribe(new Subscriber<List<Editor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载Editor数据出错。");
                        getMvpView().hideProgress();
                    }

                    @Override
                    public void onNext(List<Editor> editors) {
                        getMvpView().hideProgress();
                        if (editors == null || editors.isEmpty()) {
                            getMvpView().showEditorsEmpty();
                        } else {
                            getMvpView().showEditors(editors);
                        }
                    }
                });
    }

}
