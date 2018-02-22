package com.udacity.project.popularmovies.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.project.popularmovies.R;
import com.udacity.project.popularmovies.network.model.Trailer;
import com.udacity.project.popularmovies.ui.adapter.TrailerAdapter;

import java.util.ArrayList;

/**
 * Fragment that shows a list of Reviews.
 * <p>
 * Created by Amilcar Serrano <amilcar.sr8@gmail.com> on 2/19/18.
 */

public class TrailersFragment extends Fragment implements TrailerAdapter.OnTrailerClickListener {
    private final static String ARG_TRAILERS = "ARG_TRAILERS";

    public static TrailersFragment getInstance(ArrayList<Trailer> trailers) {
        TrailersFragment fragment = new TrailersFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARG_TRAILERS, trailers);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailers, container, false);

        if (getArguments() != null) {
            ArrayList<Trailer> trailers = getArguments().getParcelableArrayList(ARG_TRAILERS);

            RecyclerView recyclerView = view.findViewById(R.id.rv_trailers);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new TrailerAdapter(trailers, this));
        }

        return view;
    }


    @Override
    public void onVideoClicked(Trailer video) {
        if (getContext() != null) {
            playYoutubeVideo(getContext(), video.getKey());
        }
    }

    private static void playYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(context.getString(R.string.base_youtube_url), id)));

        if (appIntent.resolveActivity(context.getPackageManager()) == null) {
            context.startActivity(webIntent);
        } else {
            context.startActivity(appIntent);
        }

    }
}
