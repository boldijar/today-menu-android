package com.todaymenu.android.mvp.view;

import com.todaymenu.android.data.models.Restaurant;

import java.util.List;

/**
 * @author Paul
 * @since 2017.10.14
 */

public interface RestaurantsView {
    void showRestaurants(List<Restaurant> restaurants);

    void showRestaurant(Restaurant restaurant);

    void showProgress();

    void showError();
}
