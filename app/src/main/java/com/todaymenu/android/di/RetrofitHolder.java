package com.todaymenu.android.di;

import com.todaymenu.android.server.ApiService;
import com.todaymenu.android.utils.Constants;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Paul
 * @since 2017.09.26
 */

public class RetrofitHolder {

    private OkHttpClient mOkHttpClient;
    private Converter.Factory mConverter;
    private CallAdapter.Factory mAdapterFactory;

    private ApiService mApiService;

    RetrofitHolder(final OkHttpClient okHttpClient,
                   final Converter.Factory converter,
                   final CallAdapter.Factory adapterFactory) {
        mOkHttpClient = okHttpClient;
        mConverter = converter;
        mAdapterFactory = adapterFactory;
        buildApi();
    }

    public void buildApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(mConverter)
                .addCallAdapterFactory(mAdapterFactory)
                .baseUrl(Constants.ENDPOINT)
                .client(mOkHttpClient)
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
