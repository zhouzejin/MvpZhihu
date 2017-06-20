package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.bean.HotNews;

import java.util.List;

/**
 * The type News hot entity.
 * Created by Zhou Zejin on 2017/6/16.
 */

@AutoValue
public abstract class NewsHotEntity implements Parcelable {

    public abstract List<HotNews> recent();

    public static TypeAdapter<NewsHotEntity> typeAdapter(Gson gson) {
        return new AutoValue_NewsHotEntity.GsonTypeAdapter(gson);
    }

}
