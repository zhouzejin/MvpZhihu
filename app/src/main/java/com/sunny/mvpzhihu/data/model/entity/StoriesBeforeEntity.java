package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.bean.Story;

import java.util.List;

/**
 * Created by Zhou zejin on 2017/5/15.
 */

@AutoValue
public abstract class StoriesBeforeEntity implements Parcelable {

    public abstract String date();
    public abstract List<Story> stories();

    public static StoriesBeforeEntity create(String date, List<Story> stories) {
        return new AutoValue_StoriesBeforeEntity(date, stories);
    }

    public static TypeAdapter<StoriesBeforeEntity> typeAdapter(Gson gson) {
        return new AutoValue_StoriesBeforeEntity.GsonTypeAdapter(gson);
    }

}
