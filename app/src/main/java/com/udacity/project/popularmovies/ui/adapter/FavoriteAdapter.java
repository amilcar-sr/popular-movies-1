package com.udacity.project.popularmovies.ui.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.project.popularmovies.R;
import com.udacity.project.popularmovies.data.MovieContract;
import com.udacity.project.popularmovies.network.model.Movie;
import com.udacity.project.popularmovies.ui.utils.GlideApp;

/**
 * Adapter class that takes care of provide item views to the Favorite RecyclerView
 * <p>
 * Created by Amilcar Serrano <amilcar.sr8@gmail.com> on 2/20/18.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private final MovieAdapter.OnMovieClickListener mListener;

    //Interface that notifies when a favorite item has been clicked
    public interface OnFavoriteClickListener {
        void onFavoriteClicked(Movie movie);
    }

    public FavoriteAdapter(MovieAdapter.OnMovieClickListener listener) {
        this.mListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new FavoriteViewHolder that holds the view for each task
     */
    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        return new FavoriteViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(MovieContract.FavoriteVideoEntry.COLUMN_MOVIE_ID);
        int titleColumn = mCursor.getColumnIndex(MovieContract.FavoriteVideoEntry.COLUMN_NAME);
        int imageColumn = mCursor.getColumnIndex(MovieContract.FavoriteVideoEntry.COLUMN_IMAGE);
        int ratingColumn = mCursor.getColumnIndex(MovieContract.FavoriteVideoEntry.COLUMN_RATING);
        int releaseDateColumn = mCursor.getColumnIndex(MovieContract.FavoriteVideoEntry.COLUMN_RELEASE_DATE);
        int synopsisColumn = mCursor.getColumnIndex(MovieContract.FavoriteVideoEntry.COLUMN_SYNOPSIS);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String title = mCursor.getString(titleColumn);
        String image = mCursor.getString(imageColumn);
        float rating = mCursor.getFloat(ratingColumn);
        String releaseDate = mCursor.getString(releaseDateColumn);
        String synopsis = mCursor.getString(synopsisColumn);


        //Set values
        Movie movie = new Movie(id, title, image, rating, synopsis, releaseDate);
        holder.bind(movie);
    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public void swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return;
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
    }


    // Inner class for creating ViewHolders
    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        final TextView movieTitle;
        final ImageView movieImage;

        FavoriteViewHolder(View itemView) {
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
            GlideApp.with(itemView.getContext()).load(movie.getPosterPath()).placeholder(R.drawable.ic_placeholder).into(movieImage);
            movieTitle.setText(movie.getTitle());
        }
    }
}