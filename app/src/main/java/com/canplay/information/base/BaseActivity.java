package com.canplay.information.base;

import com.canplay.information.R;
import com.canplay.information.util.StatusBarUtil;

/**
 * @Title:基础Activity类
 * @Description:每个Activity必须继承该类
 * @Author: LLC
 * @Since:2015-3-19
 */
public abstract class BaseActivity extends BaseAllActivity {
    public void setStatus() {
        StatusBarUtil.setTranslucentStatus(this,false);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.slow_black),0);
    }
}
