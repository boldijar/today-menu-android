package com.todaymenu.android.mvp.presenter;

/**
 * @author Paul
 * @since 2017.08.30
 */

public class SamplePresenter /*extends Presenter<StatsView>*/ {

//    @Inject
//    RetrofitHolder mRetrofitHolder;
//
//    public SamplePresenter(StatsView view) {
//        super(view);
//        InjectionHelper.getApplicationComponent().inject(this);
//    }
//
//    public void load(String eventId) {
//        getView().showLoading();
//        mRetrofitHolder.getApiService().getEventStats(eventId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MvpObserver<ArrayList<EventStats>>(this) {
//                    @Override
//                    public void onNext(ArrayList<EventStats> value) {
//                        getView().showStats(value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        getView().showError();
//                    }
//                });
//
//    }

}
