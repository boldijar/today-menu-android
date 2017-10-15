package com.todaymenu.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.todaymenu.android.R;
import com.todaymenu.android.adapters.RestaurantsAdapter;
import com.todaymenu.android.data.models.Restaurant;

import java.util.List;

import butterknife.BindView;

/**
 * @author Paul
 * @since 2017.10.14
 */

public class RestaurantsListFragment extends BaseRestaurantsFragment {

    @BindView(R.id.restaurants_list_recycler)
    RecyclerView mRecyclerView;

    private RestaurantsAdapter mRestaurantsAdapter;

    @Override
    int getLayoutId() {
        return R.layout.fragment_restaurants_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRestaurantsAdapter = new RestaurantsAdapter(mRestaurantsListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRestaurantsAdapter);
    }

    @Override
    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurantsAdapter.setRestaurants(restaurants);
    }

    RestaurantsAdapter.Listener mRestaurantsListener = new RestaurantsAdapter.Listener() {
        @Override
        public void onClickedRestaurant(Restaurant restaurant, View cover) {
            mHomeView.clickedRestaurant(restaurant, cover);
        }
    };
}
