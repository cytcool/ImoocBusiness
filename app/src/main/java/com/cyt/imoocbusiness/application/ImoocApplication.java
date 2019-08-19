package com.cyt.imoocbusiness.application;

import android.app.Application;

public class ImoocApplication extends Application {

    private static ImoocApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static ImoocApplication getInstance(){
        return mApplication;
    }
}
