package com.udacity.project.popularmovies.ui.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.udacity.project.popularmovies.data.MovieContract;
import com.udacity.project.popularmovies.network.loader.FavoriteLoader;
import com.udacity.project.popularmovies.network.loader.ReviewLoader;
import com.udacity.project.popularmovies.network.loader.TrailerLoader;
import com.udacity.project.popularmovies.network.model.Movie;
import com.udacity.project.popularmovies.network.model.Review;
import com.udacity.project.popularmovies.network.model.Trailer;
import com.udacity.project.popularmovies.ui.fragment.ReviewsFragment;
import com.udacity.project.popularmovies.ui.fragment.TrailersFragment;
import com.udacity.project.popularmovies.R;
import com.udacity.project.popularmovies.ui.utils.GlideApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    private final static String EXT_MOVIE = "EXT_MOVIE";
    private final static String EXT_TRAILERS = "EXT_TRAILERS";
    private final static String EXT_REVIEWS = "EXT_REVIEWS";
    private final static String ARG_MOVIE_ID = "ARG_MOVIE_ID";

    private final static int TRAILER_LOADER_ID = 1000;
    private final static int REVIEW_LOADER_ID = 2000;
    private final static int FAVORITE_LOADER_ID = 3000;

    private ArrayList<Trailer> mTrailers;
    private ArrayList<Review> mReviews;

    private ViewPager mRelatedContentPager;
    private LikeButton mFavoriteButton;

    private Movie mMovie;

    public static Intent getInstance(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXT_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView movieImageView = findViewById(R.id.iv_movie_image);
        TextView movieReleaseTextView = findViewById(R.id.tv_movie_release_year);
        TextView movieRate = findViewById(R.id.tv_movie_rate);
        TextView moviePlotTextView = findViewById(R.id.tv_movie_plot);
        TabLayout tabLayout = findViewById(R.id.tl_movie_related);
        mRelatedContentPager = findViewById(R.id.vp_movie_related);
        mFavoriteButton = findViewById(R.id.btn_movie_favorite);

        tabLayout.setupWithViewPager(mRelatedContentPager);

        mMovie = getIntent().getParcelableExtra(EXT_MOVIE);

        if (savedInstanceState != null) {
            mTrailers = savedInstanceState.getParcelableArrayList(EXT_TRAILERS);
            mReviews = savedInstanceState.getParcelableArrayList(EXT_REVIEWS);
            setupPager();
        }

        if (mMovie != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mMovie.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            mFavoriteButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    addToFavorites();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    removeFromFavorites();
                }
            });

            GlideApp.with(this).load(mMovie.getPosterPath()).placeholder(R.drawable.ic_placeholder).into(movieImageView);

            movieReleaseTextView.setText(getReleaseYear(mMovie.getReleaseDate()));
            movieRate.setText(String.format(Locale.getDefault(), "%.2f/10", mMovie.getVoteAverage()));
            moviePlotTextView.setText(mMovie.getOverview());

            Bundle arguments = new Bundle();
            arguments.putLong(ARG_MOVIE_ID, mMovie.getId());

            if (mTrailers == null) {
                getSupportLoaderManager().initLoader(TRAILER_LOADER_ID, arguments, mTrailerCallbacks);
            }

            if (mReviews == null) {
                getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, arguments, mReviewCallbacks);
            }

            getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, mFavoriteCallback);
        }
    }

    private void addToFavorites() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.FavoriteVideoEntry.COLUMN_MOVIE_ID, mMovie.getId());
        contentValues.put(MovieContract.FavoriteVideoEntry.COLUMN_NAME, mMovie.getTitle());
        contentValues.put(MovieContract.FavoriteVideoEntry.COLUMN_IMAGE, mMovie.getPosterPath());
        contentValues.put(MovieContract.FavoriteVideoEntry.COLUMN_RATING, mMovie.getVoteAverage());
        contentValues.put(MovieContract.FavoriteVideoEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
        contentValues.put(MovieContract.FavoriteVideoEntry.COLUMN_SYNOPSIS, mMovie.getOverview());
        getContentResolver().insert(MovieContract.FavoriteVideoEntry.CONTENT_URI, contentValues);
    }

    private void removeFromFavorites() {
        Uri uri = MovieContract.FavoriteVideoEntry.CONTENT_URI;
        String movieId = String.valueOf(mMovie.getId());
        getContentResolver().delete(uri.buildUpon().appendPath(movieId).build(), null, null);
    }

    private String getReleaseYear(String dateString) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());

        String year = "";
        try {
            Date date = inputFormat.parse(dateString);
            year = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return year;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.share_trailer:
                if (mTrailers != null && mTrailers.size() > 0) {
                    ShareCompat.IntentBuilder.from(this)
                            .setType("text/plain")
                            .setChooserTitle(getString(R.string.share_trailer))
                            .setText(String.format(getString(R.string.base_youtube_url), mTrailers.get(0).getKey()))
                            .startChooser();
                } else {
                    Toast.makeText(this, "No Trailers available for sharing", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupPager() {
        if (mTrailers != null && mReviews != null) {
            mRelatedContentPager.setAdapter(new RelatedContentPagerAdapter(getSupportFragmentManager()));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (mReviews != null) {
            outState.putParcelableArrayList(EXT_REVIEWS, mReviews);
        }

        if (mTrailers != null) {
            outState.putParcelableArrayList(EXT_TRAILERS, mTrailers);
        }

        super.onSaveInstanceState(outState);
    }

    private final LoaderManager.LoaderCallbacks<ArrayList<Trailer>> mTrailerCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<Trailer>>() {
        @Override
        public Loader<ArrayList<Trailer>> onCreateLoader(int id, Bundle args) {
            long movieId = args.getLong(ARG_MOVIE_ID);
            return new TrailerLoader(MovieDetailActivity.this, movieId);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> data) {
            mTrailers = data;
            setupPager();
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Trailer>> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<ArrayList<Review>> mReviewCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<Review>>() {
        @Override
        public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {
            long movieId = args.getLong(ARG_MOVIE_ID);
            return new ReviewLoader(MovieDetailActivity.this, movieId);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> data) {
            mReviews = data;
            setupPager();
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Review>> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<Cursor> mFavoriteCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new FavoriteLoader(MovieDetailActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            boolean favorite = false;
            for (int i = 0; i < data.getCount(); i++) {
                int movieIdColumn = data.getColumnIndex(MovieContract.FavoriteVideoEntry.COLUMN_MOVIE_ID);

                data.moveToPosition(i);

                long movieId = data.getLong(movieIdColumn);
                if (movieId == mMovie.getId()) {
                    favorite = true;
                    break;
                }
            }

            mFavoriteButton.setLiked(favorite);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private class RelatedContentPagerAdapter extends FragmentPagerAdapter {

        RelatedContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ReviewsFragment.getInstance(mReviews);
                case 1:
                    return TrailersFragment.getInstance(mTrailers);
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.reviews_tab_title);
                case 1:
                    return getString(R.string.trailers_tab_title);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
