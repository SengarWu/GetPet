package com.example.administrator.getpet.base;

import android.app.Application;

/**
 * Created by Administrator on 2016/5/16.
 */
public class CustomApplication extends Application {

    public static CustomApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static CustomApplication getmInstance() {
        return mInstance;
    }
}
