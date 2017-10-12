package com.yqbd.yqbdapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.activities.task.PublishTaskActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends BaseFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TestFragment() {
        // Required empty public constructor
    }


    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button).setOnClickListener(this);
        view.findViewById(R.id.btn_create_group).setOnClickListener(this);
//        view.findViewById(R.id.btn_delete_group).setOnClickListener(this);
        view.findViewById(R.id.btn_publishtask_longterm).setOnClickListener(this);
        view.findViewById(R.id.btn_toJoinGroup).setOnClickListener(this);
//        view.findViewById(R.id.btn_toCancelJoin).setOnClickListener(this);
//        view.findViewById(R.id.btn_toQuitGroup).setOnClickListener(this);
//        view.findViewById(R.id.btn_checkApplication).setOnClickListener(this);
//        view.findViewById(R.id.btn_checkQuit).setOnClickListener(this);

        view.findViewById(R.id.btn_toUserGroup).setOnClickListener(this);
        view.findViewById(R.id.btn_toCompanyGroup).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button:
                intent=new Intent(getContext(), PublishTaskActivity.class);
                startActivity(intent);
                break;
            /*case R.id.btn_create_group:
                intent=new Intent(getContext(), CreateGroupActivity.class);
                startActivity(intent);
                break;
//            case R.id.btn_delete_group:
//                intent=new Intent(getContext(), DeleteGroupActivity.class);
//                intent.putExtra("groupId",2);
//                startActivity(intent);
//                break;
            case R.id.btn_publishtask_longterm:
                intent=new Intent(getContext(), PublishLongTermTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toJoinGroup:
                intent=new Intent(getContext(), ApplyJoinActivity.class);
                //需求：点击列表项，传一个groupId
                intent.putExtra("groupId",1);
                startActivity(intent);
                break;*/
//            case R.id.btn_toCancelJoin:
//                intent=new Intent(getContext(), CancelJoinActivity.class);
//                //需求：点击列表项，传一个groupId
//                intent.putExtra("groupId",1);
//                startActivity(intent);
//                break;
//            case R.id.btn_toQuitGroup:
//                intent=new Intent(getContext(), ApplyQuitActivity.class);
//                //需求：点击列表项，传一个groupId
//                intent.putExtra("groupId",1);
//                startActivity(intent);
//                break;
//            case R.id.btn_checkApplication:
//                intent=new Intent(getContext(), CheckJoinActivity.class);
//                //需求：点击列表项，传一个groupId
//                intent.putExtra("groupId",2);
//                intent.putExtra("candidateId",2);
//                startActivity(intent);
//                break;
//            case R.id.btn_checkQuit:
//                intent=new Intent(getContext(), CheckQuitActivity.class);
//                //需求：点击列表项，传一个groupId
//                intent.putExtra("groupId",2);
//                intent.putExtra("candidateId",2);
//                startActivity(intent);
//                break;
            /*case R.id.btn_toUserGroup:
                intent=new Intent(getContext(), UserGroupActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toCompanyGroup:
                intent=new Intent(getContext(), CompanyGroupActivity.class);
                startActivity(intent);
                break;*/
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
