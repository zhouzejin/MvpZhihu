package com.sunny.mvpzhihu.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import com.sunny.mvpzhihu.BuildConfig;
import com.sunny.mvpzhihu.data.model.entity.InTheatersEntity;
import com.sunny.mvpzhihu.utils.factory.MyGsonTypeAdapterFactory;

public interface SubjectsService {

    String ENDPOINT = "https://api.douban.com/v2/";
    int DEFAULT_TIMEOUT = 5;

    @GET("movie/in_theaters")
    Observable<InTheatersEntity> getSubjects();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static SubjectsService newSubjectsService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClientBuilder.addInterceptor(logging);
                httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SubjectsService.ENDPOINT)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(SubjectsService.class);
        }
    }
}
