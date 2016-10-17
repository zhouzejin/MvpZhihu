package com.sunny.mvpzhihu.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sunny.mvpzhihu.BuildConfig;
import com.sunny.mvpzhihu.data.model.entity.InTheatersEntity;
import com.sunny.mvpzhihu.utils.factory.MyGsonTypeAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface RetrofitService {

    String ENDPOINT = "https://api.douban.com/v2/";
    int DEFAULT_TIMEOUT = 5;

    @GET("movie/in_theaters")
    Observable<InTheatersEntity> getSubjects();

    /******** Helper class that sets up a new services *******/
    class Creator {
        static Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        static {
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClientBuilder.addInterceptor(logging);
            }
        }

        public static RetrofitService newRetrofitService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitService.ENDPOINT)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(RetrofitService.class);
        }
    }

}
