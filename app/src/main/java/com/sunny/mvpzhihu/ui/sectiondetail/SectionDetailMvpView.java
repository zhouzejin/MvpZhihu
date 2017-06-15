package com.sunny.mvpzhihu.ui.sectiondetail;

import com.sunny.mvpzhihu.data.model.bean.SectionStory;
import com.sunny.mvpzhihu.ui.base.MvpView;

import java.util.List;

/**
 * The interface Section detail mvp view.
 * Created by Zhou Zejin on 2017/6/14.
 */
public interface SectionDetailMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showSectionDetail(List<SectionStory> sectionStories);

    void setRecyclerScrollLoading(boolean isLoading);

}
