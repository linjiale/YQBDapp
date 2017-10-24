package com.yqbd.yqbdapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.ICompanyAction;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.activities.task.SingleTaskActivity;
import com.yqbd.yqbdapp.adapter.MyTaskAdapter;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.beans.CompanyInfoBean;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.beans.UserInfoBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.ActionInject;
import com.yqbd.yqbdapp.utils.AsyncBitmapLoader;
import com.yqbd.yqbdapp.utils.BaseJson;

import java.util.List;

public class CompanyInfoActivity extends BaseActivity implements IActionCallBack {
    private AsyncBitmapLoader asyncBitmapLoader = AsyncBitmapLoader.asyncBitmapLoader;
    private CompanyInfoBean companyInfoBean;

    private Integer companyId;

    @BindView(R.id.head_portrait)
    ImageView headPortrait;

    @BindView(R.id.summary)
    TextView summary;

    @BindView(R.id.myTaskList)
    RecyclerView myTaskList;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;

    private ICompanyAction companyAction;

    private MyTaskAdapter adapter;

    private LinearLayoutManager linearLayoutManager;

    @Action
    private ITaskAction taskAction;

    private List<TaskBean> taskBeanList = Lists.newArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        ButterKnife.bind(this);
        initView();
        //initializeTop(true, "账号信息");
        companyId = getIntent().getIntExtra("companyId", 0);
        companyAction = ActionInject.bindActionCallBack(this, ICompanyAction.class, new IActionCallBack() {
            @Override
            public void OnSuccess(BaseJson baseJson) {
                companyInfoBean = baseJson.getBean(CompanyInfoBean.class);
                summary.setText(companyInfoBean.getSummary());
                initializeTop(true, companyInfoBean.getCompanyName());
                Bitmap bitmap = asyncBitmapLoader.loadBitmap(headPortrait, companyInfoBean.getHeadPortraitAddress(), headPortrait.getLayoutParams().width, headPortrait.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
                    @Override
                    public void imageLoad(ImageView imageView, Bitmap bitmap) {
                        // TODO Auto-generated method stub
                        imageView.setImageBitmap(bitmap);
                        //item.picture = bitmap;
                    }
                });
                if (bitmap != null) {
                    headPortrait.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailed(BaseJson baseJson) {
                makeToast("连接错误");
            }
        });
        companyAction.getCompanyInfoByCompanyId(companyId);
        linearLayoutManager = new LinearLayoutManager(this);
        myTaskList.setLayoutManager(linearLayoutManager);
        adapter = new MyTaskAdapter(taskBeanList, this);
        myTaskList.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TaskBean taskBean) {
                Intent intent = new Intent(getApplicationContext(), SingleTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("taskBean", taskBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        taskAction.getCompanyTasks(companyId);
    }

    @Override
    protected void initView() {
        super.initView();
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
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

    @Override
    public void OnSuccess(BaseJson baseJson) {
        taskBeanList.addAll(baseJson.getBeanList(TaskBean[].class));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        makeToast("Failed");
    }
}
