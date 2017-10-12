package com.yqbd.yqbdapp.actions.impl;

import android.util.Log;
import com.google.common.collect.Maps;
import com.yqbd.yqbdapp.actions.ITypeAction;
import com.yqbd.yqbdapp.annotation.ActionService;
import com.yqbd.yqbdapp.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ActionService("typeAction")
public class TypeActionImpl extends BaseActionImpl implements ITypeAction {

    @Override
    public BaseJson getAllTypes() {
        try {
            Map<String, String> map = new HashMap<>();
            String result = httpConnectByPost("/task/getAllTypes", map);
            return new BaseJson(result);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.d("error", "321", e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson getAllSearchTypes() {
        try {
            Map<String, String> map = Maps.newHashMap();
            String result = httpConnectByPost("/task/getSearchTypes", map);
            return new BaseJson(result);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.d("error", "321", e);
            return networkErrorResult();
        }
    }
}
