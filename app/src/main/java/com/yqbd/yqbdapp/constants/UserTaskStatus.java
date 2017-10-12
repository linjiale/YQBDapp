package com.yqbd.yqbdapp.constants;


public enum UserTaskStatus {

    NEW(10, "提交"),
    NOT_PAID(20, "待支付"),
    PREPARING(30, "准备中"),
    PEND_TO_TAKE(40, "待取餐"),
    DISTRIBUTING(50, "待配送"),
    NOT_COMMENT(60, "未评价"),
    APPLY_FOR_CANCEL(70, "申请取消"),
    CANCELLED(80, "已取消"),
    CLOSED(90, "被商家取消");


    UserTaskStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


    public static UserTaskStatus valueOf(int value) {
        for (UserTaskStatus taskStatus : UserTaskStatus.values()) {
            if (taskStatus.value == value) {
                return taskStatus;
            }
        }
        return NEW;
    }

    private final int value;
    private final String desc;

}
