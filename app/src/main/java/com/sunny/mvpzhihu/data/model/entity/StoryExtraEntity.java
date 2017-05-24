package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * The type Story extra entity.
 * Created by Zhou Zejin on 2017/5/24.
 */

@AutoValue
public abstract class StoryExtraEntity implements Parcelable {

    public abstract int long_comments();
    public abstract int popularity();
    public abstract int short_comments();
    public abstract int comments();

    public static StoryExtraEntity create(int long_comments, int popularity,
                                          int short_comments, int comments) {
        return new AutoValue_StoryExtraEntity(long_comments, popularity, short_comments, comments);
    }

    public static TypeAdapter<StoryExtraEntity> typeAdapter(Gson gson) {
        return new AutoValue_StoryExtraEntity.GsonTypeAdapter(gson);
    }

}
