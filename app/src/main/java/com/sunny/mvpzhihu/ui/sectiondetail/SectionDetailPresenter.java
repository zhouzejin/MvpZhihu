package com.sunny.mvpzhihu.ui.sectiondetail;

import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.entity.SectionDetailEntity;
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
 * The type Section detail presenter.
 * Created by Zhou Zejin on 2017/6/14.
 */

@ConfigPersistent
public class SectionDetailPresenter extends BasePresenter<SectionDetailMvpView> {

    private final DataManager mDataManager;

    private Subscription mSubscription;
    private long mTimestamp;


    @Inject
    public SectionDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SectionDetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadSectionDetail(int sectionId) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getSectionDetail(sectionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().showProgress();
                    }
                })
                .subscribe(new Subscriber<SectionDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载SectionDetail数据出错。");
                        getMvpView().hideProgress();
                    }

                    @Override
                    public void onNext(SectionDetailEntity sectionDetailEntity) {
                        getMvpView().hideProgress();
                        getMvpView().showSectionDetail(sectionDetailEntity.stories());
                        mTimestamp = sectionDetailEntity.timestamp();
                    }
                });
    }

    public void loadMoreSectionDetail(int sectionId) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getBeforeSectionDetail(sectionId, mTimestamp)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<SectionDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载更多SectionDetail数据出错。");
                        getMvpView().setRecyclerScrollLoading(false);
                    }

                    @Override
                    public void onNext(SectionDetailEntity sectionDetailEntity) {
                        getMvpView().setRecyclerScrollLoading(false);
                        getMvpView().showSectionDetail(sectionDetailEntity.stories());
                        mTimestamp = sectionDetailEntity.timestamp();
                    }
                });
    }

}
