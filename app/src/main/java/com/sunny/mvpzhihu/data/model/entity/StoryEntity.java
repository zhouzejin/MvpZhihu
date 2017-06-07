package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * The type Story entity.
 * Created by Zhou Zejin on 2017/5/23.
 */

@AutoValue
public abstract class StoryEntity implements Parcelable {

    public abstract String body();
    @Nullable public abstract String image_source();
    public abstract String title();
    @Nullable public abstract String image();
    public abstract String share_url();
    public abstract String ga_prefix();
    public abstract int type();
    public abstract int id();
    public abstract List<String> js();
    public abstract List<String> images();
    public abstract List<String> css();

    public static StoryEntity create(String body, String image_source, String title, String image,
                                     String share_url, String ga_prefix, int type, int id,
                                     List<String> js, List<String> images, List<String> css) {
        return new AutoValue_StoryEntity(body, image_source, title, image, share_url, ga_prefix,
                type, id, js, images, css);
    }

    public static TypeAdapter<StoryEntity> typeAdapter(Gson gson) {
        return new AutoValue_StoryEntity.GsonTypeAdapter(gson);
    }

}
