package com.yqbd.yqbdapp.activities.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.IUserAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.activities.main.MainActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.BaseJson;

public class UserLoginActivity extends BaseActivity implements IActionCallBack, View.OnClickListener {

    @Action
    private IUserAction userAction;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        findViewById(R.id.login).setOnClickListener(this);
        initView();
        initializeTop(true, "登录");
    }

    private EditText getUserAccount() {
        return (EditText) findViewById(R.id.user_account);
    }

    private EditText getUserPassword() {
        return (EditText) findViewById(R.id.user_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                //TODO implement
                String accountNumberString = getUserAccount().getText().toString();
                String userPasswordString = getUserPassword().getText().toString();
                userAction.login(accountNumberString, userPasswordString);
                break;
        }
    }

    @Override
    public void OnSuccess(BaseJson baseJson) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.putInt("userID", baseJson.getSingleIntegerResult());
            editor.commit();
            Intent intent = new Intent();
            intent.setClass(UserLoginActivity.this, MainActivity.class);
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
