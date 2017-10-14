package com.todaymenu.android.data.models;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("name")
    public String mName;
    @SerializedName("image_big")
    public String mImageBig;
    @SerializedName("image_small")
    public String mImageSmall;
}
