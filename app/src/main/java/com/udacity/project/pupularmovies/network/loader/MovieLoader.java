package com.udacity.project.pupularmovies.network.loader;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.udacity.project.pupularmovies.network.MovieService;
import com.udacity.project.pupularmovies.network.NetworkManager;
import com.udacity.project.pupularmovies.network.model.FeedResponse;
import com.udacity.project.pupularmovies.network.model.Movie;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.ArrayList;

import retrofit2.Response;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by amilcar on 1/4/18.
 */

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    @Retention(SOURCE)
    @IntDef({SORT_BY_RATE, SORT_BY_POPULAR})
    public @interface SortMode {
    }

    public static final String EXT_SORT_MODE = "EXT_SORT_MODE";

    public static final int SORT_BY_POPULAR = 0;
    public static final int SORT_BY_RATE = 1;

    private final int sortMode;


    public MovieLoader(@NonNull Context context, @SortMode int sortMode) {
        super(context);
        this.sortMode = sortMode;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        try {
            Response<FeedResponse> response;
            MovieService movieService = NetworkManager.getInstance().getMovieService();

            if (sortMode == SORT_BY_POPULAR) {
                response = movieService.fetchPopularMovies().execute();
            } else {
                response = movieService.fetchTopRatedMovies().execute();
            }

            if (response.body() != null) {
                return response.body().getResults();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
