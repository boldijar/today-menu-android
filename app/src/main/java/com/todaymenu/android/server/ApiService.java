package com.todaymenu.android.server;

import com.todaymenu.android.data.models.Restaurant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("restaurants.json")
    Observable<List<Restaurant>> getRestaurants();

    @GET("restaurant.json")
    Observable<Restaurant> getRestaurant();
}
