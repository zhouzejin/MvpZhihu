package com.sunny.mvpzhihu.data.model.bean;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * The type Editor.
 * Created by Zhou Zejin on 2017/6/1.
 */

@AutoValue
public abstract class Editor implements Parcelable {

    public abstract String bio();
    public abstract String title();
    public abstract int id();
    public abstract String avatar();
    public abstract String name();

    public static Editor create(String bio, String title, int id, String avatar, String name) {
        return new AutoValue_Editor(bio, title, id, avatar, name);
    }

    public static TypeAdapter<Editor> typeAdapter(Gson gson) {
        return new AutoValue_Editor.GsonTypeAdapter(gson);
    }

}
