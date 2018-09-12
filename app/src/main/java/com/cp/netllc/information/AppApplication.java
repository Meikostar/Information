package com.cp.netllc.information;

import android.app.Application;
import android.util.Log;

/**
 * Created by netllc on 2018/8/9.
 */

public class AppApplication extends Application {

    public static String url = "http://vr.canplay.com.cn/address/web";
    public static Content content;

    public String getUrl() {
        return url;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        content=new Content();
        Log.d("AppApplication", "onCreate");
    }
}
