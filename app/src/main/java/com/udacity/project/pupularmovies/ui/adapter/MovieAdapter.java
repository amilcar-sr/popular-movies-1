package com.udacity.project.pupularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.project.pupularmovies.R;
import com.udacity.project.pupularmovies.network.model.Movie;

import java.util.ArrayList;

/**
 * Adapter class that takes care of provide item views to the RecyclerView
 * <p>
 * Created by amilcar on 1/4/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private ArrayList<Movie> mMovies;
    private OnMovieClickListener mListener;

    //Interface that notifies when a movie item has been clicked
    public interface OnMovieClickListener {
        void onMovieClicked(Movie movie);
    }

    public MovieAdapter(OnMovieClickListener listener) {
        this.mListener = listener;
    }

    public void updateMovies(ArrayList<Movie> movies) {
        this.mMovies = movies;
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        holder.bind(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    /**
     * ViewHolder class for movie items
     */
    class MovieHolder extends RecyclerView.ViewHolder {
        final ImageView movieImage;
        final TextView movieTitle;

        MovieHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.iv_movie_image);
            movieTitle = itemView.findViewById(R.id.tv_movie_title);
        }

        /**
         * Binds the movie info with the UI elements
         *
         * @param movie Movie to be bound
         */
        void bind(final Movie movie) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onMovieClicked(movie);
                }
            });
            Picasso.with(itemView.getContext()).load(movie.getPosterPath()).into(movieImage);
            movieTitle.setText(movie.getTitle());
        }
    }
}
