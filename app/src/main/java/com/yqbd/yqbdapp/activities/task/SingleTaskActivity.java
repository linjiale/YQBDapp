package com.yqbd.yqbdapp.activities.task;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.annotation.VoData;
import com.yqbd.yqbdapp.annotation.VoDataField;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.constants.TaskStatus;
import com.yqbd.yqbdapp.utils.BaseJson;

public class SingleTaskActivity extends BaseActivity implements IActionCallBack {

    @VoDataField
    @BindView(R.id.task_title)
    TextView taskTitle;

    @BindView(R.id.task_button)
    Button taskButton;

    @VoDataField
    @BindView(R.id.task_description)
    TextView taskDescription;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.moments_appBar)
    AppBarLayout appBarLayout;

    @VoData
    private TaskBean taskBean;

    @Action
    private ITaskAction taskAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        ButterKnife.bind(this);
        initView();

        Bundle bundle = getIntent().getExtras();
        taskBean = (TaskBean) bundle.getSerializable("taskBean");
        initializeTop(true, taskBean.getTaskTitle());
        taskAction.isUserTaken(bundle.getInt("userId"), taskBean.getTaskId());
    }

    @Override
    protected void initView() {
        super.initView();
        appBarLayout = (AppBarLayout) findViewById(R.id.moments_appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @OnClick(R.id.task_button)
    public void onViewClicked() {
        switch (TaskStatus.valueOf(taskBean.getTaskStatus())) {

        }
    }

    private void resetTaskButton(Boolean result) {
        if (result) {
            taskButton.setText("取消接受");
        } else {
            taskButton.setText("接受任务");
        }
    }

    @Override
    public void OnSuccess(BaseJson baseJson) {
        try {
            if (taskAction.getCurrentUserID() == taskBean.getUserId()) {
                taskButton.setText("取消发布");
            } else {
                if (baseJson.getSingleBooleanResult()) {
                    taskButton.setText("取消接受");
                } else {
                    taskButton.setText("接受任务");
                }
            }
        } catch (Exception e) {
            onFailed(baseJson);
        }
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        Log.d(getLogTag(), baseJson.getErrorMessage());
        makeToast("Failed");
    }
}
