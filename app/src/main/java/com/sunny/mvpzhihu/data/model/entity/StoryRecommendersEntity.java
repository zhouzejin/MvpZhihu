package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.bean.Editor;

import java.util.List;

/**
 * The type Story recommenders entity.
 * Created by Zhou Zejin on 2017/6/1.
 */

@AutoValue
public abstract class StoryRecommendersEntity implements Parcelable {

    public abstract int item_count();
    public abstract List<Object> items();
    public abstract List<Editor> editors();

    public static StoryRecommendersEntity create(int item_count, List<Object> items, List<Editor> editors) {
        return new AutoValue_StoryRecommendersEntity(item_count, items, editors);
    }

    public static TypeAdapter<StoryRecommendersEntity> typeAdapter(Gson gson) {
        return new AutoValue_StoryRecommendersEntity.GsonTypeAdapter(gson);
    }

}
