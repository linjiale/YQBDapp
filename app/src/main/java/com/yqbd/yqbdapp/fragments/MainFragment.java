package com.yqbd.yqbdapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.activities.task.SingleTaskActivity;
import com.yqbd.yqbdapp.adapter.MyTaskAdapter;
import com.yqbd.yqbdapp.adapter.RecommendTaskAdapter;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.BaseJson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment implements IActionCallBack {
    private List<TaskBean> tasks = new ArrayList<>();
    private RecyclerView taskList;
    private RecommendTaskAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    @Action
    private ITaskAction taskAction;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskList = (RecyclerView) getActivity().findViewById(R.id.recommendTaskList);
        linearLayoutManager = new LinearLayoutManager(this.getActivity(), OrientationHelper.HORIZONTAL, true);
        taskList.setLayoutManager(linearLayoutManager);
        adapter = new RecommendTaskAdapter(tasks, getActivity());
        taskList.setAdapter(adapter);
        taskAction.getAllTasks();
        adapter.setOnItemClickListener(new RecommendTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TaskBean taskBean) {
                Intent intent = new Intent(getActivity(), SingleTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("taskBean", taskBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnSuccess(BaseJson baseJson) {
        tasks.clear();
        tasks.addAll(baseJson.getBeanList(TaskBean[].class));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        Log.d(getLogTag(), baseJson.getErrorMessage());
        makeToast("Failed");
    }
}
