package com.yqbd.yqbdapp.activities.task;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.activities.main.MainActivity;
import com.yqbd.yqbdapp.activities.personal.PersonalActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.annotation.VoData;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.beans.TypeBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.constants.TaskStatus;
import com.yqbd.yqbdapp.tagview.widget.Tag;
import com.yqbd.yqbdapp.tagview.widget.TagListView;
import com.yqbd.yqbdapp.utils.AsyncBitmapLoader;
import com.yqbd.yqbdapp.utils.BaseJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleTaskActivity extends BaseActivity implements IActionCallBack {

    private AsyncBitmapLoader asyncBitmapLoader = AsyncBitmapLoader.asyncBitmapLoader;
    private final List<Tag> mTags = new ArrayList<Tag>();
    @BindView(R.id.task_button)
    Button taskButton;

    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_description)
    TextView taskDescription;
    @BindView(R.id.primary_work)
    TextView primaryWork;
    @BindView(R.id.task_address)
    TextView taskAddress;
    @BindView(R.id.pay)
    TextView pay;
    @BindView(R.id.max_people_number)
    TextView maxPeopleNumber;
    @BindView(R.id.primary_contact)
    TextView primaryContact;
    @BindView(R.id.other_company)
    TextView otherCompany;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.complete_time)
    TextView completeTime;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.moments_appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tagview)
    TagListView tagview;
    @BindView(R.id.participant_button)
    Button participantButton;
    @VoData
    private TaskBean taskBean;

    @Action
    private ITaskAction taskAction;

    private Integer userId;

    private MenuItem collectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        ButterKnife.bind(this);
        initView();

        Bundle bundle = getIntent().getExtras();
        taskBean = (TaskBean) bundle.getSerializable("taskBean");
        initializeTop(true, taskBean.getTaskTitle());
        //taskAction.isUserTaken(bundle.getInt("userId"), taskBean.getTaskId());
        taskTitle.setText(taskBean.getTaskTitle());
        taskDescription.setText(taskBean.getTaskDescription());
        primaryWork.setText(taskBean.getPrimaryWork());
        primaryContact.setText(taskBean.getPrimaryContact());
        taskAddress.setText(taskBean.getTaskAddress());
        remark.setText(taskBean.getRemark());
        otherCompany.setText(taskBean.getOtherCompany());
        maxPeopleNumber.setText(taskBean.getMaxPeopleNumber().toString());
        pay.setText(taskBean.getPay().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        startTime.setText(simpleDateFormat.format(new Date(taskBean.getStartTime())));
        completeTime.setText(simpleDateFormat.format(new Date(taskBean.getCompleteTime())));
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskAction.take(taskBean.getTaskId());
            }
        });
        participantButton.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent();
            @Override
            public void onClick(View v) {
                intent.setClass(SingleTaskActivity.this, PeopleActivity.class);
                intent.putExtra("taskId", String.valueOf(taskBean.getTaskId()));
                intent.putExtra("title", "参与者");
                startActivity(intent);
            }
        });
        taskAction.isTake(taskBean.getTaskId());
        imageButton.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;

            @Override
            public void onClick(View v) {
                if (flag) {
                    flag = false;
                    imageButton.setImageResource(R.drawable.sort_common_up);
                    taskDescription.setEllipsize(null); // 展开
                    taskDescription.setSingleLine(flag);

                } else {
                    flag = true;
                    imageButton.setImageResource(R.drawable.sort_common_down);
                    taskDescription.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    taskDescription.setLines(2);

                }
            }

        });

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        //System.out.println(layoutParams.width + "     " + layoutParams.height);
        Bitmap bitmap = asyncBitmapLoader.loadBitmap(imageView, taskBean.getSimpleDrawingAddress(), imageView.getLayoutParams().width, imageView.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
                //item.picture = bitmap;
            }
        });
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        setUpData();
    }


    private void setUpData() {
        for (TypeBean typeBean : taskBean.getTypeBeans()) {
            Tag tag = new Tag();
            tag.setId(typeBean.getTypeId());
            tag.setChecked(true);
            tag.setTitle(typeBean.getTypeName());
            mTags.add(tag);
        }
        tagview.setTags(mTags);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.top_toolbar_style, menu);
        collectButton = menu.getItem(0);
        taskAction.isCollect(taskBean.getTaskId());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODOAuto-generated method stub
        //Toast.makeText(this,item.getTitle()+String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
        //区分被点击的item
        switch (item.getItemId()) {
            case R.id.action_collect:
                taskAction.collect(taskBean.getTaskId());
                break;
            case R.id.action_share:
                makeToast("获得测试资格才可使用");
                break;


        }
        return super.onOptionsItemSelected(item);
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

    private void resetCollectButton(Boolean result) {
        if (result) {
            collectButton.setIcon(R.drawable.collect);
        } else {
            collectButton.setIcon(R.drawable.collect_white);
        }
    }

    @Override
    public void OnSuccess(BaseJson baseJson) {
        try {
            switch (baseJson.getReturnCode()) {
                case "1.0.C.0":
                    resetCollectButton(baseJson.getSingleBooleanResult());
                    break;
                case "1.0.T.0":
                    resetTaskButton(baseJson.getSingleBooleanResult());
                    break;
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
