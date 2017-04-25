package com.sunny.mvpzhihu.ui.main;

import com.sunny.mvpzhihu.injection.scope.ConfigPersistent;
import com.sunny.mvpzhihu.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * The type Daily presenter.
 * Created by Zhou Zejin on 2017/4/24.
 */

@ConfigPersistent
public class DailyPresenter extends BasePresenter<DailyMvpView> {

    @Inject
    public DailyPresenter() {

    }

    @Override
    public void attachView(DailyMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

}
