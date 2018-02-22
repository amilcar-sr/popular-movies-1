package com.udacity.project.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.project.popularmovies.R;
import com.udacity.project.popularmovies.network.model.Review;

import java.util.ArrayList;

/**
 * Adapter class that takes care of provide item views to the Review RecyclerView
 * <p>
 * Created by Amilcar Serrano <amilcar.sr8@gmail.com> on 1/4/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private final ArrayList<Review> mReviews;
    private final OnReviewClickListener mListener;

    //Interface that notifies when a review item has been clicked
    public interface OnReviewClickListener {
        void onReviewClicked(Review review);
    }

    public ReviewAdapter(ArrayList<Review> reviews, OnReviewClickListener listener) {
        this.mListener = listener;
        this.mReviews = reviews;
    }

    public ArrayList<Review> getReviews() {
        return mReviews;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.bind(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews == null ? 0 : mReviews.size();
    }

    /**
     * ViewHolder class for review items
     */
    class ReviewHolder extends RecyclerView.ViewHolder {
        final TextView reviewAuthor;
        final TextView reviewContent;

        ReviewHolder(View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.tv_review_author);
            reviewContent = itemView.findViewById(R.id.tv_review_content);
        }

        /**
         * Binds the review info with the UI elements
         *
         * @param review Review to be bound
         */
        void bind(final Review review) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onReviewClicked(review);
                }
            });
            reviewAuthor.setText(review.getAuthor());
            reviewContent.setText(review.getContent());
        }
    }
}
