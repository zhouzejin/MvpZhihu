package com.sunny.mvpzhihu.data.model.pojo;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

@AutoValue
public abstract class Creative implements Parcelable {

    public abstract String url();
    public abstract int start_time();
    public abstract int type();
    public abstract String id();
    public abstract List<String> impression_tracks();

    public static Creative create(String url, int start_time, int type, String id,
                                  List<String> impression_tracks) {
        return new AutoValue_Creative(url, start_time, type, id, impression_tracks);
    }

    public static TypeAdapter<Creative> typeAdapter(Gson gson) {
        return new AutoValue_Creative.GsonTypeAdapter(gson);
    }

}
