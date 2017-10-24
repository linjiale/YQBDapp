package com.yqbd.yqbdapp.actions.impl;

import android.util.Log;
import com.google.common.collect.Maps;
import com.yqbd.yqbdapp.actions.ICompanyAction;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.annotation.ActionService;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.utils.BaseJson;
import com.yqbd.yqbdapp.utils.HttpUtils;

import java.util.Map;

@ActionService("companyAction")
public class CompanyActionImpl extends BaseActionImpl implements ICompanyAction {

    @Override
    public BaseJson getAllCompanies() {
        try {
            Map<String, String> map = Maps.newHashMap();
            String result = httpConnectByPost("/company/getAllCompanies", map);
            return new BaseJson(result);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }

    @Override
    public BaseJson getCompanyInfoByCompanyId(Integer companyId) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("companyId", companyId.toString());
            String result = httpConnectByPost("/company/getCompanyInfoByCompanyId", map);
            return new BaseJson(result);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.w(getClass().getSimpleName(), e);
            return networkErrorResult();
        }
    }
}
