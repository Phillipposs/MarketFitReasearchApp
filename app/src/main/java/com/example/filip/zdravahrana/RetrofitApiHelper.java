package com.example.filip.zdravahrana;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitApiHelper {

    private static RetrofitAPI retrofitAPI;

    private static OkHttpClient client;

    public static RetrofitAPI getRetrofitAPI() {

        if(retrofitAPI == null) {
            refreshRetrofitApi();
        }

        return retrofitAPI;
    }

    private static void refreshRetrofitApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        if(ApiConstants.infiniteTimeout) {
            client = new OkHttpClient.Builder()
                    .readTimeout(0, TimeUnit.SECONDS)
                    .connectTimeout(0, TimeUnit.SECONDS)
                    .addInterceptor(interceptor).build();
        }
        else {
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor).build();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiUrls.OVERPASS_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

}

/*
HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        if(Const.infiniteTimeout) {
            client = new OkHttpClient.Builder()
                    .readTimeout(0, TimeUnit.SECONDS)
                    .connectTimeout(0, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new OAuthInterceptor(getConsumer())).build();
        }
        else {
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new OAuthInterceptor(getConsumer())).build();
        }

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
 */
