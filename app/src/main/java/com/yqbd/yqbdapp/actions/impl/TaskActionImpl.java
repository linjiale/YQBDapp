package com.yqbd.yqbdapp.actions.impl;

import android.util.Log;
import com.google.common.collect.Maps;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.annotation.ActionService;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.utils.BaseJson;
import com.yqbd.yqbdapp.utils.HttpUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ActionService("taskAction")
public class TaskActionImpl extends BaseActionImpl implements ITaskAction {

    @Override
    public BaseJson publishTaskByUser(TaskBean object) {
        object.setUserId(getCurrentUserID());
        try {
            String result = httpConnectByPost("/task/publishTask", object);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson getPublishTasks(Integer userId) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", userId.toString());
            String result = httpConnectByPost("/task/publishTasks", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson isUserTaken(Integer userId, Integer taskId) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", userId.toString());
            map.put("taskId", taskId.toString());
            String result = httpConnectByPost("/task/isTaken", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson userTakeTask(Integer userId, Integer taskId) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", userId.toString());
            map.put("taskId", taskId.toString());
            String result = httpConnectByPost("/task/userTakeTask", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson userCancelTakeTask(Integer userId, Integer taskId) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", userId.toString());
            map.put("taskId", taskId.toString());
            String result = httpConnectByPost("/task/userCancelTakeTask", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson getAllTasks() {
        try {
            Map<String, String> map = Maps.newHashMap();
            String result = httpConnectByPost("/task/getAllTasks", map);
            return new BaseJson(result);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson getSearch(String str) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("map", str);
            System.out.println(str);
            String result = HttpUtils.httpConnectByPost("/task/getSearch", map);
            return new BaseJson(result);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson isCollect(Integer taskId) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", getCurrentUserID().toString());
            map.put("taskId", taskId.toString());
            String result = httpConnectByPost("/task/isCollected", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson collect(Integer taskId) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", getCurrentUserID().toString());
            map.put("taskId", taskId.toString());
            String result = httpConnectByPost("/task/collect", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson getCollectedTasks() {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", getCurrentUserID().toString());
            String result = httpConnectByPost("/task/getCollectedTasks", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson getCompanyTasks(Integer companyId) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("companyId",companyId.toString());
            String result = httpConnectByPost("/task/getCompanyTasks", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }
}
