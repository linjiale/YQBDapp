package com.yqbd.yqbdapp.actions;

import com.yqbd.yqbdapp.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

public interface ITypeAction extends IBaseAction {
    BaseJson getAllTypes();

    BaseJson getAllSearchTypes();
}
