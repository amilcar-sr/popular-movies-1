package com.udacity.project.popularmovies.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.udacity.project.popularmovies.R;
import com.udacity.project.popularmovies.network.loader.FavoriteLoader;
import com.udacity.project.popularmovies.network.loader.MovieLoader;
import com.udacity.project.popularmovies.network.model.Movie;
import com.udacity.project.popularmovies.ui.adapter.FavoriteAdapter;
import com.udacity.project.popularmovies.ui.adapter.MovieAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>, MovieAdapter.OnMovieClickListener, FavoriteAdapter.OnFavoriteClickListener {

    private final static String EXT_MOVIES = "EXT_MOVIES";
    private final static String EXT_SELECTED_LOADER = "EXT_SELECTED_LOADER";

    //Ids of loaders used in this screen
    private static final int POPULAR_LOADER_ID = 0;
    private static final int TOP_RATED_LOADER_ID = 1;
    private static final int FAVORITE_LOADER_ID = 2;

    private int mSelectedLoader = 0;

    //Movie adapter that takes care of binding the appropriate movie items into the RecyclerView
    private MovieAdapter mMovieAdapter;

    //Favorite adapter that takes care of binding the appropriate favorite items into the RecyclerView
    private FavoriteAdapter mFavoriteAdapter;

    //Message that indicates if no items were retrieved from the API or database.
    private TextView mEmptyListMessage;

    private RecyclerView mMovieRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieAdapter = new MovieAdapter(this);
        mFavoriteAdapter = new FavoriteAdapter(MainActivity.this);

        mEmptyListMessage = findViewById(R.id.tv_empty_list_message);
        mMovieRecycler = findViewById(R.id.rv_movie_recycler);
        mMovieRecycler.setHasFixedSize(true);
        mMovieRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mMovieRecycler.setAdapter(mMovieAdapter);

        if (savedInstanceState == null) {
            getSupportLoaderManager().restartLoader(0, getLoaderBundle(MovieLoader.SORT_BY_POPULAR), this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_popular:
                mSelectedLoader = POPULAR_LOADER_ID;
                getSupportLoaderManager().restartLoader(POPULAR_LOADER_ID, getLoaderBundle(MovieLoader.SORT_BY_POPULAR), this);
                return true;
            case R.id.sort_by_rate:
                mSelectedLoader = TOP_RATED_LOADER_ID;
                getSupportLoaderManager().restartLoader(TOP_RATED_LOADER_ID, getLoaderBundle(MovieLoader.SORT_BY_RATE), this);
                return true;
            case R.id.sort_by_favorite:
                mSelectedLoader = FAVORITE_LOADER_ID;
                getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, mFavoriteCallbacks);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXT_MOVIES, mMovieAdapter.getMovies());
        outState.putInt(EXT_SELECTED_LOADER, mSelectedLoader);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mSelectedLoader = savedInstanceState.getInt(EXT_SELECTED_LOADER);
        if (mSelectedLoader == POPULAR_LOADER_ID || mSelectedLoader == TOP_RATED_LOADER_ID) {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(EXT_MOVIES);
            if (mMovieAdapter != null) {
                mMovieAdapter.updateMovies(movies);
            }
        } else {
            getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, mFavoriteCallbacks);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private Bundle getLoaderBundle(@MovieLoader.SortMode int sortMode) {
        Bundle bundle = new Bundle();
        bundle.putInt(MovieLoader.EXT_SORT_MODE, sortMode);
        return bundle;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        int sortMode = args.getInt(MovieLoader.EXT_SORT_MODE);
        return new MovieLoader(this, sortMode);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        if (data == null) {
            mMovieAdapter.updateMovies(new ArrayList<Movie>());
            mEmptyListMessage.setText(R.string.internet_connection_error_message);
            mEmptyListMessage.setVisibility(View.VISIBLE);
        } else {
            mMovieAdapter.updateMovies(data);
            mEmptyListMessage.setVisibility(View.GONE);
        }

        if (!(mMovieRecycler.getAdapter() instanceof MovieAdapter)) {
            mMovieRecycler.setAdapter(mMovieAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }

    private final LoaderManager.LoaderCallbacks<Cursor> mFavoriteCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new FavoriteLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            if (data == null || data.getCount() == 0) {
                mEmptyListMessage.setText(R.string.no_favorites_message);
                mEmptyListMessage.setVisibility(View.VISIBLE);
            } else {
                mFavoriteAdapter.swapCursor(data);
                mEmptyListMessage.setVisibility(View.GONE);
            }

            if (!(mMovieRecycler.getAdapter() instanceof FavoriteAdapter)) {
                mMovieRecycler.setAdapter(mFavoriteAdapter);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    public void onMovieClicked(Movie movie) {
        startActivity(MovieDetailActivity.getInstance(this, movie));
    }

    @Override
    public void onFavoriteClicked(Movie movie) {
        startActivity(MovieDetailActivity.getInstance(this, movie));
    }
}
