package com.udacity.project.popularmovies.network.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.udacity.project.popularmovies.network.MovieService;
import com.udacity.project.popularmovies.network.model.Review;
import com.udacity.project.popularmovies.network.NetworkManager;
import com.udacity.project.popularmovies.network.model.ReviewResponse;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * Loader that returns a list of reviews for the specified movie by the movieId param.
 */
public class ReviewLoader extends AsyncTaskLoader<ArrayList<Review>> {

    private final long movieId;

    public ReviewLoader(@NonNull Context context, long movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Review> loadInBackground() {
        try {
            Response<ReviewResponse> response;
            MovieService movieService = NetworkManager.getInstance().getMovieService();

            response = movieService.fetchReviews(movieId).execute();

            if (response != null && response.body() != null) {
                return response.body().getResults();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
