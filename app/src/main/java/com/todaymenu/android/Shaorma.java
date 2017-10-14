package com.todaymenu.android;

import android.app.Application;

import com.todaymenu.android.data.Prefs;
import com.todaymenu.android.di.ApiModule;
import com.todaymenu.android.di.ApplicationComponent;
import com.todaymenu.android.di.ApplicationModule;
import com.todaymenu.android.di.DaggerApplicationComponent;
import com.todaymenu.android.di.InjectionHelper;
import com.todaymenu.android.utils.Factory;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Shaorma extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.init(this);
        Factory.TOKEN = Prefs.TokenPref.get();
        buildGraphAndInject();
        InjectionHelper.init(this);
        initTimber();
        initFonts();
    }

    private void initFonts() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/CrimsonText-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void buildGraphAndInject() {
        final ApplicationModule applicationModule = new ApplicationModule(this);

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .apiModule(new ApiModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
