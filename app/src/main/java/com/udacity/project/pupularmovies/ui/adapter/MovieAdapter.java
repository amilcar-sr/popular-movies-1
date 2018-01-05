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
 * Created by amilcar on 1/4/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private ArrayList<Movie> movies;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClicked(Movie movie);
    }

    public MovieAdapter(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public void updateMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        final ImageView movieImage;
        final TextView movieTitle;

        MovieHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.iv_movie_image);
            movieTitle = itemView.findViewById(R.id.tv_movie_title);
        }

        void bind(final Movie movie) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMovieClicked(movie);
                }
            });
            Picasso.with(itemView.getContext()).load(movie.getPosterPath()).into(movieImage);
            movieTitle.setText(movie.getTitle());
        }
    }
}
