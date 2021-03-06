package com.shokoofeadeli.rxjavainmvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Repository {
    private static Repository instance;

    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }

    public LiveData<ResponseBody> makeReactiveQuery(){
        HashMap<String,String> map = new HashMap<>();
        map.put("action", "login");
        map.put("username","shokoofe");
        map.put("password","adeli");
        return LiveDataReactiveStreams.fromPublisher(ServiceGenerator.getServiceApi()
                .GetUserInfo(map)
                .subscribeOn(Schedulers.io()));
    }

    public Future<Observable<ResponseBody>> makeFutureQuery(){
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<ResponseBody>> myNetworkCallable = new Callable<Observable<ResponseBody>>() {
            @Override
            public Observable<ResponseBody> call() throws Exception {
                HashMap<String,String> map = new HashMap<>();
                map.put("action", "login");
                map.put("username","shokoofe");
                map.put("password","adeli");
                return ServiceGenerator.getServiceApi().GetUser(map);
            }
        };


        final Future<Observable<ResponseBody>> futureObservable = new Future<Observable<ResponseBody>>(){

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                if(mayInterruptIfRunning){
                    executor.shutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executor.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executor.isTerminated();
            }

            @Override
            public Observable<ResponseBody> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<ResponseBody> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(timeout, unit);
            }
        };

        return futureObservable;

    }
}
