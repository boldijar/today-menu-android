package com.todaymenu.android.di;

import com.todaymenu.android.Shaorma;

public final class InjectionHelper {

//    private static LoginComponent sLoginComponent;

    private static ApplicationComponent sApplicationComponent;

    public static void init(Shaorma shaorma) {
        sApplicationComponent = shaorma.getApplicationComponent();
    }

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }

//    public static LoginComponent getLoginComponent(Context context) {
//        if (sLoginComponent == null) {
//            sLoginComponent = DaggerLoginComponent.builder()
//                    .applicationComponent(getApplicationComponent(context))
//                    .build();
//        }
//        return sLoginComponent;
//    }
//
//    public static void destroyIntroComponents() {
//        sLoginComponent = null;
//    }
}