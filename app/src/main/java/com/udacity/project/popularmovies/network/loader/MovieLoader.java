package com.udacity.project.popularmovies.network.loader;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.udacity.project.popularmovies.network.MovieService;
import com.udacity.project.popularmovies.network.NetworkManager;
import com.udacity.project.popularmovies.network.model.FeedResponse;
import com.udacity.project.popularmovies.network.model.Movie;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.ArrayList;

import retrofit2.Response;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Loader that returns a list of movies sorted based on the received sortMode param.
 */
public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    //IntDef used to validate the parameters passed to this Loader constructor
    //this way we make sure we're passing SORT_BY_RATE or SORT_BY_POPULAR
    @Retention(SOURCE)
    @IntDef({SORT_BY_RATE, SORT_BY_POPULAR})
    public @interface SortMode {
    }

    public static final String EXT_SORT_MODE = "EXT_SORT_MODE";
    private static final String POPULAR_ENDPOINT = "popular";
    private static final String TOP_RATED_ENDPOINT = "top_rated";

    public static final int SORT_BY_POPULAR = 0;
    public static final int SORT_BY_RATE = 1;

    //Indicates whether this loader will fetch popular or top rated movies
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
                response = movieService.fetchMovies(POPULAR_ENDPOINT).execute();
            } else {
                response = movieService.fetchMovies(TOP_RATED_ENDPOINT).execute();
            }

            if (response != null && response.body() != null) {
                return response.body().getResults();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
