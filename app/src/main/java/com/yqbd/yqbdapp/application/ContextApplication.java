package com.yqbd.yqbdapp.application;

import android.app.Application;
import android.content.Context;
import com.google.common.collect.Maps;
import com.yqbd.yqbdapp.ActionInject;
import com.yqbd.yqbdapp.actions.IBaseAction;
import com.yqbd.yqbdapp.annotation.ActionService;
import dalvik.system.DexFile;

import java.lang.reflect.Field;
import java.util.*;

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