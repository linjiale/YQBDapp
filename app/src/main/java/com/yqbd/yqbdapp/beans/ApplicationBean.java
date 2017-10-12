package com.yqbd.yqbdapp.beans;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joy12 on 2017/7/26.
 * 新封装的Bean，就是把UserInfo GroupInfo GroupMember放到一起
 */
public class ApplicationBean  implements Parcelable {
    //group
    private Integer groupId;
    private Integer companyId;
    private String groupTitle;
    private String groupDescription;
    private Integer currentPeopleNumber;
    private Integer maxPeopleNumber;
    //candidate
    private Integer userId;
    private String accountNumber;
    private String sex;
    private String realName;
    private String nickName;
    private String headPortrait;
    private Integer professionalLevel;
    private Integer creditLevel;
    //status
    private Integer status;

    public ApplicationBean(){}

    public ApplicationBean(JSONObject jsonObject) throws JSONException {
        this.groupId = jsonObject.getInt("groupId");
        this.companyId = jsonObject.getInt("companyId");
        this.groupTitle = jsonObject.getString("groupTitle");
        this.groupDescription = jsonObject.getString("groupDescription");
        this.currentPeopleNumber = jsonObject.getInt("currentPeopleNumber");
        this.maxPeopleNumber = jsonObject.getInt("maxPeopleNumber");

        this.userId = jsonObject.getInt("userId");
        this.accountNumber = jsonObject.getString("accountNumber");
        this.sex = jsonObject.getString("sex");
        this.realName = jsonObject.getString("realName");
        this.nickName = jsonObject.getString("nickName");
        this.headPortrait = jsonObject.getString("headPortrait");
        this.professionalLevel = jsonObject.getInt("professionalLevel");
        this.creditLevel = jsonObject.getInt("creditLevel");

        this.status = jsonObject.getInt("status");
    }

    public ApplicationBean(GroupInfoBean group, UserInfoBean candidate, GroupMemberBean status) {
        this.groupId = group.getGroupId();
        this.companyId = group.getCompanyId();
        this.groupTitle = group.getGroupTitle();
        this.groupDescription = group.getGroupDescription();
        this.currentPeopleNumber = group.getCurrentPeopleNumber();
        this.maxPeopleNumber = group.getMaxPeopleNumber();
        this.userId = candidate.getUserId();
        this.accountNumber = candidate.getAccountNumber();
        this.sex = candidate.getSex();
        this.realName = candidate.getRealName();
        this.nickName = candidate.getNickName();
        this.headPortrait = candidate.getHeadPortrait();
        this.professionalLevel = candidate.getProfessionalLevel();
        this.creditLevel = candidate.getCreditLevel();
        this.status = status.getStatus();
    }

    public static final Creator<ApplicationBean> CREATOR = new Creator<ApplicationBean>() {
        @Override
        public ApplicationBean createFromParcel(Parcel in) {
            ApplicationBean applicationBean = new ApplicationBean();
            applicationBean.setGroupId(in.readInt());
            applicationBean.setCompanyId(in.readInt());
            applicationBean.setGroupTitle(in.readString());
            applicationBean.setGroupDescription(in.readString());
            applicationBean.setCurrentPeopleNumber(in.readInt());
            applicationBean.setMaxPeopleNumber(in.readInt());

            applicationBean.setUserId(in.readInt());
            applicationBean.setAccountNumber(in.readString());
            applicationBean.setSex(in.readString());
            applicationBean.setRealName(in.readString());
            applicationBean.setNickName(in.readString());
            applicationBean.setHeadPortrait(in.readString());
            applicationBean.setProfessionalLevel(in.readInt());
            applicationBean.setCreditLevel(in.readInt());

            applicationBean.setStatus(in.readInt());

            return applicationBean;
        }

        @Override
        public ApplicationBean[] newArray(int size) {
            return new ApplicationBean[size];
        }
    };

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Integer getCurrentPeopleNumber() {
        return currentPeopleNumber;
    }

    public void setCurrentPeopleNumber(Integer currentPeopleNumber) {
        this.currentPeopleNumber = currentPeopleNumber;
    }

    public Integer getMaxPeopleNumber() {
        return maxPeopleNumber;
    }

    public void setMaxPeopleNumber(Integer maxPeopleNumber) {
        this.maxPeopleNumber = maxPeopleNumber;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groupId);
        dest.writeInt(companyId);
        dest.writeString(groupTitle);
        dest.writeString(groupDescription);
        dest.writeInt(currentPeopleNumber);
        dest.writeInt(maxPeopleNumber);

        dest.writeInt(userId);
        dest.writeString(accountNumber);
        dest.writeString(sex);
        dest.writeString(realName);
        dest.writeString(nickName);
        dest.writeString(headPortrait);
        dest.writeInt(professionalLevel);
        dest.writeInt(creditLevel);

        dest.writeInt(status);

    }
}

