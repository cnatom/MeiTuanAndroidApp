package com.ccatom.meituan;

import android.app.Application;
import android.content.Context;

//用于获取全局context
public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
