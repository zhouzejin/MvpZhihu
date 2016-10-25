package com.sunny.mvpzhihu.ui.example;

import java.util.List;

import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showSubjects(List<Subject> ribots);

    void showSubjectsEmpty();

    void showError();

}
