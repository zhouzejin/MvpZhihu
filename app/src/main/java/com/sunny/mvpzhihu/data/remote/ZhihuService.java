package com.sunny.mvpzhihu.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sunny.mvpzhihu.BuildConfig;
import com.sunny.mvpzhihu.data.model.entity.CommentsEntity;
import com.sunny.mvpzhihu.data.model.entity.PrefetchLaunchImagesEntity;
import com.sunny.mvpzhihu.data.model.entity.StoriesBeforeEntity;
import com.sunny.mvpzhihu.data.model.entity.StoriesLastEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryExtraEntity;
import com.sunny.mvpzhihu.data.model.entity.StoryRecommendersEntity;
import com.sunny.mvpzhihu.data.model.entity.ThemesEntity;
import com.sunny.mvpzhihu.injection.qualifier.ApplicationContext;
import com.sunny.mvpzhihu.utils.NetworkUtil;
import com.sunny.mvpzhihu.utils.factory.MyGsonTypeAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ZhihuService {

    String ZHIHU_DAILY_URL = "http://news-at.zhihu.com/api/";
    int DEFAULT_TIMEOUT = 5;

    /**
     * 根据分辨率获取启动界面图片
     *
     * @param res
     * @return
     */
    @GET("7/prefetch-launch-images/{res}")
    Observable<PrefetchLaunchImagesEntity> getPrefetchLaunchImages(@Path("res") String res);

    /**
     * 获取最新的日报数据
     *
     * @return
     */
    @GET("4/stories/latest")
    Observable<StoriesLastEntity> getStoriesLast();

    /**
     * 根据日期获取对应的日报数据
     *
     * @param date
     * @return
     */
    @GET("4/stories/before/{date}")
    Observable<StoriesBeforeEntity> getStoriesBefore(@Path("date") String date);

    /**
     * 获取日报详情数据
     *
     * @param id
     * @return
     */
    @GET("4/story/{id}")
    Observable<StoryEntity> getStory(@Path("id") int id);

    /**
     * 获取日报的额外信息
     *
     * @param id
     * @return
     */
    @GET("4/story-extra/{id}")
    Observable<StoryExtraEntity> getStoryExtra(@Path("id") int id);

    /**
     * 获取日报的推荐者信息
     *
     * @param id
     * @return
     */
    @GET("4/story/{id}/recommenders")
    Observable<StoryRecommendersEntity> getStoryRecommenders(@Path("id") int id);

    /**
     * 查询日报的长评论
     *
     * @param id
     * @return
     */
    @GET("4/story/{id}/long-comments")
    Observable<CommentsEntity> getStoryLongComments(@Path("id") int id);

    /**
     * 查询日报的短评论
     *
     * @param id
     * @return
     */
    @GET("4/story/{id}/short-comments")
    Observable<CommentsEntity> getStoryShortComments(@Path("id") int id);

    /**
     * 获取主题日报
     *
     * @return
     */
    @GET("4/themes")
    Observable<ThemesEntity> getThemes();

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

        public static ZhihuService newZhihuService(@ApplicationContext final Context context) {
            // 缓存文件设置
            final String CACHE_FILE_NAME = "RetrofitHttpCache";
            final long CACHE_SIZE = 1024 * 1024 * 100;
            final Cache CACHE_FILE = new Cache(new File(context.getCacheDir(), CACHE_FILE_NAME),
                    CACHE_SIZE);

            // 设置HTTP缓存拦截器
            final int CACHE_TIME = 60 * 60 * 24 * 7;
            final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetworkUtil.isNetworkConnected(context)) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }

                    Response originalResponse = chain.proceed(request);
                    if (NetworkUtil.isNetworkConnected(context)) {
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale="
                                        + CACHE_TIME)
                                .removeHeader("Pragma")
                                .build();
                    }
                }
            };

            // REWRITE_CACHE_CONTROL_INTERCEPTOR拦截器需要同时设置networkInterceptors和interceptors
            OkHttpClient client = httpClientBuilder
                    .cache(CACHE_FILE)
                    .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ZhihuService.ZHIHU_DAILY_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(ZhihuService.class);
        }
    }

}
