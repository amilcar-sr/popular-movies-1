package com.udacity.project.pupularmovies.network;

import com.udacity.project.pupularmovies.network.model.FeedResponse;
import com.udacity.project.pupularmovies.network.model.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface that provides Retrofit Calls for The Movie DB endpoints
 *
 * Created by amilcar on 1/4/18.
 */

public interface MovieService {

    /**
     * Call for popular movies endpoint
     * @return Call<FeedResponse>
     */
    @GET("3/movie/popular")
    Call<FeedResponse> fetchPopularMovies();

    /**
     * Call for top rated movies endpoint
     * @return Call<FeedResponse>
     */
    @GET("3/movie/top_rated")
    Call<FeedResponse> fetchTopRatedMovies();
}
