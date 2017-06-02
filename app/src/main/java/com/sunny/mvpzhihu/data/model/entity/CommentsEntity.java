package com.sunny.mvpzhihu.data.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sunny.mvpzhihu.data.model.bean.Comment;

import java.util.List;

/**
 * The type Long comments entity.
 * Created by Zhou Zejin on 2017/6/2.
 */

@AutoValue
public abstract class CommentsEntity implements Parcelable {

    public abstract List<Comment> comments();

    public static CommentsEntity create(List<Comment> comments) {
        return new AutoValue_CommentsEntity(comments);
    }

    public static TypeAdapter<CommentsEntity> typeAdapter(Gson gson) {
        return new AutoValue_CommentsEntity.GsonTypeAdapter(gson);
    }

}
