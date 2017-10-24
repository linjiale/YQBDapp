package com.yqbd.yqbdapp.activities.initial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.IUserAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.activities.login.RegisterActivity;
import com.yqbd.yqbdapp.activities.login.UserLoginActivity;
import com.yqbd.yqbdapp.activities.main.MainActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.BaseJson;

import java.util.Random;

public class InitialActivity extends BaseActivity implements View.OnClickListener ,IActionCallBack {

    @Action
    private IUserAction userAction;
    private SharedPreferences sharedPreferences;


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
        findViewById(R.id.user_register).setOnClickListener(this);
        findViewById(R.id.skip).setOnClickListener(this);
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.user_login:
                intent.setClass(InitialActivity.this, UserLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.user_register:
                intent.setClass(InitialActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.skip:
                String accountNumberString = getRandomString(12);
                String userPasswordString = getRandomString(12);
                userAction.register(accountNumberString,userPasswordString,"hello");
                break;
        }
    }

    @Override
    public void OnSuccess(BaseJson baseJson) {
        try {
            sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.putInt("userID", baseJson.getSingleIntegerResult());
            editor.commit();
            Intent intent = new Intent();
            intent.setClass(InitialActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            makeToast("登录失败");
        }
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        makeToast(baseJson.getErrorMessage());
    }
}
