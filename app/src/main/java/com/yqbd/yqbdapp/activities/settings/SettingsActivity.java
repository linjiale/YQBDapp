package com.yqbd.yqbdapp.activities.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.activities.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        initializeTop(true,"设置");
    }
}
