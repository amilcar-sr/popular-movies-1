package com.udacity.project.pupularmovies.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.udacity.project.pupularmovies.R;
import com.udacity.project.pupularmovies.network.loader.MovieLoader;
import com.udacity.project.pupularmovies.network.model.Movie;
import com.udacity.project.pupularmovies.ui.adapter.MovieAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>, MovieAdapter.OnMovieClickListener {

    private final static String EXT_MOVIES = "EXT_MOVIES";

    //Movie adapter that takes care of binding the appropriate movie items into the RecyclerView
    private MovieAdapter mMovieAdapter;

    //Error message that indicates if something went wrong with the API request
    private TextView mInternetErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieAdapter = new MovieAdapter(this);

        mInternetErrorMessage = findViewById(R.id.tv_internet_error);
        RecyclerView movieRecycler = findViewById(R.id.rv_movie_recycler);
        movieRecycler.setHasFixedSize(true);
        movieRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        movieRecycler.setAdapter(mMovieAdapter);

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
                getSupportLoaderManager().restartLoader(0, getLoaderBundle(MovieLoader.SORT_BY_POPULAR), this);
                return true;
            case R.id.sort_by_rate:
                getSupportLoaderManager().restartLoader(1, getLoaderBundle(MovieLoader.SORT_BY_RATE), this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXT_MOVIES, mMovieAdapter.getMovies());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(EXT_MOVIES);
        if (mMovieAdapter != null) {
            mMovieAdapter.updateMovies(movies);
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
            mInternetErrorMessage.setVisibility(View.VISIBLE);
        } else {
            mMovieAdapter.updateMovies(data);
            mInternetErrorMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }

    @Override
    public void onMovieClicked(Movie movie) {
        startActivity(MovieDetailActivity.getInstance(this, movie));
    }
}
