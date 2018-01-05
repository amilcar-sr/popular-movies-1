package com.udacity.project.pupularmovies.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.project.pupularmovies.R;
import com.udacity.project.pupularmovies.network.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private final static String EXT_MOVIE = "EXT_MOVIE";

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
        TextView movieReleaseTextView = findViewById(R.id.tv_movie_release_date);
        TextView movieVoteTextView = findViewById(R.id.tv_movie_vote_average);
        TextView moviePlotTextView = findViewById(R.id.tv_movie_plot);

        Movie movie = getIntent().getParcelableExtra(EXT_MOVIE);

        if (movie != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(movie.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            Picasso.with(this).load(movie.getPosterPath()).into(movieImageView);
            movieReleaseTextView.setText(String.format(getString(R.string.release_date), movie.getReleaseDate()));
            movieVoteTextView.setText(String.format(getString(R.string.vote_average), movie.getVoteAverage()));
            moviePlotTextView.setText(movie.getOverview());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
