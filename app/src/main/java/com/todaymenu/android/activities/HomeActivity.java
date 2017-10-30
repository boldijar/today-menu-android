package com.todaymenu.android.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.todaymenu.android.R;
import com.todaymenu.android.data.Prefs;
import com.todaymenu.android.data.models.Restaurant;
import com.todaymenu.android.fragments.BaseRestaurantsFragment;
import com.todaymenu.android.fragments.RestaurantsListFragment;
import com.todaymenu.android.fragments.RestaurantsMapFragment;
import com.todaymenu.android.mvp.presenter.RestaurantsPresenter;
import com.todaymenu.android.mvp.view.HomeView;
import com.todaymenu.android.mvp.view.RestaurantsView;
import com.todaymenu.android.view.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

/**
 * @author Paul
 * @since 2017.08.29
 */

public class HomeActivity extends BaseActivity implements RestaurantsView, HomeView {

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.home_empty_layout)
    EmptyLayout mEmptyLayout;
    @BindView(R.id.home_container)
    View mContainer;
    @BindView(R.id.home_fab)
    FloatingActionButton mFloatingActionButton;

    private RestaurantsPresenter mRestaurantsPresenter = new RestaurantsPresenter(this);
    private BaseRestaurantsFragment mBaseRestaurantsFragment;
    private RestaurantsListFragment mRestaurantsListFragment = new RestaurantsListFragment();
    private RestaurantsMapFragment mRestaurantsMapFragment = new RestaurantsMapFragment();
    private MenuItem mMenu;
    private List<Restaurant> mRestaurants = new ArrayList<>();

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
        mEmptyLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRestaurantsPresenter.loadRestaurants();
            }
        });
//        getLocationUpdate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        mMenu = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.action_map) {
            return true;
        }
        boolean showingList = mBaseRestaurantsFragment instanceof RestaurantsListFragment;
        if (showingList) {
            mFloatingActionButton.show();
            mMenu.setIcon(R.drawable.toolbar_list);
            mMenu.setTitle(R.string.list);
            mBaseRestaurantsFragment = mRestaurantsMapFragment;
        } else {
            mFloatingActionButton.hide();
            mMenu.setIcon(R.drawable.toolbar_map);
            mMenu.setTitle(R.string.map);
            mBaseRestaurantsFragment = mRestaurantsListFragment;
        }
        setFragment(R.id.home_container, mBaseRestaurantsFragment);
        mBaseRestaurantsFragment.setRestaurants(mRestaurants);
        return true;
    }

    @Override
    public void showRestaurants(List<Restaurant> restaurants) {
        mContainer.setVisibility(View.VISIBLE);
        mEmptyLayout.setState(EmptyLayout.State.CLEAR);
        mBaseRestaurantsFragment.setRestaurants(restaurants);
        mRestaurants = restaurants;
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
    public void intervalUpdate() {
        // not used here
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRestaurantsPresenter.destroySubscriptions();
    }

    @OnClick(R.id.home_fab)
    void onFabClicked() {
        if (mBaseRestaurantsFragment instanceof RestaurantsMapFragment) {
            RestaurantsMapFragment restaurantsMapFragment = (RestaurantsMapFragment) mBaseRestaurantsFragment;
            restaurantsMapFragment.zoomToCurrentPosition();
        }
    }

    @Override
    public void clickedRestaurant(Restaurant restaurant) {
        startActivity(RestaurantActivity.createIntent(this, restaurant, false));
    }

    @Override
    public void clickedRestaurant(Restaurant restaurant, View imageView) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imageView, "cover");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(RestaurantActivity.createIntent(this, restaurant, true), options.toBundle());
        } else {
            clickedRestaurant(restaurant);
        }
    }

    public void getLocationUpdate() {
        SmartLocation.with(this).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Prefs.LastLatitude.put(location.getLatitude());
                        Prefs.LastLongitude.put(location.getLongitude());
                        locationUpdated();
                    }
                });
    }

    private void locationUpdated() {
        Toast.makeText(this, "LOcation was upDaTeD", Toast.LENGTH_SHORT).show();
    }
}
