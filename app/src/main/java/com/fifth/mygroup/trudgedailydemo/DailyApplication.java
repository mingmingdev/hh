package com.fifth.mygroup.trudgedailydemo;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lenovo on 2016/5/27.
 */
public class DailyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
