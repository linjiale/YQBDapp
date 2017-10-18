package com.yqbd.yqbdapp.actions;

import com.yqbd.yqbdapp.utils.BaseJson;

public interface IUserAction extends IBaseAction {
    BaseJson login(String accountNumber, String userPassword);

    BaseJson register(String accountNumber, String userPassword, String realName);

    BaseJson getUserInfoByUserID(int userID);

}
