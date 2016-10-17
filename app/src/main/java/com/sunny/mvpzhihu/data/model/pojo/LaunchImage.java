package com.sunny.mvpzhihu.data.model.pojo;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by Zhou Zejin on 2016/10/17.
 */

@AutoValue
public abstract class LaunchImage implements Parcelable {

    public abstract String text();
    public abstract String img();

    public static LaunchImage create(String text, String img) {
        return new AutoValue_LaunchImage(text, img);
    }

    public static TypeAdapter<LaunchImage> typeAdapter(Gson gson) {
        return new AutoValue_LaunchImage.GsonTypeAdapter(gson);
    }

}
