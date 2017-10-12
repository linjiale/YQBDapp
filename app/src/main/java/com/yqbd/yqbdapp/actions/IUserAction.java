package com.yqbd.yqbdapp.actions;

import com.yqbd.yqbdapp.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

public interface IUserAction extends IBaseAction {
    BaseJson login(String accountNumber, String userPassword);

    BaseJson register(String accountNumber, String userPassword, String realName, String telephone);

    BaseJson getUserInfoByUserID(int userID);

}
