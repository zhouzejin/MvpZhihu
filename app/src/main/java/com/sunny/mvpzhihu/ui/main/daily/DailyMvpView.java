package com.sunny.mvpzhihu.ui.main.daily;

import com.sunny.mvpzhihu.data.model.bean.TopStory;
import com.sunny.mvpzhihu.ui.base.MvpView;

import java.util.List;

/**
 * The interface Daily mvp view.
 * Created by Zhou Zejin on 2017/4/24.
 */
public interface DailyMvpView extends MvpView {

    void showDailies(List<DailyModel> dailyModels);

    void showDailiesEmpty();

    void showTopDailies(List<TopStory> topStories);

    void showTopDailiesEmpty();

    void showProgress();

    void hideProgress();

    void showDailyDetail(int dailyId);

    void setRecyclerScrollLoading(boolean isLoading);

    void loadDailiesOver();

}
