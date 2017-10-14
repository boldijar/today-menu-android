package com.todaymenu.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.todaymenu.android.R;
import com.todaymenu.android.data.models.Restaurant;
import com.todaymenu.android.fragments.BaseRestaurantsFragment;
import com.todaymenu.android.fragments.RestaurantsListFragment;
import com.todaymenu.android.mvp.presenter.RestaurantsPresenter;
import com.todaymenu.android.mvp.view.RestaurantsView;
import com.todaymenu.android.view.EmptyLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Paul
 * @since 2017.08.29
 */

public class HomeActivity extends BaseActivity implements RestaurantsView {


    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.home_empty_layout)
    EmptyLayout mEmptyLayout;
    @BindView(R.id.home_container)
    View mContainer;

    private RestaurantsPresenter mRestaurantsPresenter = new RestaurantsPresenter(this);
    private BaseRestaurantsFragment mBaseRestaurantsFragment;

    private RestaurantsListFragment mRestaurantsListFragment = new RestaurantsListFragment();

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        mBaseRestaurantsFragment = (BaseRestaurantsFragment) setFragment(R.id.home_container, mRestaurantsListFragment);

        mRestaurantsPresenter.loadRestaurants();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public void showRestaurants(List<Restaurant> restaurants) {
        mContainer.setVisibility(View.VISIBLE);
        mEmptyLayout.setState(EmptyLayout.State.CLEAR);
        mBaseRestaurantsFragment.setRestaurants(restaurants);
    }

    @Override
    public void showRestaurant(Restaurant restaurant) {
        // nothing here
    }

    @Override
    public void showProgress() {
        mContainer.setVisibility(View.GONE);
        mEmptyLayout.setState(EmptyLayout.State.LOADING);
    }

    @Override
    public void showError() {
        mContainer.setVisibility(View.GONE);
        mEmptyLayout.setState(EmptyLayout.State.ERROR, R.string.unexpected_error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRestaurantsPresenter.destroySubscriptions();
    }
}
