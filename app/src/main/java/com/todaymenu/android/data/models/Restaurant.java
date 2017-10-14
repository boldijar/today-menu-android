package com.todaymenu.android.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Paul
 * @since 2017.10.14
 */

public class Restaurant {
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
}
