package com.udacity.project.popularmovies.network;

import com.udacity.project.popularmovies.network.model.FeedResponse;
import com.udacity.project.popularmovies.network.model.ReviewResponse;
import com.udacity.project.popularmovies.network.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface that provides Retrofit Calls for The Movie DB endpoints
 * <p>
 * Created by Amilcar Serrano <amilcar.sr8@gmail.com> on 1/4/18.
 */

public interface MovieService {

    /**
     * Call for popular movies endpoint
     *
     * @return Call<FeedResponse>
     */
    @GET("3/movie/{sort_by}")
    Call<FeedResponse> fetchMovies(@Path("sort_by") String sortBy);

    /**
     * Call for movie trailers
     *
     * @param id Movie id
     * @return Call<TrailerResponse>
     */
    @GET("3/movie/{id}/videos")
    Call<TrailerResponse> fetchTrailers(@Path("id") long id);

    /**
     * Call for movie reviews
     *
     * @param id Movie id
     * @return Call<ReviewResponse>
     */
    @GET("3/movie/{id}/reviews")
    Call<ReviewResponse> fetchReviews(@Path("id") long id);
}
