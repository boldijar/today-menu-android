package com.todaymenu.android.fragments;

import android.content.Context;

import com.todaymenu.android.data.models.Restaurant;
import com.todaymenu.android.mvp.view.HomeView;

import java.util.List;

/**
 * @author Paul
 * @since 2017.10.14
 */

public abstract class BaseRestaurantsFragment extends BaseFragment {

    protected HomeView mHomeView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof HomeView)) {
            throw new RuntimeException("Activity must implement HomeView");
        }
        mHomeView = (HomeView) context;
    }

    abstract public void setRestaurants(List<Restaurant> restaurants);
}
