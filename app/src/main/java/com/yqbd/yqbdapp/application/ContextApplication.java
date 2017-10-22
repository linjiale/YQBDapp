package com.yqbd.yqbdapp.application;

import android.app.Application;
import android.content.Context;
import com.yqbd.yqbdapp.utils.ActionInject;

public class ContextApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        System.out.println("Context Created");
        ActionInject.init(this);
    }

    public static Context getAppContext() {
        return ContextApplication.context;
    }
}