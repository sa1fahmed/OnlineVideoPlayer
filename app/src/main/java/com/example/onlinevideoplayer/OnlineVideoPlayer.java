package com.example.onlinevideoplayer;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by Saif on 5/4/2019.
 */
public class OnlineVideoPlayer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());

    }
}
