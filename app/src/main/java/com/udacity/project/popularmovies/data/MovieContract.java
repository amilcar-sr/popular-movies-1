package com.udacity.project.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract that defines ContentProvider and database properties used in this app.
 *
 * Created by Amilcar Serrano <amilcar.sr8@gmail.com> on 2/19/18.
 */
public class MovieContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.udacity.project.popularmovies";

    // The base content URI = "content://" + <authority>
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "videos" directory
    public static final String PATH_FAVORITE_VIDEOS = "favorite_videos";

    /* VideoEntry is an inner class that defines the contents of the video table */
    public static final class FavoriteVideoEntry implements BaseColumns {

        // VideoEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_VIDEOS).build();


        // Trailer table and column names
        public static final String TABLE_NAME = "favorite_videos";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
    }
}
