package com.yqbd.yqbdapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.yqbd.yqbdapp.utils.ActionInject;
import com.yqbd.yqbdapp.R;
import org.greenrobot.eventbus.EventBus;

public class BaseFragment extends Fragment {

    private EventBus eventBus;

    protected TextView toolBarTitle;

    @Override
    public void onDestroy() {
        super.onDestroy();
        //eventBus.unregister(this);
        if (eventBus != null) {
            eventBus.unregister(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionInject.bindActionCallBack(this);
        //eventBus = new EventBus();
        //eventBus.register(this);
    }

    protected void localPost(Object o) {
        if (eventBus == null) {
            eventBus = new EventBus();
            eventBus.register(this);
        }
        eventBus.post(o);
    }

    public void initializeTop(View view, boolean isShownReturnButton, String title) {
        android.support.v7.widget.Toolbar toolBar = (android.support.v7.widget.Toolbar) (view.findViewById(R.id.toolBar));
        TextView topToolBarTitle = (TextView) view.findViewById(R.id.toolBar).findViewById(R.id.topToolBarTitle);
        if (isShownReturnButton) {
            toolBar.setTitle(title);
            topToolBarTitle.setText("");
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
            //activity.getSupportActionBar().setTitle(title);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //toolBar.setNavigationIcon(R.drawable.return_img);
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        } else {
            toolBar.setTitle("");
            topToolBarTitle.setText(title);
            //((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
        }
    }

    protected void makeToast(final String content) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getLogTag() {
        return getClass().getSimpleName();
    }
}
