package com.yqbd.yqbdapp.actions;

import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface ITaskAction extends IBaseAction {

    BaseJson publishTaskByUser(TaskBean object);

    BaseJson getPublishTasks(Integer userId);

    BaseJson isUserTaken(Integer userId,Integer taskId);

    BaseJson userTakeTask(Integer userId,Integer taskId);

    BaseJson userCancelTakeTask(Integer userId,Integer taskId);

    BaseJson getAllTasks();

    BaseJson getSearch(String str);
}
