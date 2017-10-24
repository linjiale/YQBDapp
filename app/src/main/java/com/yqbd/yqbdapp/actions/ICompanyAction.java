package com.yqbd.yqbdapp.actions;

import com.yqbd.yqbdapp.utils.BaseJson;

public interface ICompanyAction extends IBaseAction {
    BaseJson getAllCompanies();

    BaseJson getCompanyInfoByCompanyId(Integer companyId);
}
