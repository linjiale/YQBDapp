package com.yqbd.yqbdapp.actions.impl;

import android.util.Log;
import com.yqbd.yqbdapp.actions.IUserAction;
import com.yqbd.yqbdapp.annotation.ActionService;
import com.yqbd.yqbdapp.utils.BaseJson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11022 on 2017/7/9.
 */
@ActionService("userAction")
public class UserActionImpl extends BaseActionImpl implements IUserAction {

    @Override
    public BaseJson login(String accountNumber, String userPassword) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("accountNumber", accountNumber);
            map.put("userPassword", userPassword);
            String result = httpConnectByPost("/user/login", map);
            return new BaseJson(result);
        } catch (Exception e) {
            Log.w(getClass().getSimpleName(),  e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson register(String accountNumber, String userPassword, String realName) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("accountNumber", accountNumber);
            map.put("userPassword", userPassword);
            map.put("realName", realName);
            String result = httpConnectByPost("/user/register", map);
            return new BaseJson(result);
        } catch (Exception e) {
            Log.w(getClass().getSimpleName(),  e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson getUserInfoByUserID(int userID) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userID", String.valueOf(userID));
            String result = httpConnectByPost("/user/getUserInfoByUserID", map);
            BaseJson baseJson = new BaseJson(result);
            return baseJson;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(),  e);
            return networkErrorResult();
        }
    }


    public int validateRealName(String accountNumber, String realName) throws IOException {
        return 0;
    }


    public int validateTeleNum(String teleNum, String validateNumber) throws IOException {
        return 0;
    }

}
