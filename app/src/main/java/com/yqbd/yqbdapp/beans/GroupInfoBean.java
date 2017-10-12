package com.yqbd.yqbdapp.beans;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joy12 on 2017/7/22.
 */
public class GroupInfoBean implements Parcelable {
    private Integer groupId;
    private Integer companyId;
    private String groupTitle;
    private String groupDescription;
    private Integer currentPeopleNumber;
    private Integer maxPeopleNumber;

    public GroupInfoBean(){}

    public GroupInfoBean(String groupTitle, String groupDescription, Integer maxPeopleNumber) {
        this.groupTitle = groupTitle;
        this.groupDescription = groupDescription;
        this.maxPeopleNumber = maxPeopleNumber;
    }

    public GroupInfoBean(JSONObject jsonObject) throws JSONException {
        this.groupId = jsonObject.getInt("groupId");
        this.companyId = jsonObject.getInt("companyId");
        this.groupTitle = jsonObject.getString("groupTitle");
        this.groupDescription = jsonObject.getString("groupDescription");
        this.currentPeopleNumber = jsonObject.getInt("currentPeopleNumber");
        this.maxPeopleNumber = jsonObject.getInt("maxPeopleNumber");
    }

    protected GroupInfoBean(Parcel in) {
        groupTitle = in.readString();
        groupDescription = in.readString();
    }

    public static final Creator<GroupInfoBean> CREATOR = new Creator<GroupInfoBean>() {
        @Override
        public GroupInfoBean createFromParcel(Parcel in) {
            GroupInfoBean groupInfoBean = new GroupInfoBean();
            groupInfoBean.setGroupId(in.readInt());
            groupInfoBean.setCompanyId(in.readInt());
            groupInfoBean.setGroupTitle(in.readString());
            groupInfoBean.setGroupDescription(in.readString());
            groupInfoBean.setCurrentPeopleNumber(in.readInt());
            groupInfoBean.setMaxPeopleNumber(in.readInt());
            return groupInfoBean;
        }

        @Override
        public GroupInfoBean[] newArray(int size) {
            return new GroupInfoBean[size];
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

    }
}
