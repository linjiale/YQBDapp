package com.yqbd.yqbdapp.beans;

import com.google.common.collect.Lists;
import com.yqbd.yqbdapp.tagview.widget.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11022 on 2017/7/17.
 */
@Data
@NoArgsConstructor
@ToString
public class TaskBean implements Serializable {
    private Integer taskId;
    private Integer userId;
    private Integer companyId;
    private String taskTitle;
    private String taskDescription;
    private String taskAddress;
    private Double pay;
    private Integer taskStatus;
    private Long publishTime;
    private Long completeTime;
    private Integer maxPeopleNumber;
    private Long deadline;
    private String simpleDrawingAddress;
    private Integer isGroup;
    private ArrayList<TypeBean> typeBeans;
    private String name;

    public void setTypeBeans(List<Tag> tags) {
        typeBeans = Lists.newArrayList();
        for (Tag tag : tags) {
            if (tag.getOr()) {
                typeBeans.add(new TypeBean(tag));
            }
        }
    }

    /*public TaskBean(String taskTitle, String taskDescription, String taskAddress, Double pay, Integer maxPeopleNumber, Date date, List<Tag> tags) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskAddress = taskAddress;
        this.maxPeopleNumber = maxPeopleNumber;
        this.pay = pay;
        this.deadline = date.getTime();
        *//*typeBeans = new ArrayList<>();
        for (Tag tag : tags) {
            if(tag.getOr()){
                typeBeans.add(new TypeBean(tag));
            }
        }*//*
    }*/

}
