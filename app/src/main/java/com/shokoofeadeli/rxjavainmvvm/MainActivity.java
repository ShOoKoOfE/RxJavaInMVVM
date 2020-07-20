package com.shokoofeadeli.rxjavainmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ShOoKoOfE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        try {
            viewModel.makeFutureQuery().get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(TAG, "onSubscribe: called.");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.d(TAG, "onNext: got the response from server!");
                            try {
                                Log.d(TAG, "onNext: " + responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: ", e);
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: called.");
                        }
                    });

            viewModel.makeQuery().observeForever(new androidx.lifecycle.Observer<ResponseBody>() {
                @Override
                public void onChanged(ResponseBody responseBody) {
                    Log.d(TAG, "onNext: got the response from server!");
                    try {
                        Log.d(TAG, "onNext: " + responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}