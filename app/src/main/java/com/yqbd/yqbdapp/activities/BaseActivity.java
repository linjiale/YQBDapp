package com.yqbd.yqbdapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.yqbd.yqbdapp.ActionInject;
import com.yqbd.yqbdapp.R;
import org.greenrobot.eventbus.EventBus;

public class BaseActivity extends DataActivity {
    public static final int REQUEST_CODE_PICK_IMAGE = 200;
    public static final int REQUEST_CODE_CAPTURE_CAMEIA = 201;

    protected TextView toolBarTitle;

    private EventBus eventBus;

    @Override
    public void onDestroy() {
        super.onDestroy();
        //eventBus.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus = new EventBus();
        //eventBus.register(this);
        ActionInject.bindActionCallBack(this);
    }

    protected void localPost(Object o) {
        eventBus.post(o);
    }

    protected void globlePost(Object o) {
        eventBus.post(o);
    }

    protected void initView() {
        //toolBarTitle = (TextView) findViewById(R.id.toolBar).findViewById(R.id.topToolBarTitle);
    }

    protected void setToolBarTitleText(String titleText) {
        toolBarTitle.setText(titleText);
    }

    public void initializeTop(boolean isShownReturnButton, String title) {
        android.support.v7.widget.Toolbar toolBar = (android.support.v7.widget.Toolbar) (findViewById(R.id.toolBar));
        TextView topToolBarTitle = (TextView) findViewById(R.id.toolBar).findViewById(R.id.topToolBarTitle);
        if (isShownReturnButton) {
            toolBar.setTitle(title);
            topToolBarTitle.setText("");
            setSupportActionBar(toolBar);
            //activity.getSupportActionBar().setTitle(title);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //toolBar.setNavigationIcon(R.drawable.return_img);
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            toolBar.setTitle("");
            topToolBarTitle.setText(title);
            setSupportActionBar(toolBar);
        }
    }
}
