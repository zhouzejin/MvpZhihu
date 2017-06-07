package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.bean.Editor;
import com.sunny.mvpzhihu.data.model.bean.Story;

import java.util.List;

/**
 * The type Theme detail entity.
 * Created by Zhou Zejin on 2017/6/7.
 */

@AutoValue
public abstract class ThemeDetailEntity implements Parcelable {

    public abstract String description();
    public abstract String background();
    public abstract int color();
    public abstract String name();
    public abstract String image();
    public abstract String image_source();
    public abstract List<Story> stories();
    public abstract List<Editor> editors();

    public static TypeAdapter<ThemeDetailEntity> typeAdapter(Gson gson) {
        return new AutoValue_ThemeDetailEntity.GsonTypeAdapter(gson);
    }

}
