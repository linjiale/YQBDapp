package com.yqbd.yqbdapp.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joy12 on 2017/7/23.
 */
@Data
@NoArgsConstructor
public class CompanyInfoBean {
    private Integer companyId;
    private String companyName;
    private String companyAccount;
    private String password;
    private String classification;
    private String summary;
    private String headPortraitAddress;
}
