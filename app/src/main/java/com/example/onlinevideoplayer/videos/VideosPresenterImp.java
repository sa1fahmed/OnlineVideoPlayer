package com.example.onlinevideoplayer.videos;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.onlinevideoplayer.videos.entity.VideoListData;

import java.util.List;

/**
 * Created by Saif on 5/5/2019.
 */

public class VideosPresenterImp implements VideosPresenter {

    VideosView mVideosView;

    public VideosPresenterImp(VideosView mVideosView){
        this.mVideosView = mVideosView;
    }

    @Override
    public void getDataFromAPI() {
        mVideosView.showLoading();
        AndroidNetworking.get("https://interview-e18de.firebaseio.com/media.json?print=pretty")
                .setPriority(Priority.LOW)
                .build()
                .getAsObjectList(VideoListData.class, new ParsedRequestListener<List<VideoListData>>() {
                    @Override
                    public void onResponse(List<VideoListData> listData) {
                        mVideosView.hideLoading();
                        mVideosView.onResponse(listData);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mVideosView.hideLoading();
                        mVideosView.onError(anError);
                    }
                });
    }
}
