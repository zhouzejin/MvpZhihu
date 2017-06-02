package com.sunny.mvpzhihu.data.model.pojo;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * The type Reply to.
 * Created by Zhou Zejin on 2017/6/2.
 */

@AutoValue
public abstract class ReplyTo implements Parcelable {

    public abstract String content();
    public abstract int status();
    public abstract int id();
    public abstract String author();
    @Nullable public abstract String err_msg();

    public static ReplyTo create(String content, int status, int id, String author, String err_msg) {
        return new AutoValue_ReplyTo(content, status, id, author, err_msg);
    }

    public static TypeAdapter<ReplyTo> typeAdapter(Gson gson) {
        return new AutoValue_ReplyTo.GsonTypeAdapter(gson);
    }

}
