package com.sunny.mvpzhihu.ui.themedetail;

import com.sunny.mvpzhihu.data.model.entity.ThemeDetailEntity;
import com.sunny.mvpzhihu.ui.base.MvpView;

/**
 * The type Theme detail mvp view.
 * Created by Zhou Zejin on 2017/6/7.
 */
public interface ThemeDetailMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showThemeDetail(ThemeDetailEntity themeDetailEntity);

}
