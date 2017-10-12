package com.yqbd.yqbdapp.beans;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joy12 on 2017/7/23.
 */
public class CompanyInfoBean {
    private Integer companyId;
    private String companyName;
    private String companyAccount;
    private String password;
    private String classification;
    private String summary;

    public CompanyInfoBean(String companyName, String companyAccount, String password, String classification, String summary) {
        this.companyName = companyName;
        this.companyAccount = companyAccount;
        this.password = password;
        this.classification = classification;
        this.summary = summary;
    }

    public CompanyInfoBean(JSONObject jsonObject) throws JSONException {
        this.companyId = jsonObject.getInt("companyId");
        this.companyName = jsonObject.getString("companyName");
        this.companyAccount = jsonObject.getString("companyAccount");
        this.password = jsonObject.getString("password");
        this.classification = jsonObject.getString("classification");
        this.summary = jsonObject.getString("summary");
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
