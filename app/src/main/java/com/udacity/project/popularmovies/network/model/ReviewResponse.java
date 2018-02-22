package com.udacity.project.popularmovies.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * ReviewResponse model
 */
public class ReviewResponse implements Parcelable {

    @SerializedName("id")
    private long id;

    @SerializedName("page")
    private long page;

    @SerializedName("results")
    private ArrayList<Review> results = null;

    @SerializedName("total_pages")
    private long totalPages;

    @SerializedName("total_results")
    private long totalResults;

    public long getId() {
        return id;
    }

    public long getPage() {
        return page;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.page);
        dest.writeTypedList(this.results);
        dest.writeLong(this.totalPages);
        dest.writeLong(this.totalResults);
    }

    public ReviewResponse() {
    }

    private ReviewResponse(Parcel in) {
        this.id = in.readLong();
        this.page = in.readLong();
        this.results = in.createTypedArrayList(Review.CREATOR);
        this.totalPages = in.readLong();
        this.totalResults = in.readLong();
    }

    public static final Parcelable.Creator<ReviewResponse> CREATOR = new Parcelable.Creator<ReviewResponse>() {
        @Override
        public ReviewResponse createFromParcel(Parcel source) {
            return new ReviewResponse(source);
        }

        @Override
        public ReviewResponse[] newArray(int size) {
            return new ReviewResponse[size];
        }
    };
}
