package com.todaymenu.android.mvp.presenter;

import com.todaymenu.android.data.models.Restaurant;
import com.todaymenu.android.di.InjectionHelper;
import com.todaymenu.android.di.RetrofitHolder;
import com.todaymenu.android.mvp.view.RestaurantsView;
import com.todaymenu.android.utils.MvpObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
}
