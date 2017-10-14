package com.todaymenu.android.di;

import android.support.annotation.VisibleForTesting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.todaymenu.android.BuildConfig;
import com.todaymenu.android.Shaorma;
import com.todaymenu.android.server.NetworkInterceptor;
import com.todaymenu.android.utils.Constants;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private Shaorma mShaorma;

    public ApiModule(Shaorma shaorma) {
        mShaorma = shaorma;
    }

    @Provides
    @VisibleForTesting
    static Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
    }

    @Provides
    Cache provideCache() {
        File httpCacheDirectory = new File(mShaorma.getCacheDir().getAbsolutePath(), "HttpCache");
        return new Cache(httpCacheDirectory, Constants.DISK_CACHE_SIZE);
    }

    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    @Provides
    OkHttpClient provideOkHttpClient(Cache cache,
                                     HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new NetworkInterceptor())
                .build();
    }

    @Provides
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    @Provides
    Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    RetrofitHolder provideRetrofitHolder(final OkHttpClient okHttpClient,
                                         final Converter.Factory converter,
                                         final CallAdapter.Factory adapterFactory) {
        return new RetrofitHolder(okHttpClient, converter, adapterFactory);
    }
}