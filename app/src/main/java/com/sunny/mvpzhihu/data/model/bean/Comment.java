package com.sunny.mvpzhihu.data.model.bean;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.pojo.ReplyTo;

/**
 * The type Comment.
 * Created by Zhou Zejin on 2017/6/2.
 */

@AutoValue
public abstract class Comment implements Parcelable {

    public abstract String author();
    public abstract String content();
    public abstract String avatar();
    public abstract long time();
    public abstract int id();
    public abstract int likes();
    @Nullable public abstract ReplyTo reply_to();

    public static Comment create(String author, String content, String avatar,
                                 long time, int id, int likes, ReplyTo reply_to) {
        return new AutoValue_Comment(author, content, avatar, time, id, likes, reply_to);
    }

    public static TypeAdapter<Comment> typeAdapter(Gson gson) {
        return new AutoValue_Comment.GsonTypeAdapter(gson);
    }

}
