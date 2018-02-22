package com.udacity.project.popularmovies.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * TrailerResponse model
 */
public class TrailerResponse implements Parcelable {

    @SerializedName("id")
    private long id;

    @SerializedName("results")
    private ArrayList<Trailer> results = null;

    public long getId() {
        return id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeTypedList(this.results);
    }

    public TrailerResponse() {
    }

    private TrailerResponse(Parcel in) {
        this.id = in.readLong();
        this.results = in.createTypedArrayList(Trailer.CREATOR);
    }

    public static final Parcelable.Creator<TrailerResponse> CREATOR = new Parcelable.Creator<TrailerResponse>() {
        @Override
        public TrailerResponse createFromParcel(Parcel source) {
            return new TrailerResponse(source);
        }

        @Override
        public TrailerResponse[] newArray(int size) {
            return new TrailerResponse[size];
        }
    };
}
