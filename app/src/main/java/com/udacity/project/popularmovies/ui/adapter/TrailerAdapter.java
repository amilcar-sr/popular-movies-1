package com.udacity.project.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.project.popularmovies.network.model.Trailer;
import com.udacity.project.popularmovies.R;

import java.util.ArrayList;

/**
 * Adapter class that takes care of provide item views to the Trailer RecyclerView
 * <p>
 * Created by Amilcar Serrano <amilcar.sr8@gmail.com> on 1/4/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.VideoHolder> {
    private final ArrayList<Trailer> mTrailers;
    private final OnTrailerClickListener mListener;

    //Interface that notifies when a video item has been clicked
    public interface OnTrailerClickListener {
        void onVideoClicked(Trailer video);
    }

    public TrailerAdapter(ArrayList<Trailer> videos, OnTrailerClickListener listener) {
        this.mListener = listener;
        this.mTrailers = videos;
    }

    public ArrayList<Trailer> getVideos() {
        return mTrailers;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        holder.bind(mTrailers.get(position));
    }

    @Override
    public int getItemCount() {
        return mTrailers == null ? 0 : mTrailers.size();
    }

    /**
     * ViewHolder class for video items
     */
    class VideoHolder extends RecyclerView.ViewHolder {
        final TextView videoTitle;

        VideoHolder(View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.tv_video_name);
        }

        /**
         * Binds the video info with the UI elements
         *
         * @param video Trailer to be bound
         */
        void bind(final Trailer video) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onVideoClicked(video);
                }
            });
            videoTitle.setText(video.getName());
        }
    }
}
