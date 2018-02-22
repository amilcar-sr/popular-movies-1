package com.udacity.project.popularmovies.network.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.udacity.project.popularmovies.data.MovieContract;

/**
 * Loader that returns the Favorite info in a Cursor
 */
public class FavoriteLoader extends AsyncTaskLoader<Cursor> {

    private final static String TAG = "FavoriteLoader";

    private Cursor mFavoriteData = null;

    public FavoriteLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mFavoriteData != null) {
            deliverResult(mFavoriteData);
        } else {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {


        try {
            return getContext().getContentResolver().query(MovieContract.FavoriteVideoEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

        } catch (Exception e) {
            Log.e(TAG, "Failed to asynchronously load data.");
            e.printStackTrace();
            return null;
        }
    }

    public void deliverResult(Cursor data) {
        mFavoriteData = data;
        super.deliverResult(data);
    }
}
