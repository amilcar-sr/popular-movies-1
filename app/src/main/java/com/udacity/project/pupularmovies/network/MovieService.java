package com.udacity.project.pupularmovies.network;

import com.udacity.project.pupularmovies.network.model.FeedResponse;
import com.udacity.project.pupularmovies.network.model.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by amilcar on 1/4/18.
 */

public interface MovieService {

    @GET("3/movie/popular")
    Call<FeedResponse> fetchPopularMovies();

    @GET("3/movie/top_rated")
    Call<FeedResponse> fetchTopRatedMovies();
}
