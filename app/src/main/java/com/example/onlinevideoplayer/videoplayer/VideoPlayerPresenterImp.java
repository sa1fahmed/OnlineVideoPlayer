package com.example.onlinevideoplayer.videoplayer;

/**
 * Created by Saif on 5/12/2019.
 */
public class VideoPlayerPresenterImp implements VideoPlayerPresenter {

    VideoPlayerView mVideoView;

    public VideoPlayerPresenterImp(VideoPlayerView mVideoView) {
        this.mVideoView = mVideoView;
    }

    @Override
    public void setPlayList() {
        mVideoView.onSetPlayList();
    }
}
