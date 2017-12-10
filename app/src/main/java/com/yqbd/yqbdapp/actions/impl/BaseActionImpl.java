package com.yqbd.yqbdapp.actions.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.yqbd.yqbdapp.annotation.ActionService;
import com.yqbd.yqbdapp.annotation.ExecutionMode;
import com.yqbd.yqbdapp.annotation.ExecutionThreadMode;
import com.yqbd.yqbdapp.application.ContextApplication;
import com.yqbd.yqbdapp.utils.BaseJson;
import com.yqbd.yqbdapp.utils.HttpUtils;
import com.yqbd.yqbdapp.actions.IBaseAction;

import java.io.IOException;
import java.util.Map;

@ActionService("baseAction")
public class BaseActionImpl implements IBaseAction {


    public static final String path = "http://10.0.2.2:8080";

    //public static final String path = "http://101.132.108.158:8080";
    @ExecutionMode(ExecutionThreadMode.SYNC)
    @Override
    public Integer getCurrentUserID() {
        SharedPreferences sharedPreferences = ContextApplication.getAppContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", 0);
        return userID;
    }

    protected String httpConnectByPost(String url, Map<String, String> map) throws IOException {
        return HttpUtils.httpConnectByPost(path + url, map);
    }

    protected String httpConnectByPost(String url, Object o) throws IOException {
        return HttpUtils.httpConnectByPost(path + url, o);
    }

    protected BaseJson networkErrorResult() {
        BaseJson baseJson = new BaseJson();
        baseJson.setErrorMessage("连接错误");
        baseJson.setReturnCode("E");
        return baseJson;
    }
}
