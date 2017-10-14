package com.todaymenu.android.di;

import com.todaymenu.android.Shaorma;
import com.todaymenu.android.mvp.presenter.RestaurantsPresenter;
import com.todaymenu.android.mvp.presenter.SamplePresenter;

import dagger.Component;

@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(Shaorma shaorma);

    void inject(SamplePresenter samplePresenter);

    void inject(RestaurantsPresenter restaurantsPresenter);
}