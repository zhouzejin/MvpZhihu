package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.bean.Story;
import com.sunny.mvpzhihu.data.model.bean.TopStory;

import java.util.List;

/**
 * Created by Zhou zejin on 2017/4/28.
 */

@AutoValue
public abstract class StoriesLastEntity implements Parcelable {

    public abstract String date();
    public abstract List<Story> stories();
    public abstract List<TopStory> top_stories();

    public static StoriesLastEntity create(String date, List<Story> stories,
                                           List<TopStory> top_stories) {
        return new AutoValue_StoriesLastEntity(date, stories, top_stories);
    }

    public static TypeAdapter<StoriesLastEntity> typeAdapter(Gson gson) {
        return new AutoValue_StoriesLastEntity.GsonTypeAdapter(gson);
    }

}
