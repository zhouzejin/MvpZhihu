package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.sunny.mvpzhihu.data.model.bean.Section;

import java.util.List;

/**
 * The type Sections entity.
 * Created by Zhou Zejin on 2017/6/9.
 */

@AutoValue
public abstract class SectionsEntity implements Parcelable {

    @SerializedName("data") public abstract List<Section> sections();

    public static TypeAdapter<SectionsEntity> typeAdapter(Gson gson) {
        return new AutoValue_SectionsEntity.GsonTypeAdapter(gson);
    }

}
