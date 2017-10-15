package com.todaymenu.android.mvp.presenter;

import android.location.Location;

import com.todaymenu.android.data.Prefs;
import com.todaymenu.android.data.models.Restaurant;
import com.todaymenu.android.di.InjectionHelper;
import com.todaymenu.android.di.RetrofitHolder;
import com.todaymenu.android.mvp.view.RestaurantsView;
import com.todaymenu.android.utils.MvpObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Paul
 * @since 2017.10.14
 */

public class RestaurantsPresenter extends Presenter<RestaurantsView> {

    @Inject
    RetrofitHolder mRetrofitHolder;

    public RestaurantsPresenter(RestaurantsView view) {
        super(view);
        InjectionHelper.getApplicationComponent().inject(this);
    }

    public void loadRestaurants() {
        getView().showProgress();
        mRetrofitHolder.getApiService().getRestaurants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<Restaurant>, List<Restaurant>>() {
                    @Override
                    public List<Restaurant> apply(List<Restaurant> restaurants) throws Exception {
                        double latitude = Prefs.LastLatitude.getDouble();
                        double longitude = Prefs.LastLongitude.getDouble();
                        for (Restaurant restaurant : restaurants) {
                            float[] result = new float[1];
                            Location.distanceBetween(latitude, longitude, restaurant.mLatitude, restaurant.mLongitude, result);
                            restaurant.mDistanceAway = (int) (result[0] / 1000);
                        }
                        return restaurants;
                    }
                })
                .subscribe(new MvpObserver<List<Restaurant>>(this) {
                    @Override
                    public void onNext(List<Restaurant> value) {
                        getView().showRestaurants(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().showError();
                    }
                });
    }

    public void loadRestaurant(int id) {
        getView().showProgress();
        mRetrofitHolder.getApiService().getRestaurant()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvpObserver<Restaurant>(this) {
                    @Override
                    public void onNext(Restaurant value) {
                        getView().showRestaurant(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().showError();
                    }
                });
    }
}
