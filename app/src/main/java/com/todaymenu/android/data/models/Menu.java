package com.todaymenu.android.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Menu {
    @SerializedName("price")
    public String mPrice;
    @SerializedName("name")
    public String mName;
    @SerializedName("foods")
    public List<Food> mFoods;
    @SerializedName("extra")
    public String mExtra;


    // non api
    public String mAllFoods;
}
