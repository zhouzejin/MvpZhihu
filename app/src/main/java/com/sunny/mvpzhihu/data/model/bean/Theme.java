package com.sunny.mvpzhihu.data.model.bean;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * The type Theme.
 * Created by Zhou Zejin on 2017/6/6.
 */

@AutoValue
public abstract class Theme implements Parcelable {

    public abstract int color();
    public abstract String thumbnail();
    public abstract String description();
    public abstract int id();
    public abstract String name();

    public static Theme create(int color, String thumbnail, String description, int id, String name) {
        return new AutoValue_Theme(color,thumbnail, description, id, name);
    }

    public static TypeAdapter<Theme> typeAdapter(Gson gson) {
        return new AutoValue_Theme.GsonTypeAdapter(gson);
    }

}
