package com.yqbd.yqbdapp.beans;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by joy12 on 2017/7/23.
 */
public class GroupMemberBean {

    private Integer groupId;

    private Integer userId;

    private Date participateTime;

    private Integer status;

    public GroupMemberBean(JSONObject jsonObject){}

    public GroupMemberBean(Integer groupId, Integer userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getParticipateTime() {
        return participateTime;
    }

    public void setParticipateTime(Date participateTime) {
        this.participateTime = participateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
