package com.todaymenu.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.todaymenu.android.R;
import com.todaymenu.android.data.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TODO: Class description
 *
 * @author Paul
 * @since 2017.10.14
 */

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantsHolder> {

    private List<Restaurant> mRestaurants = new ArrayList<>();

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
        notifyDataSetChanged();
    }

    @Override
    public RestaurantsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false));
    }

    @Override
    public void onBindViewHolder(RestaurantsHolder holder, int position) {
        Restaurant restaurant = mRestaurants.get(position);
        Glide.with(holder.mCover.getContext()).load(restaurant.mCoverUrl).into(holder.mCover);
        holder.mDescription.setText(restaurant.mDetails);
        holder.mTitle.setText(restaurant.mName);
        holder.mDistance.setText((int) (Math.random() * 100) + " km away");
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public static class RestaurantsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restaurant_cover)
        ImageView mCover;
        @BindView(R.id.restaurant_description)
        TextView mDescription;
        @BindView(R.id.restaurant_distance)
        TextView mDistance;
        @BindView(R.id.restaurant_title)
        TextView mTitle;

        public RestaurantsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
