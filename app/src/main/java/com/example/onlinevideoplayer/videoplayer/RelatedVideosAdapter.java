package com.example.onlinevideoplayer.videoplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.onlinevideoplayer.R;
import com.example.onlinevideoplayer.videos.entity.VideoListData;

import java.util.List;

/**
 * Created by Saif on 5/12/2019.
 */
public class RelatedVideosAdapter extends RecyclerView.Adapter<RelatedVideosAdapter.RelatedViewHolder> {


    private Context mContext;
    private List<VideoListData> videosLists;
    private ItemClick itemClick;

    public RelatedVideosAdapter(Context mContext, List<VideoListData> videosLists, ItemClick itemClick) {
        this.mContext = mContext;
        this.videosLists = videosLists;
        this.itemClick = itemClick;
    }

    public void updateList(List<VideoListData> videosLists){
        this.videosLists = videosLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RelatedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.related_videos_list, viewGroup, false);
        return new RelatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedViewHolder myHolder, final int i) {
        if (videosLists != null && videosLists.size() > 0) {
            VideoListData listData = videosLists.get(i);
            myHolder.desc.setText(listData.getDescription());
            myHolder.title.setText(listData.getTitle());
            Glide.with(mContext)
                    .load(listData.getThumb())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(myHolder.thumbnail);
            myHolder.lyr_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClick(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (videosLists != null)
            return videosLists.size();
        else
            return 0;
    }

    public class RelatedViewHolder extends RecyclerView.ViewHolder {

        TextView desc, title;
        ImageView thumbnail;
        LinearLayout lyr_video;

        public RelatedViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            lyr_video = itemView.findViewById(R.id.lyr_video);
        }
    }

    public interface ItemClick{
        void onItemClick(int position);
    }
}
