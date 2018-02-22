package com.udacity.project.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.project.popularmovies.network.model.Review;
import com.udacity.project.popularmovies.ui.adapter.ReviewAdapter;
import com.udacity.project.popularmovies.R;

import java.util.ArrayList;

/**
 * Fragment that shows a list of Reviews.
 * <p>
 * Created by Amilcar Serrano <amilcar.sr8@gmail.com> on 2/19/18.
 */
public class ReviewsFragment extends Fragment implements ReviewAdapter.OnReviewClickListener {
    private final static String ARG_REVIEWS = "ARG_REVIEWS";

    public static ReviewsFragment getInstance(ArrayList<Review> reviews) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARG_REVIEWS, reviews);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        if (getArguments() != null) {
            ArrayList<Review> reviews = getArguments().getParcelableArrayList(ARG_REVIEWS);

            RecyclerView recyclerView = view.findViewById(R.id.rv_reviews);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new ReviewAdapter(reviews, this));
        }
        return view;
    }

    @Override
    public void onReviewClicked(Review review) {

    }
}
