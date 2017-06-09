package com.sunny.mvpzhihu.data.model.bean;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * The type Section.
 * Created by Zhou Zejin on 2017/6/9.
 */

@AutoValue
public abstract class Section implements Parcelable {

    @Nullable public abstract String description();
    public abstract int id();
    public abstract String name();
    @Nullable public abstract String thumbnail();

    public static TypeAdapter<Section> typeAdapter(Gson gson) {
        return new AutoValue_Section.GsonTypeAdapter(gson);
    }

}
