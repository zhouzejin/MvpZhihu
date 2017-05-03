package com.sunny.mvpzhihu.data.model.bean;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class TopStory implements Parcelable {

    public abstract String image();
    public abstract int type();
    public abstract int id();
    public abstract String ga_prefix();
    public abstract String title();

    public static TopStory create(String image, int type, int id, String ga_prefix, String title) {
        return new AutoValue_TopStory(image, type, id, ga_prefix, title);
    }

    public static TypeAdapter<TopStory> typeAdapter(Gson gson) {
        return new AutoValue_TopStory.GsonTypeAdapter(gson);
    }

}
