package com.cp.netllc.information;

import android.app.Application;
import android.util.Log;

/**
 * Created by netllc on 2018/8/9.
 */

public class AppApplication extends Application {

    private String url="https://www.sojson.com/open/api/weather/json.shtml?city=深圳";


    public String getUrl() {
        return url;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AppApplication","onCreate");
    }
}
