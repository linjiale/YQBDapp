package com.yqbd.yqbdapp.actions;

import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.utils.BaseJson;

public interface ITaskAction extends IBaseAction {

    BaseJson publishTaskByUser(TaskBean object);

    BaseJson getTakenTask(Integer userId);

    BaseJson isUserTaken(Integer userId,Integer taskId);

    BaseJson userTakeTask(Integer userId,Integer taskId);

    BaseJson userCancelTakeTask(Integer userId,Integer taskId);

    BaseJson getAllTasks();

    BaseJson getSearch(String str);

    BaseJson isCollect(Integer taskId);

    BaseJson collect(Integer taskId);

    BaseJson getCollectedTasks();

    BaseJson isTake(Integer taskId);

    BaseJson take(Integer taskId);

    BaseJson getCompanyTasks(Integer companyId);

    BaseJson getAcceptTasks();

    BaseJson getParticipant(Integer taskId);
}
