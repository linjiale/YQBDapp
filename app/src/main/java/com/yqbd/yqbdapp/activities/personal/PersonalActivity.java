package com.yqbd.yqbdapp.activities.personal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.IUserAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.activities.initial.InitialActivity;
import com.yqbd.yqbdapp.activities.task.TaskListActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.annotation.VoData;
import com.yqbd.yqbdapp.annotation.VoDataField;
import com.yqbd.yqbdapp.beans.UserInfoBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.AsyncBitmapLoader;
import com.yqbd.yqbdapp.utils.BaseJson;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalActivity extends BaseActivity implements View.OnClickListener, IActionCallBack {

    @VoDataField
    @BindView(R.id.head_portrait)
    CircleImageView headPortrait;

    @VoDataField
    @BindView(R.id.nick_name)
    TextView nickName;

    @VoDataField
    @BindView(R.id.sex)
    TextView sex;

    @VoDataField
    @BindView(R.id.id_number)
    TextView idNumber;

    @VoDataField
    @BindView(R.id.professional_level)
    TextView professionalLevel;

    @VoDataField
    @BindView(R.id.credit_level)
    TextView creditLevel;

    @BindView(R.id.telephone)
    TextView telephone;
    @BindView(R.id.school)
    TextView school;
    @BindView(R.id.occupation)
    TextView occupation;
    @BindView(R.id.company_name)
    TextView companyName;

    @Action
    private IUserAction userAction;

    @VoData
    private UserInfoBean userInfoBean;

    private Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        initView();
        initializeTop(true, "账号信息");
        userID = getIntent().getIntExtra("userID", 0);
        userAction.getUserInfoByUserID(userID);
    }

    @OnClick({R.id.logout, R.id.my_taken})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                SharedPreferences.Editor editor = getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
                editor.clear().commit();
                editor = getSharedPreferences("companyInfo", Context.MODE_PRIVATE).edit();
                editor.clear().commit();
                Intent intent = new Intent();
                intent.setClass(this, InitialActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.my_taken:
                Intent intent3 = new Intent(this, TaskListActivity.class);
                Bundle bundle2 = new Bundle();
                //bundle2.putInt("tasksType", 1);
                intent3.putExtras(bundle2);
                intent3.putExtra("title", "我接受的任务");
                startActivity(intent3);
                break;
        }
    }

    @Override
    public void OnSuccess(BaseJson baseJson) {
        try {
            UserInfoBean userInfoBean = baseJson.getBean(UserInfoBean.class);

            //UserInfoBean userInfoBean = UserInfoBean.newInstance(baseJson.getJSONObject());
            nickName.setText(userInfoBean.getNickName().toString());
            sex.setText(userInfoBean.getSex().toString());
            idNumber.setText(userInfoBean.getAccountNumber().toString());
            professionalLevel.setText(userInfoBean.getProfessionalLevel().toString());
            creditLevel.setText(userInfoBean.getCreditLevel().toString());
            telephone.setText(userInfoBean.getTelephone());
            occupation.setText(userInfoBean.getOccupation());
            school.setText(userInfoBean.getSchool());
            companyName.setText(userInfoBean.getCompanyName());
            Bitmap bitmap = asyncBitmapLoader.loadBitmap(headPortrait, userInfoBean.getHeadPortrait(), headPortrait.getLayoutParams().width, headPortrait.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
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
        } catch (Exception e) {

        }
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        makeToast("Failed");
    }

}
