package com.udacity.project.pupularmovies.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that initializes a Retrofit client and holds a singleton instance of itself so it can be accessed easily.
 *
 * Created by amilcar on 1/4/18.
 */

public class NetworkManager {

    //Base url for The Movie DB's endpoints
    private final static String BASE_URL = "http://api.themoviedb.org/";

    /*
    Follow the steps described on the "Stage 1 - API Hints" section of this document in order to get
    your own API key.

    https://docs.google.com/document/d/1ZlN1fUsCSKuInLECcJkslIqvpKlP7jWL2TP9m6UiA6I/pub?embedded=true


     */
    private final static String API_KEY = "";

    //Interface that handles Retrofit's calls
    private MovieService mMovieService;

    //Singleton instances of NetworkManager
    private static NetworkManager INSTANCE;

    public static NetworkManager getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new NetworkManager();
        }

        return INSTANCE;
    }

    //Constructor that initializes a Retrofit client
    private NetworkManager() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", API_KEY).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mMovieService = retrofit.create(MovieService.class);
    }

    public MovieService getMovieService() {
        return mMovieService;
    }
}
