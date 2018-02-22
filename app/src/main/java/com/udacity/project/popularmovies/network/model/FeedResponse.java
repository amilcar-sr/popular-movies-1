package com.udacity.project.popularmovies.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * FeedResponse model
 */
public class FeedResponse implements Parcelable {

    @SerializedName("page")
    private long page;

    @SerializedName("total_results")
    private long totalResults;

    @SerializedName("total_pages")
    private long totalPages;

    @SerializedName("results")
    private ArrayList<Movie> results = null;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.page);
        dest.writeLong(this.totalResults);
        dest.writeLong(this.totalPages);
        dest.writeTypedList(this.results);
    }

    public FeedResponse() {
    }

    private FeedResponse(Parcel in) {
        this.page = in.readLong();
        this.totalResults = in.readLong();
        this.totalPages = in.readLong();
        this.results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Parcelable.Creator<FeedResponse> CREATOR = new Parcelable.Creator<FeedResponse>() {
        @Override
        public FeedResponse createFromParcel(Parcel source) {
            return new FeedResponse(source);
        }

        @Override
        public FeedResponse[] newArray(int size) {
            return new FeedResponse[size];
        }
    };
}
