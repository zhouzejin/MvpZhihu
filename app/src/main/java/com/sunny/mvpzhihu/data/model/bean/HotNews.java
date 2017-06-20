package com.sunny.mvpzhihu.data.model.bean;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * The type HotNews.
 * Created by Zhou Zejin on 2017/6/16.
 */

@AutoValue
public abstract class HotNews implements Parcelable {

    public abstract int news_id();
    public abstract String url();
    public abstract String thumbnail();
    public abstract String title();

    public static TypeAdapter<HotNews> typeAdapter(Gson gson) {
        return new AutoValue_HotNews.GsonTypeAdapter(gson);
    }

}
