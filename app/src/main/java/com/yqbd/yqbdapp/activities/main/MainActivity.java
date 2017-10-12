package com.yqbd.yqbdapp.activities.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.IBaseAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.fragments.PersonalFragment;
import com.yqbd.yqbdapp.fragments.TestFragment;
import com.yqbd.yqbdapp.fragments.filter.FilterFragment;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    private Fragment oldFragment, fragment;

    @Action
    private IBaseAction baseAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        TextBadgeItem numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.RED)
                .setText("3")
                .setHideOnSelect(true);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.bottom_my, "主页").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.bottom_my, "历史订单").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.bottom_my, "个人信息").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise();

        //fragments = getFragments();
        //setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
        onTabSelected(0);
    }

    @Override
    public void onTabSelected(int position) {
        /*if (fragments != null) {
            if (position < fragments.size()) {*/
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //Fragment fragment=null; //= fragments.get(position);
        oldFragment = fragment;
        switch (position) {
            case 0:
                fragment = TestFragment.newInstance();
                break;
            case 1:
                fragment = FilterFragment.newInstance();
                break;
            case 2:
                int userID = baseAction.getCurrentUserID();
                fragment = PersonalFragment.newInstance(userID);
                break;
        }
        if (fragment.isAdded()) {
            ft.replace(R.id.layFrame, fragment);
        } else {
            ft.add(R.id.layFrame, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {
        /*if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }*/
        if (oldFragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(oldFragment);
            ft.commitAllowingStateLoss();
        }

    }

    @Override
    public void onTabReselected(int position) {

    }
}
