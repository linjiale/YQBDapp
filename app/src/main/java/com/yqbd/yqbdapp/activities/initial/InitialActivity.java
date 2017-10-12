package com.yqbd.yqbdapp.activities.initial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.activities.login.UserLoginActivity;
import com.yqbd.yqbdapp.activities.main.MainActivity;

public class InitialActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", 0);
        sharedPreferences = getSharedPreferences("companyInfo", Context.MODE_PRIVATE);
        int companyID = sharedPreferences.getInt("companyID", 0);
        if (companyID != 0 || userID != 0) {
            Intent intent = new Intent();
            intent.setClass(InitialActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.user_login).setOnClickListener(this);
        findViewById(R.id.company_login).setOnClickListener(this);
        findViewById(R.id.company_register).setOnClickListener(this);
        findViewById(R.id.user_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.user_login:
                intent.setClass(InitialActivity.this, UserLoginActivity.class);
                break;
            case R.id.company_login:
                //intent.setClass(InitialActivity.this, CompanyLoginActivity.class);
                break;
            case R.id.company_register:
                intent.setClass(InitialActivity.this, MainActivity.class);
                break;
            case R.id.user_register:
                intent.setClass(InitialActivity.this, MainActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
