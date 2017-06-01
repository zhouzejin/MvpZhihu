package com.sunny.mvpzhihu.ui.recommenders;

import com.sunny.mvpzhihu.data.model.bean.Editor;
import com.sunny.mvpzhihu.ui.base.MvpView;

import java.util.List;

/**
 * The interface Recommenders mvp view.
 * Created by Zhou Zejin on 2017/5/31.
 */

public interface RecommendersMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showEditorsEmpty();

    void showEditors(List<Editor> editors);

}
