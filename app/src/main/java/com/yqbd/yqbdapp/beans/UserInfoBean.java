package com.yqbd.yqbdapp.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoBean {
    private Integer userId;

    private String accountNumber;

    private String password;

    private String sex;

    private String realName;

    private String nickName;

    private String headPortrait;

    private Integer professionalLevel;

    private Integer creditLevel;

    private String telephone;

    private String school;

    private String occupation;

    private String companyName;

}