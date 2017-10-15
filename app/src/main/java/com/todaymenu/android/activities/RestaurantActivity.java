package com.todaymenu.android.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.todaymenu.android.R;
import com.todaymenu.android.data.models.Restaurant;
import com.todaymenu.android.view.EmptyLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Paul
 * @since 2017.10.15
 */

public class RestaurantActivity extends BaseActivity {

    private static final String ARG_RESTAURANT = "restaurant";

    @BindView(R.id.restaurant_cover)
    ImageView mCover;
    @BindView(R.id.restaurant_empty)
    EmptyLayout mEmptyLayout;
    @BindView(R.id.restaurant_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.restaurant_collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Restaurant mRestaurant;

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

        mEmptyLayout.setState(EmptyLayout.State.LOADING);

        Glide.with(this).load(mRestaurant.mCoverUrl).into(mCover);
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar() {
        Typeface expandedFont = Typeface.createFromAsset(getAssets(), "fonts/CrimsonText-Bold.ttf");
        Typeface collapsedFont = Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mRestaurant.mName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedTitle);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedTitle);
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(collapsedFont);
        mCollapsingToolbarLayout.setExpandedTitleTypeface(expandedFont);

    }
}
