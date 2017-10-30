package com.todaymenu.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.todaymenu.android.R;
import com.todaymenu.android.data.models.Food;
import com.todaymenu.android.data.models.Menu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Paul
 * @since 2017.10.14
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    private List<Menu> mItems = new ArrayList<>();

    public void setItems(List<Menu> items) {
        mItems = items;
        for (Menu menu : items) {
            StringBuilder builder = new StringBuilder();
            List<Food> mFoods = menu.mFoods;
            for (int i = 0; i < mFoods.size(); i++) {
                Food food = mFoods.get(i);
                builder.append(food.mName);
                if (i < mFoods.size() - 1) {
                    builder.append("\n");
                }
            }
            menu.mAllFoods = builder.toString();
        }
        notifyDataSetChanged();
    }

    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FoodHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(final FoodHolder holder, int position) {
        final Menu menu = mItems.get(position);
        if (menu.mFoods != null && menu.mFoods.size() > 0) {
            if (menu.mThumbnailIndex >= menu.mFoods.size()) {
                menu.mThumbnailIndex = 0;
            }
            Glide.with(holder.mCover.getContext()).load(menu.mFoods.get(menu.mThumbnailIndex).mImageSmall).into(holder.mCover);
        } else {
            holder.mCover.setImageDrawable(null);
        }
        holder.mExtra.setText(menu.mExtra);
        holder.mFoodNameList.setText(menu.mAllFoods);
        holder.mPrice.setText(menu.mPrice);
        holder.mDivider.setVisibility(position < getItemCount() - 1 ? View.VISIBLE : View.INVISIBLE);
        holder.mFoodName.setText(menu.mName);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void timeUpdate(int firstIndex, int lastIndex) {
        if (mItems.isEmpty()) {
            return;
        }
        for (int i = firstIndex; i <= lastIndex; i++) {
            Menu menu = mItems.get(i);
            if (menu.mFoods == null || menu.mFoods.size() == 0) {
                continue;
            }
            int thumbIndex = menu.mThumbnailIndex;
            if (thumbIndex == menu.mFoods.size() - 1) {
                thumbIndex = 0;
            } else {
                thumbIndex++;
            }
            menu.mThumbnailIndex = thumbIndex;
        }
        notifyItemRangeChanged(firstIndex, lastIndex - firstIndex + 1);
    }

    public static class FoodHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.food_image)
        ImageView mCover;
        @BindView(R.id.food_name_list)
        TextView mFoodNameList;
        @BindView(R.id.food_price)
        TextView mPrice;
        @BindView(R.id.food_extra)
        TextView mExtra;
        @BindView(R.id.food_divider)
        View mDivider;
        @BindView(R.id.food_name)
        TextView mFoodName;

        public FoodHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
