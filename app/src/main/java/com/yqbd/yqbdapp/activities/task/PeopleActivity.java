package com.yqbd.yqbdapp.activities.task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.collect.Lists;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.actions.IUserAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.adapter.MyTaskAdapter;
import com.yqbd.yqbdapp.adapter.PersonAdapter;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.beans.UserInfoBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.BaseJson;

import java.util.List;

public class PeopleActivity extends BaseActivity implements IActionCallBack  {

    @BindView(R.id.peopleList)
    RecyclerView peopleList;

    private PersonAdapter adapter;

    private LinearLayoutManager linearLayoutManager;

    @Action
    private ITaskAction taskAction;

    @Action
    private IUserAction userAction;

    private List<UserInfoBean> userInfoList = Lists.newArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        Log.d(getLogTag(), "title:" + title);
        initializeTop(true, intent.getStringExtra("title"));
        linearLayoutManager = new LinearLayoutManager(this);
        peopleList.setLayoutManager(linearLayoutManager);
        adapter = new PersonAdapter(userInfoList, this);
        peopleList.setAdapter(adapter);
        adapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, UserInfoBean userInfoBean) {
//                Intent intent = new Intent(getApplicationContext(), SingleTaskActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("taskBean", taskBean);
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });
        Log.d("hello",intent.getStringExtra("taskId"));
        taskAction.getParticipant(Integer.valueOf(intent.getStringExtra("taskId")));
    }

    @Override
    public void OnSuccess(BaseJson baseJson) {
        userInfoList.addAll(baseJson.getBeanList(UserInfoBean[].class));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        makeToast("Failed");
    }
}
