package com.todaymenu.android.fragments;

import com.todaymenu.android.data.models.Restaurant;

import java.util.List;

/**
 * @author Paul
 * @since 2017.10.14
 */

public abstract class BaseRestaurantsFragment extends BaseFragment {

    abstract public void setRestaurants(List<Restaurant> restaurants);
}
