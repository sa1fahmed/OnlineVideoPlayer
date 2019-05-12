package com.example.onlinevideoplayer.videos;

import com.androidnetworking.error.ANError;
import com.example.onlinevideoplayer.videos.entity.VideoListData;

import java.util.List;

/**
 * Created by Saif on 5/5/2019.
 */
public interface VideosView {

    void onResponse(List<VideoListData> listData);

    void onError(ANError anError);

    void showLoading();

    void hideLoading();

}
