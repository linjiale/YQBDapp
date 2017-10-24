package com.yqbd.yqbdapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.IUserAction;
import com.yqbd.yqbdapp.activities.initial.InitialActivity;
import com.yqbd.yqbdapp.activities.task.TaskListActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.annotation.VoData;
import com.yqbd.yqbdapp.annotation.VoDataField;
import com.yqbd.yqbdapp.beans.UserInfoBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.BaseJson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener, IActionCallBack {

    private static final String ARG_PARAM1 = "userID";


    private Integer userID;

    @VoDataField
    @BindView(R.id.head_portrait)
     ImageView headPortrait;

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private Button footMark;
    private Button logout;
    private Button published;
    private Button taken;

    @Action
    private IUserAction userAction;

    @VoData
    private UserInfoBean userInfoBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        userAction.getUserInfoByUserID(userID);
        //initializeTop(view, false, "个人主页");
        //EventBusUtils.register();
        //new Thread(new PersonalThread()).start();
    }

    @OnClick({R.id.logout, R.id.my_published, R.id.my_taken})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
                editor.clear().commit();
                editor = getActivity().getSharedPreferences("companyInfo", Context.MODE_PRIVATE).edit();
                editor.clear().commit();
                Intent intent = new Intent();
                intent.setClass(getContext(), InitialActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.my_published:
                Intent intent2 = new Intent(getActivity(), TaskListActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putInt("tasksType", 0);
                intent2.putExtras(bundle);
                intent2.putExtra("title", "我发布的任务");
                startActivity(intent2);
                break;
            case R.id.my_taken:
                Intent intent3 = new Intent(getActivity(), TaskListActivity.class);
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
            Gson gson = new Gson();
            UserInfoBean userInfoBean = baseJson.getBean(UserInfoBean.class);

            //UserInfoBean userInfoBean = UserInfoBean.newInstance(baseJson.getJSONObject());
            nickName.setText(userInfoBean.getNickName().toString());
            sex.setText(userInfoBean.getSex().toString());
            idNumber.setText(userInfoBean.getAccountNumber().toString());
            professionalLevel.setText(userInfoBean.getProfessionalLevel().toString());
            creditLevel.setText(userInfoBean.getCreditLevel().toString());
        } catch (Exception e) {

        }
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        makeToast("Failed");
    }

    public PersonalFragment() {
        // Required empty public constructor
    }


    public static PersonalFragment newInstance(int userID) {
        PersonalFragment fragment = new PersonalFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getInt(ARG_PARAM1);
        }
    }
}
