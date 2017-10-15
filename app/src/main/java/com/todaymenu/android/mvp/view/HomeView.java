package com.todaymenu.android.mvp.view;

import android.view.View;

import com.todaymenu.android.data.models.Restaurant;

/**
 * @author Paul
 * @since 2017.10.15
 */

public interface HomeView {

    void clickedRestaurant(Restaurant restaurant);

    void clickedRestaurant(Restaurant restaurant, View imageView);
}
