package com.canplay.information.mvp.component;


import com.canplay.information.base.AppComponent;


import com.canplay.information.mvp.ActivityScope;

import dagger.Component;

/**
 * Created by leo on 2016/9/27.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface BaseComponent{



}
