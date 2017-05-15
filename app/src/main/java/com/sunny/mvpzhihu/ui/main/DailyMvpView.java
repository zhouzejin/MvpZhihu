package com.sunny.mvpzhihu.ui.main;

import com.sunny.mvpzhihu.ui.base.MvpView;

import java.util.List;

/**
 * The interface Story mvp view.
 * Created by Zhou Zejin on 2017/4/24.
 */
public interface DailyMvpView extends MvpView {

    void showDailies(List<DailyModel> dailyModels);

    void showDailiesEmpty();

    void showProgress();

    void hideProgress();

    void showTaskDetail(String msg);

    void setRecyclerScrollLoading(boolean isLoading);

    void loadDailiesOver();

}
