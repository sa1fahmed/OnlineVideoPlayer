package com.example.onlinevideoplayer.videoplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.onlinevideoplayer.R;
import com.example.onlinevideoplayer.db.DBHelper;
import com.example.onlinevideoplayer.videos.entity.VideoListData;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saif on 5/12/2019.
 */
public class VideoPlayer extends AppCompatActivity implements VideoPlayerView, RelatedVideosAdapter.ItemClick {

    PlayerView mPlayerView;
    int position;
    public static List<VideoListData> listData;
    VideoListData currentEntity;
    ArrayList<String> videos;
    List<VideoListData> newList;
    DBHelper dbHelper;
    RelatedVideosAdapter mAdapter;
    VideoPlayerPresenterImp videoPlayerPresenterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        dbHelper = new DBHelper(this);
        mPlayerView = findViewById(R.id.player_view);
        ((TextView) findViewById(R.id.desc)).setMovementMethod(new ScrollingMovementMethod());
        position = getIntent().getIntExtra("position", -1);
        listData = (List<VideoListData>) getIntent().getSerializableExtra("videoList");
        videos = new ArrayList<>();
        newList = new ArrayList<>();
        for (VideoListData data : listData) {
            videos.add(data.getUrl());
        }
        videoPlayerPresenterImp = new VideoPlayerPresenterImp(this);
        onSetTitle();
        onSetAdapterData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayerPresenterImp.setPlayList();
    }

    @Override
    protected void onPause() {
        dbHelper.setLastTime(listData.get(position).getId(), ExoPlayerManager.getSharedInstance(this).getCurrentSeekTIme());
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        ExoPlayerManager.getSharedInstance(this).stopPlayer(false);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        ExoPlayerManager.getSharedInstance(this).stopPlayer(false);
        super.onStop();
    }

    @Override
    public void onItemClick(int position) {
        String id = newList.get(position).getId();
        int newPosition = -1;
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getId().equalsIgnoreCase(id)) {
                newPosition = i;
                break;
            }
        }
        startActivity(new Intent(this, VideoPlayer.class).putExtra("position", newPosition).putExtra("videoList", ((Serializable) listData)));
    }

    @Override
    public void onSetAdapterData() {
        RecyclerView recyclerView = findViewById(R.id.related_videos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newList.addAll(listData);
        currentEntity = newList.get(position);
        newList.remove(position);
        mAdapter = new RelatedVideosAdapter(this, newList, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSetPlayList() {
        mPlayerView.setPlayer(ExoPlayerManager.getSharedInstance(this).getPlayerView().getPlayer());
        // ExoPlayerManager.getSharedInstance(this).playStream(currentUrl);
        ExoPlayerManager.getSharedInstance(this).setPlaylist(videos, position, new CallBacks.playerCallBack() {
            @Override
            public void onItemClickOnItem(Integer albumId) {

            }

            @Override
            public void onPlayingEnd() {
                dbHelper.setLastTime(listData.get(position).getId(), 0);
                if (position == listData.size() - 1) {
                    return;
                }
                position++;
                newList = new ArrayList<>();
                newList.addAll(listData);
                newList.remove(position);
                mAdapter.updateList(newList);
                onSetTitle();
            }
        }, this);
        ExoPlayerManager.getSharedInstance(this).seekTo(dbHelper.getLastTime(listData.get(position).getId()));
        ExoPlayerManager.getSharedInstance(this).stopPlayer(false);
    }

    @Override
    public void onSetTitle() {
        ((TextView) findViewById(R.id.title)).setText(listData.get(position).getTitle());
        ((TextView) findViewById(R.id.desc)).setText(listData.get(position).getDescription());
    }
}
