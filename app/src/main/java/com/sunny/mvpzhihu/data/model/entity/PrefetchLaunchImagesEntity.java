package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.pojo.Creative;

import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

@AutoValue
public abstract class PrefetchLaunchImagesEntity implements Parcelable {

    public abstract List<Creative> creatives();

    public static PrefetchLaunchImagesEntity create(List<Creative> creatives) {
        return new AutoValue_PrefetchLaunchImagesEntity(creatives);
    }

    public static TypeAdapter<PrefetchLaunchImagesEntity> typeAdapter(Gson gson) {
        return new AutoValue_PrefetchLaunchImagesEntity.GsonTypeAdapter(gson);
    }

}
