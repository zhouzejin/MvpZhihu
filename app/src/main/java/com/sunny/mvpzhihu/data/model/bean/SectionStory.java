package com.sunny.mvpzhihu.data.model.bean;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * The type Section story.
 * Created by Zhou Zejin on 2017/6/14.
 */

@AutoValue
public abstract class SectionStory implements Parcelable {

    public abstract String date();
    public abstract String display_date();
    public abstract int id();
    public abstract String title();
    public abstract boolean multipic();
    public abstract List<String> images();

    public static TypeAdapter<SectionStory> typeAdapter(Gson gson) {
        return new AutoValue_SectionStory.GsonTypeAdapter(gson);
    }

}
