package com.sunny.mvpzhihu.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import com.sunny.mvpzhihu.data.model.bean.Subject;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
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
                Subject.SELECT_ALL)
                .mapToList(new Func1<Cursor, Subject>() {
                    @Override
                    public Subject call(Cursor cursor) {
                        return Subject.MAPPER.map(cursor);
                    }
                });
    }

}
