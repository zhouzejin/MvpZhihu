package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.bean.Theme;

import java.util.List;

/**
 * The type Themes entity.
 * Created by Zhou Zejin on 2017/6/6.
 */

@AutoValue
public abstract class ThemesEntity implements Parcelable {

    public abstract int limit();
    public abstract List<Object> subscribed();
    public abstract List<Theme> others();

    public static ThemesEntity create(int limit, List<Object> subscribed, List<Theme> others) {
        return new AutoValue_ThemesEntity(limit, subscribed, others);
    }

    public static TypeAdapter<ThemesEntity> typeAdapter(Gson gson) {
        return new AutoValue_ThemesEntity.GsonTypeAdapter(gson);
    }

}
