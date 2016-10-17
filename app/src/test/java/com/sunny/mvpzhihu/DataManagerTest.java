package com.sunny.mvpzhihu;

import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.local.DatabaseHelper;
import com.sunny.mvpzhihu.data.local.PreferencesHelper;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.data.model.entity.InTheatersEntity;
import com.sunny.mvpzhihu.data.remote.RetrofitService;
import com.sunny.mvpzhihu.test.common.TestDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock DatabaseHelper mMockDatabaseHelper;
    @Mock PreferencesHelper mMockPreferencesHelper;
    @Mock
    RetrofitService mMockRetrofitService;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(
                mMockRetrofitService,
                mMockPreferencesHelper,
                mMockDatabaseHelper);
    }

    @Test
    public void syncSubjectsEmitsValues() {
        List<Subject> subjects = Arrays.asList(
                TestDataFactory.makeSubject(TestDataFactory.randomUuid()),
                TestDataFactory.makeSubject(TestDataFactory.randomUuid()));
        stubSyncSubjectsHelperCalls(subjects);

        TestSubscriber<Subject> result = new TestSubscriber<>();
        mDataManager.syncSubjects().subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(subjects);
    }

    @Test
    public void syncSubjectsCallsApiAndDatabase() {
        List<Subject> subjects = Arrays.asList(
                TestDataFactory.makeSubject(TestDataFactory.randomUuid()),
                TestDataFactory.makeSubject(TestDataFactory.randomUuid()));
        stubSyncSubjectsHelperCalls(subjects);

        mDataManager.syncSubjects().subscribe();
        // Verify right calls to helper methods
        verify(mMockRetrofitService).getSubjects();
        verify(mMockDatabaseHelper).setSubjects(subjects);
    }

    @Test
    public void syncSubjectsDoesNotCallDatabaseWhenApiFails() {
        when(mMockRetrofitService.getSubjects().map(new Func1<InTheatersEntity, List<Subject>>() {
            @Override
            public List<Subject> call(InTheatersEntity inTheatersEntity) {
                return inTheatersEntity.subjects();
            }
        })).thenReturn(Observable.<List<Subject>>error(new RuntimeException()));

        mDataManager.syncSubjects().subscribe(new TestSubscriber<Subject>());
        // Verify right calls to helper methods
        verify(mMockRetrofitService).getSubjects();
        verify(mMockDatabaseHelper, never()).setSubjects(anyListOf(Subject.class));
    }

    private void stubSyncSubjectsHelperCalls(List<Subject> subjects) {
        // Stub calls to the subject service and database helper.
        when(mMockRetrofitService.getSubjects().map(new Func1<InTheatersEntity, List<Subject>>() {
            @Override
            public List<Subject> call(InTheatersEntity inTheatersEntity) {
                return inTheatersEntity.subjects();
            }
        })).thenReturn(Observable.just(subjects));
        when(mMockDatabaseHelper.setSubjects(subjects))
                .thenReturn(Observable.from(subjects));
    }

}
