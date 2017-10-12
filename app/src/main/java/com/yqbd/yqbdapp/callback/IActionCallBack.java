package com.yqbd.yqbdapp.callback;

import com.yqbd.yqbdapp.utils.BaseJson;

public interface IActionCallBack {
    void OnSuccess(BaseJson baseJson);

    void onFailed(BaseJson baseJson);
}
