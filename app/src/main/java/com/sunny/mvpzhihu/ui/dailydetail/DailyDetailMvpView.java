package com.sunny.mvpzhihu.ui.dailydetail;

import com.sunny.mvpzhihu.data.model.entity.StoryEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryExtraEntity;
import com.sunny.mvpzhihu.ui.base.MvpView;

/**
 * The type Daily detail mvp view.
 * Created by Zhou Zejin on 2017/5/26.
 */

public interface DailyDetailMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showDailyDetail(StoryEntity storyEntity);

    void showDailyExtra(StoryExtraEntity storyExtraEntity);

    void showSwipeBackHint();

}
