package com.shokoofeadeli.rxjavainmvvm;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

interface ServiceApi {
    @GET("services.php")
    Observable<ResponseBody> GetUser(@QueryMap HashMap<String,String> map);

    @GET("services.php")
    Flowable<ResponseBody> GetUserInfo(@QueryMap HashMap<String,String> map);
}


    