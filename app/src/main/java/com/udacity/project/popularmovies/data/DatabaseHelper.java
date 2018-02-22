package com.udacity.project.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class that makes easier to create the database and its tables
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "popularMovies.db";

    //Database version, must be updated if the database structure has changed.
    private static final int VERSION = 1;

    // Constructor
    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + MovieContract.FavoriteVideoEntry.TABLE_NAME + " (" +
                MovieContract.FavoriteVideoEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieContract.FavoriteVideoEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.FavoriteVideoEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MovieContract.FavoriteVideoEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                MovieContract.FavoriteVideoEntry.COLUMN_RATING + " INTEGER NOT NULL, " +
                MovieContract.FavoriteVideoEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.FavoriteVideoEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteVideoEntry.TABLE_NAME);
        onCreate(db);
    }
}

