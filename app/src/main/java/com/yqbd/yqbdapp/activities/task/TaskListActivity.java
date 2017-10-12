package com.yqbd.yqbdapp.activities.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.adapter.MyTaskAdapter;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.BaseJson;

import java.util.List;

public class TaskListActivity extends BaseActivity implements IActionCallBack {

    @BindView(R.id.myTaskList)
    RecyclerView myTaskList;

    private MyTaskAdapter adapter;

    private LinearLayoutManager linearLayoutManager;

    @Action
    private ITaskAction taskAction;

    private List<TaskBean> taskBeanList = Lists.newArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        Log.d(getLogTag(), "title:" + title);
        initializeTop(true, intent.getStringExtra("title"));
        linearLayoutManager = new LinearLayoutManager(this);
        myTaskList.setLayoutManager(linearLayoutManager);
        adapter = new MyTaskAdapter(taskBeanList, this);
        myTaskList.setAdapter(adapter);
        taskAction.getPublishTasks(taskAction.getCurrentUserID());
    }

    @Override
    public void OnSuccess(BaseJson baseJson) {
        //Gson gson = new Gson();
        //TaskBean[] taskBeans = gson.fromJson(baseJson.getObj().toString(), TaskBean[].class);
        //List<TaskBean> newTasksList = Lists.newArrayList(taskBeans);
        //baseJson.getBeanList(TaskBean[].class);
        taskBeanList.addAll(baseJson.getBeanList(TaskBean[].class));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        makeToast("Failed");
    }
}
