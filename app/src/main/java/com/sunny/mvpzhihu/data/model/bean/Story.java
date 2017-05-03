package com.sunny.mvpzhihu.data.model.bean;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.squareup.sqldelight.ColumnAdapter;
import com.squareup.sqldelight.RowMapper;
import com.sunny.mvpzhihu.utils.factory.MyGsonTypeAdapterFactory;
import com.sunny.sql.StoryModel;

import java.util.List;

@AutoValue
public abstract class Story implements StoryModel, Parcelable {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();

    private static final ColumnAdapter IMAGES_ADAPTER = new ColumnAdapter<List<String>, String>() {
        @NonNull
        @Override
        public List<String> decode(String databaseValue) {
            return gson.fromJson(databaseValue, new TypeToken<List<String>>() {
            }.getType());
        }

        @Override
        public String encode(@NonNull List<String> value) {
            return gson.toJson(value);
        }
    };

    public static final Factory<Story> FACTORY = new Factory<>(
            new StoryModel.Creator<Story>() {
                @Override
                public Story create(int type, int id, @NonNull String ga_prefix,
                                    @NonNull String title, @Nullable Boolean multipic,
                                    @NonNull List<String> images) {
                    return new AutoValue_Story(type, id, ga_prefix, title, multipic, images);
                }
            }, IMAGES_ADAPTER);

    public static Builder builder() {
        return new AutoValue_Story.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder type(int type);

        public abstract Builder id(int id);

        public abstract Builder ga_prefix(String ga_prefix);

        public abstract Builder title(String title);

        public abstract Builder multipic(Boolean multipic);

        public abstract Builder images(List<String> images);

        public abstract Story build();
    }

    public static TypeAdapter<Story> typeAdapter(Gson gson) {
        return new AutoValue_Story.GsonTypeAdapter(gson);
    }

    public static final RowMapper<Long> MAPPER = FACTORY.selectExistsByIdMapper();

}
