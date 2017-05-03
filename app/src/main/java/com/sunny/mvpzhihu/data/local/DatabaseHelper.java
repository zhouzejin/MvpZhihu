package com.sunny.mvpzhihu.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.squareup.sqldelight.SqlDelightStatement;
import com.sunny.mvpzhihu.data.model.bean.Story;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.ui.main.DailyModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, Schedulers.immediate());
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<Subject> setSubjects(final Collection<Subject> newSubjects) {
        return Observable.create(new Observable.OnSubscribe<Subject>() {
            @Override
            public void call(Subscriber<? super Subject> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Subject.TABLE_NAME, null);
                    for (Subject subject : newSubjects) {
                        long result = mDb.insert(Subject.TABLE_NAME,
                                Subject.FACTORY.marshal(subject).asContentValues(),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) subscriber.onNext(subject);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Subject>> getSubjects() {
        return mDb.createQuery(Subject.TABLE_NAME,
                Subject.FACTORY.select_all().statement)
                .mapToList(new Func1<Cursor, Subject>() {
                    @Override
                    public Subject call(Cursor cursor) {
                        return Subject.MAPPER.map(cursor);
                    }
                });
    }

    public Observable<? extends List<DailyModel>> setDailies(final List<Story> stories,
                                                             final String date) {
        return Observable.create(new Observable.OnSubscribe<List<DailyModel>>() {
            @Override
            public void call(Subscriber<? super List<DailyModel>> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                List<DailyModel> dailyModels = new ArrayList<>();
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    for (Story story : stories) {
                        SqlDelightStatement query = Story.FACTORY.selectExistsById(story.id());
                        Cursor cursor = mDb.query(query.statement, query.args);
                        cursor.moveToFirst();
                        Long result = Story.MAPPER.map(cursor);
                        DailyModel dailyModel = new DailyModel(story, false, date);
                        if (result != 0L) dailyModel.setRead(true);
                        dailyModels.add(dailyModel);
                    }
                    transaction.markSuccessful();
                    subscriber.onNext(dailyModels);
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

}
