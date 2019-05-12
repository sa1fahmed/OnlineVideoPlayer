package com.example.onlinevideoplayer.videos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.onlinevideoplayer.R;
import com.example.onlinevideoplayer.videoplayer.VideoPlayer;
import com.example.onlinevideoplayer.videos.entity.VideoListData;

import java.io.Serializable;
import java.util.List;

public class VideosList extends AppCompatActivity implements VideosView, VideoListAdapter.ItemClick {

    RecyclerView recyclerView;
    VideoListAdapter mAdapter;
    List<VideoListData> videoList;
    ProgressDialog loading;
    VideosPresenterImp videosPresenterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_list);
        recyclerView = findViewById(R.id.recycler);
        videosPresenterImp = new VideosPresenterImp(this);
        videosPresenterImp.getDataFromAPI();
    }


    @Override
    public void onItemClick(int position) {
            startActivity(new Intent(this, VideoPlayer.class).putExtra("position",position).putExtra("videoList", ((Serializable) videoList)));
    }

    @Override
    public void onResponse(List<VideoListData> listData) {
        videoList = listData;
        mAdapter = new VideoListAdapter(VideosList.this, videoList,VideosList.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(VideosList.this));
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onError(ANError anError) {
        Toast.makeText(this, anError.getErrorBody(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (loading == null)
            loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Loading...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
    }

    @Override
    public void hideLoading() {
        if (loading != null && loading.isShowing())
            loading.dismiss();
    }
}
