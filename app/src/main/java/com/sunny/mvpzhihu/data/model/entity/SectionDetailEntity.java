package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.bean.SectionStory;

import java.util.List;

/**
 * The type Section detail entity.
 * Created by Zhou Zejin on 2017/6/14.
 */

@AutoValue
public abstract class SectionDetailEntity implements Parcelable {

    public abstract long timestamp();
    public abstract String name();
    public abstract List<SectionStory> stories();

    public static TypeAdapter<SectionDetailEntity> typeAdapter(Gson gson) {
        return new AutoValue_SectionDetailEntity.GsonTypeAdapter(gson);
    }

}
