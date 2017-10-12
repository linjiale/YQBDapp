package com.yqbd.yqbdapp.actions;

import com.yqbd.yqbdapp.annotation.ExecutionMode;
import com.yqbd.yqbdapp.annotation.ExecutionThreadMode;

public interface IBaseAction {
    @ExecutionMode(ExecutionThreadMode.SYNC)
    int getCurrentUserID();
}
