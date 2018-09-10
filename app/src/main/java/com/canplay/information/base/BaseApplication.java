package com.canplay.information.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Vibrator;
import android.support.multidex.MultiDex;


import com.canplay.information.base.manager.AppManager;
import com.canplay.information.bean.Province;
import com.canplay.information.util.ExceptionHandler;


import java.util.HashMap;
import java.util.Map;





/**
 * App基类
 * Created by peter on 2016/9/11.
 */

public class BaseApplication extends Application {
    //全局单例
    AppComponent mAppComponent;
    public static  BaseApplication cplayApplication;

    public static Map<String,String> map=new HashMap<>();
    public static BaseApplication getInstance() {
        if (cplayApplication == null) {
            cplayApplication = new BaseApplication();
        }
        return  cplayApplication;
    }
    public static Province province;

    public  Vibrator mVibrator;
    @Override
    public void onCreate(){
        // TODO Auto-generated method stub
        super.onCreate();
        //生成全局单例
        cplayApplication = this;
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);
        ApplicationConfig.setAppInfo(this);

        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        unSppuortSystemFont();
        //全局异常处理
        new ExceptionHandler().init(this);


//        JPushInterface.setLatestNotificationNumber(this, 1);

    }



    public void unSppuortSystemFont() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }


    /**
     * 退出应用
     */
    public void exit(){
        AppManager.getInstance(this).exitAPP(this);
    }

    @Override
    public void attachBaseContext(Context base){
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
