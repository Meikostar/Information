package com.canplay.information.mvp.http;



import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface BaseApi {


    /**
     * 设置默认冲泡的奶粉
     */
    @POST("web/setUserMilk")
    Observable<String> setUserMilk(@QueryMap Map<String, String> options);

}
