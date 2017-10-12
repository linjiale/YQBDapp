package com.yqbd.yqbdapp.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 11022 on 2017/7/9.
 */
public class UserInfoBean {
    private Integer userId;
    private String accountNumber;
    private String sex;
    private String realName;
    private String nickName;
    private String headPortrait;
    private Integer professionalLevel;
    private Integer creditLevel;




    public UserInfoBean(Integer userId, String accountNumber, String sex, String realName, String nickName, Integer professionalLevel, Integer credit) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.sex = sex;
        this.realName = realName;
        this.nickName = nickName;
        this.professionalLevel = professionalLevel;
        this.creditLevel = credit;
    }

    public UserInfoBean(JSONObject jsonObject) throws JSONException {
        this.userId = jsonObject.getInt("userId");
        this.accountNumber = jsonObject.getString("accountNumber");
        this.sex = jsonObject.getString("sex");
        this.realName = jsonObject.getString("realName");
        this.nickName = jsonObject.getString("nickName");
        this.headPortrait = jsonObject.getString("headPortrait");
        this.professionalLevel = jsonObject.getInt("professionalLevel");
        this.creditLevel = jsonObject.getInt("creditLevel");
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Integer getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(Integer professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

    public Integer getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(Integer creditLevel) {
        this.creditLevel = creditLevel;
    }
}
