package com.yqbd.yqbdapp.activities.main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.IBaseAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.activities.PersonalActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.fragments.MainFragment;
import com.yqbd.yqbdapp.fragments.PersonalFragment;
import com.yqbd.yqbdapp.fragments.TestFragment;
import com.yqbd.yqbdapp.fragments.filter.FilterFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, View.OnClickListener {

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    protected CircleImageView headPortraitCircleImageView;

    @BindView(R.id.toolBar_head_portrait)
    protected CircleImageView smallHeadPortraitCircleImageView;

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
        bottomNavigationBar.setInActiveColor(R.color.black)
                .setActiveColor(R.color.white)
                .setBarBackgroundColor(R.color.blue);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home, "主页"))
                .addItem(new BottomNavigationItem(R.drawable.handshake, "志愿"))
                .addItem(new BottomNavigationItem(R.drawable.interest, "志趣"))
                .setFirstSelectedPosition(0)
                .initialise();

        //fragments = getFragments();
        //setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
        onTabSelected(0);

        smallHeadPortraitCircleImageView.setOnClickListener(this);


        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.drawable.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
        navigationView.setItemIconTintList(csl);

        ViewGroup.LayoutParams params = navigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
        navigationView.setLayoutParams(params);
        //navigationView.setItemTextColor(R.color.white);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.personal:
                        Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
                        intent.putExtra("userID", baseAction.getCurrentUserID());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolBar_head_portrait:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.toolBar_head_portrait:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
                setToolBarTitleText("主页");
                fragment = MainFragment.newInstance();
                break;
            case 1:
                setToolBarTitleText("查看任务");
                fragment = FilterFragment.newInstance();
                break;
            case 2:
                setToolBarTitleText("个人主页");
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
