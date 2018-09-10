package com.canplay.information.mvp.present;

import com.canplay.information.base.BasePresenter;
import com.canplay.information.base.BaseView;

public class BaseContract {
    public    interface View extends BaseView {

//        <T> void toList(List<T> list, int type, int... refreshType);
        <T> void toEntity(T entity,int type);

        void toNextStep(int type);

        void showTomast(String msg);
    }

    public  interface Presenter extends BasePresenter<View> {

        /**
         * 测量记录
         */
        void getMeasureRecord(int type, String b, String c, String d);
    }
}
