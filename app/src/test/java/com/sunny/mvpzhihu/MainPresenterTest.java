package com.sunny.mvpzhihu;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.test.common.TestDataFactory;
import com.sunny.mvpzhihu.ui.main.MainMvpView;
import com.sunny.mvpzhihu.ui.main.MainPresenter;
import com.sunny.mvpzhihu.utils.RxSchedulersOverrideRule;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock MainMvpView mMockMainMvpView;
    @Mock DataManager mMockDataManager;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadSubjectsReturnsSubjects() {
        List<Subject> subjects = TestDataFactory.makeListSubject(10);
        when(mMockDataManager.getSubjects())
                .thenReturn(Observable.just(subjects));

        mMainPresenter.loadSubjects();
        verify(mMockMainMvpView).showSubjects(subjects);
        verify(mMockMainMvpView, never()).showSubjectsEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadSubjectsReturnsEmptyList() {
        when(mMockDataManager.getSubjects())
                .thenReturn(Observable.just(Collections.<Subject>emptyList()));

        mMainPresenter.loadSubjects();
        verify(mMockMainMvpView).showSubjectsEmpty();
        verify(mMockMainMvpView, never()).showSubjects(anyListOf(Subject.class));
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadSubjectsFails() {
        when(mMockDataManager.getSubjects())
                .thenReturn(Observable.<List<Subject>>error(new RuntimeException()));

        mMainPresenter.loadSubjects();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showSubjectsEmpty();
        verify(mMockMainMvpView, never()).showSubjects(anyListOf(Subject.class));
    }

}
