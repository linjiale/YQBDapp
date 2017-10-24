package com.yqbd.yqbdapp.utils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * File name：BaseJson
 * Author: Administrator
 * Description：JSON基础结构，包含响应码和响应消息，反馈给前台页面
 * Modify History:
 */
public class BaseJson implements Serializable, Cloneable {

    private String returnCode;//响应代码
    private String errorMessage;//错误消息

    private Object obj;

    public <T> T getBean(Class<T> cls) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), cls);
    }

    public <T> List<T> getBeanList(Class<T[]> cls) {
        Gson gson = new Gson();
        return Lists.newArrayList(gson.fromJson(obj.toString(), cls));
    }

    public BaseJson() {
    }

    public BaseJson(JSONObject jsonObject) throws JSONException {
        this.returnCode = jsonObject.getString("returnCode");
        this.errorMessage = jsonObject.getString("errorMessage");
        try {
            this.obj = jsonObject.getJSONObject("obj");
        } catch (JSONException e) {
            this.obj = jsonObject.getJSONArray("obj");
        }
    }

    public BaseJson(String jsonObject) throws JSONException {
        this(new JSONObject(jsonObject));
    }

    public Boolean getSingleBooleanResult() throws JSONException {
        return ((JSONObject) obj).getBoolean("singleResult");
    }

    public String getSingleStringResult() throws JSONException {
        return ((JSONObject) obj).getString("singleResult");
    }

    public int getSingleIntegerResult() throws JSONException {
        return Integer.parseInt(getSingleStringResult());
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Deprecated
    public JSONObject getJSONObject() {
        return (JSONObject) obj;
    }

    @Deprecated
    public JSONArray getJSONArray() {
        return (JSONArray) obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }
}
