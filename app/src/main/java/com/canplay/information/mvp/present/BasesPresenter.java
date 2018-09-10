package com.canplay.information.mvp.present;


import android.support.annotation.NonNull;

import com.canplay.information.base.manager.ApiManager;
import com.canplay.information.mvp.http.BaseApi;
import com.canplay.information.net.MySubscriber;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import rx.Subscription;


public class BasesPresenter implements BaseContract.Presenter {
    private Subscription subscription;

    private BaseContract.View mView;

    private BaseApi contactApi;

    @Inject
    BasesPresenter(ApiManager apiManager){
        contactApi = apiManager.createApi(BaseApi.class);
    }
    @Override
    public void getMeasureRecord( final int type,String category,String from, String take) {

        Map<String, String> params = new TreeMap<>();
        params.put("milkInfoId","");
        subscription = ApiManager.setSubscribe(contactApi.setUserMilk(ApiManager.getParameters(params, true)), new MySubscriber<String>() {
            @Override
            public void onNext(String ruslt) {

                mView.toEntity(ruslt,2);

            }

            @Override
            public void onError(Throwable e) {

                super.onError(e);
                mView.showTomast(e.getMessage());
            }
        });
    }

    @Override
    public void attachView(@NonNull BaseContract.View view) {
        mView = view;
    }


    @Override
    public void detachView(){
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        mView = null;
    }
}
