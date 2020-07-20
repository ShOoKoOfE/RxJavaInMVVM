package com.shokoofeadeli.rxjavainmvvm;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String API_BASE_URL = "http://169.254.131.120/online-shop/";

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    private static ServiceApi serviceApi = retrofit.create(ServiceApi.class);
    public static ServiceApi getServiceApi(){
        return serviceApi;
    }
}








