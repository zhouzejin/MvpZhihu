package com.sunny.mvpzhihu.data.model.bean;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.squareup.sqldelight.ColumnAdapter;
import com.squareup.sqldelight.RowMapper;
import com.sunny.sql.SubjectModel;

import java.util.List;

import com.sunny.mvpzhihu.data.model.pojo.Image;
import com.sunny.mvpzhihu.data.model.pojo.Person;
import com.sunny.mvpzhihu.data.model.pojo.Rating;
import com.sunny.mvpzhihu.utils.factory.MyGsonTypeAdapterFactory;

/**
 * Created by Zhou Zejin on 2016/9/21.
 */

@AutoValue
public abstract class Subject implements SubjectModel, Parcelable, Comparable<Subject> {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();

    private static final ColumnAdapter RATING_ADAPTER = new ColumnAdapter<Rating>() {
        @Override
        public Rating map(Cursor cursor, int columnIndex) {
            return gson.fromJson(cursor.getString(columnIndex), Rating.class);
        }

        @Override
        public void marshal(ContentValues values, String key, Rating value) {
            values.put(key, gson.toJson(value));
        }
    };

    private static final ColumnAdapter GENRES_ADAPTER = new ColumnAdapter<List<String>>() {
        @Override
        public List<String> map(Cursor cursor, int columnIndex) {
            return gson.fromJson(cursor.getString(columnIndex),
                    new TypeToken<List<String>>() {}.getType());
        }

        @Override
        public void marshal(ContentValues values, String key, List<String> value) {
            values.put(key, gson.toJson(value));
        }
    };

    private static final ColumnAdapter PERSONS_ADAPTER = new ColumnAdapter<List<Person>>() {
        @Override
        public List<Person> map(Cursor cursor, int columnIndex) {
            return gson.fromJson(cursor.getString(columnIndex),
                    new TypeToken<List<Person>>() {}.getType());
        }

        @Override
        public void marshal(ContentValues values, String key, List<Person> value) {
            values.put(key, gson.toJson(value));
        }
    };

    private static final ColumnAdapter IMAGE_ADAPTER = new ColumnAdapter<Image>() {
        @Override
        public Image map(Cursor cursor, int columnIndex) {
            return gson.fromJson(cursor.getString(columnIndex), Image.class);
        }

        @Override
        public void marshal(ContentValues values, String key, Image value) {
            values.put(key, gson.toJson(value));
        }
    };

    public static final Factory<Subject> FACTORY = new Factory<>(
            new SubjectModel.Creator<Subject>() {
                @Override
                public Subject create(@NonNull String id, @NonNull Rating rating,
                                      @NonNull List<String> genres, @NonNull String title,
                                      @NonNull List<Person> casts, int collect_count,
                                      @NonNull String original_title, @NonNull String subtype,
                                      @NonNull List<Person> directors, @NonNull String year,
                                      @NonNull Image images, @NonNull String alt) {
                    return new AutoValue_Subject(id, rating, genres, title, casts, collect_count,
                            original_title, subtype, directors, year, images, alt);
                }
            },
            RATING_ADAPTER,
            GENRES_ADAPTER,
            PERSONS_ADAPTER,
            PERSONS_ADAPTER,
            IMAGE_ADAPTER);

    public static final RowMapper<Subject> MAPPER = FACTORY.select_allMapper();

    public static Builder builder() {
        return new AutoValue_Subject.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder rating(Rating rating);

        public abstract Builder genres(List<String> genres);

        public abstract Builder title(String title);

        public abstract Builder casts(List<Person> casts);

        public abstract Builder collect_count(int collect_count);

        public abstract Builder original_title(String original_title);

        public abstract Builder subtype(String subtype);

        public abstract Builder directors(List<Person> directors);

        public abstract Builder year(String year);

        public abstract Builder images(Image images);

        public abstract Builder alt(String alt);

        public abstract Subject build();
    }

    public static TypeAdapter<Subject> typeAdapter(Gson gson) {
        return new AutoValue_Subject.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull Subject another) {
        return title().compareTo(another.title());
    }

}
