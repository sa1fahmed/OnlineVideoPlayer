package com.example.onlinevideoplayer.videoplayer;

/**
 * Created by Saif on 5/11/2019.
 */
public interface CallBacks {

    void callbackObserver(Object obj);

    interface playerCallBack {
        void onItemClickOnItem(Integer albumId);

        void onPlayingEnd();
    }
}
