package com.todaymenu.android.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul
 * @since 2017.10.14
 */

public class Restaurant implements Parcelable {
    @SerializedName("details")
    public String mDetails;
    @SerializedName("cover_url")
    public String mCoverUrl;
    @SerializedName("name")
    public String mName;
    @SerializedName("latitude")
    public double mLatitude;
    @SerializedName("longitude")
    public double mLongitude;
    @SerializedName("id")
    public int mId;
    @SerializedName("address")
    public String mAddress;
    @SerializedName("phone")
    public String mPhone;
    @SerializedName("website")
    public String mWebsite;
    @SerializedName("menus")
    public List<Menu> mMenus;

    // non API fields
    public int mDistanceAway;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDetails);
        dest.writeString(this.mCoverUrl);
        dest.writeString(this.mName);
        dest.writeDouble(this.mLatitude);
        dest.writeDouble(this.mLongitude);
        dest.writeInt(this.mId);
        dest.writeString(this.mAddress);
        dest.writeString(this.mPhone);
        dest.writeString(this.mWebsite);
        dest.writeList(this.mMenus);
        dest.writeInt(this.mDistanceAway);
    }

    public Restaurant() {
    }

    protected Restaurant(Parcel in) {
        this.mDetails = in.readString();
        this.mCoverUrl = in.readString();
        this.mName = in.readString();
        this.mLatitude = in.readDouble();
        this.mLongitude = in.readDouble();
        this.mId = in.readInt();
        this.mAddress = in.readString();
        this.mPhone = in.readString();
        this.mWebsite = in.readString();
        this.mMenus = new ArrayList<Menu>();
        in.readList(this.mMenus, Menu.class.getClassLoader());
        this.mDistanceAway = in.readInt();
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
