package com.todaymenu.android.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.todaymenu.android.R;
import com.todaymenu.android.adapters.FoodAdapter;
import com.todaymenu.android.data.models.Restaurant;
import com.todaymenu.android.mvp.presenter.RestaurantsPresenter;
import com.todaymenu.android.mvp.view.RestaurantsView;
import com.todaymenu.android.view.EmptyLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Paul
 * @since 2017.10.15
 */

public class RestaurantActivity extends BaseActivity implements RestaurantsView {

    private static final String ARG_RESTAURANT = "restaurant";

    @BindView(R.id.restaurant_cover)
    ImageView mCover;
    @BindView(R.id.restaurant_empty)
    EmptyLayout mEmptyLayout;
    @BindView(R.id.restaurant_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.restaurant_address)
    TextView mAddress;
    @BindView(R.id.restaurant_collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.restaurant_recycler)
    RecyclerView mFoodRecycler;

    private Restaurant mRestaurant;
    private RestaurantsPresenter mRestaurantsPresenter;
    private FoodAdapter mAdapter;

    public static Intent createIntent(Context context, Restaurant restaurant) {
        Intent intent = new Intent(context, RestaurantActivity.class);
        intent.putExtra(ARG_RESTAURANT, restaurant);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        if (!getIntent().getExtras().containsKey(ARG_RESTAURANT)) {
            throw new RuntimeException("Restaurant missing.");
        }
        mRestaurant = getIntent().getExtras().getParcelable(ARG_RESTAURANT);
        ButterKnife.bind(this);
        initToolbar();
        Glide.with(this).load(mRestaurant.mCoverUrl).into(mCover);
        initList();
        initPresenter();
        mFoodRecycler.setAlpha(0);
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                mFoodRecycler.animate().setDuration(500).alpha(1).start();
            }
        });
    }

    private void initList() {
        mFoodRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FoodAdapter();
        mFoodRecycler.setAdapter(mAdapter);
    }

    private void initPresenter() {
        mRestaurantsPresenter = new RestaurantsPresenter(this);
        mEmptyLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRestaurantsPresenter.loadRestaurant(mRestaurant.mId);
            }
        });
        mRestaurantsPresenter.loadRestaurant(mRestaurant.mId);
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar() {
        Typeface expandedFont = Typeface.createFromAsset(getAssets(), "fonts/CrimsonText-Bold.ttf");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mRestaurant.mName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedTitle);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedTitle);
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(expandedFont);
        mCollapsingToolbarLayout.setExpandedTitleTypeface(expandedFont);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_call && mRestaurant.mPhone != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mRestaurant.mPhone));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.restaurant_directions_fab)
    void directions() {
        String uri = "http://maps.google.com/maps?f=d&hl=en&daddr=" + mRestaurant.mLatitude + "," + mRestaurant.mLongitude;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(Intent.createChooser(intent, getString(R.string.choose_app)));
    }

    @Override
    public void showRestaurants(List<Restaurant> restaurants) {
        // not used
    }

    @Override
    public void showRestaurant(Restaurant restaurant) {
        mEmptyLayout.setState(EmptyLayout.State.CLEAR);
        mRestaurant = restaurant;
        mAddress.setText(mRestaurant.mAddress);
        mAdapter.setItems(restaurant.mMenus);
    }

    @Override
    public void showProgress() {
        mEmptyLayout.setState(EmptyLayout.State.LOADING);
    }

    @Override
    public void showError() {
        mEmptyLayout.setState(EmptyLayout.State.ERROR, R.string.unexpected_error);
    }
}
