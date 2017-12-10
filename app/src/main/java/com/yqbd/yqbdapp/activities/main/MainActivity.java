package com.yqbd.yqbdapp.activities.main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.IBaseAction;
import com.yqbd.yqbdapp.actions.IUserAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.activities.personal.PersonalActivity;
import com.yqbd.yqbdapp.activities.settings.SettingsActivity;
import com.yqbd.yqbdapp.activities.task.TaskListActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.beans.UserInfoBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.fragments.BlankFragment;
import com.yqbd.yqbdapp.fragments.MainFragment;
import com.yqbd.yqbdapp.fragments.PersonalFragment;
import com.yqbd.yqbdapp.fragments.filter.FilterFragment;
import com.yqbd.yqbdapp.utils.AsyncBitmapLoader;
import com.yqbd.yqbdapp.utils.BaseJson;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, View.OnClickListener, IActionCallBack {

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    //@BindView(R.id.head_portrait)
    protected CircleImageView headPortraitCircleImageView;

    @BindView(R.id.toolBar_head_portrait)
    protected CircleImageView smallHeadPortraitCircleImageView;

    //@BindView(R.id.nick_name)
    protected TextView nickName;

    //@BindView(R.id.telephone)
    protected TextView telephone;

    private Fragment oldFragment, fragment;

    private UserInfoBean userInfoBean;

    @Action
    private IUserAction userAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        userAction.getUserInfoByUserID(userAction.getCurrentUserID());
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
        bottomNavigationBar.setInActiveColor(R.color.gray)
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
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.personal:
                        intent.setClass(MainActivity.this, PersonalActivity.class);
                        intent.putExtra("userID", userAction.getCurrentUserID());
                        startActivity(intent);
                        break;
                    case R.id.nav_collect:
                        intent.setClass(MainActivity.this, TaskListActivity.class);
                        //intent.putExtra("userID", baseAction.getCurrentUserID());
                        intent.putExtra("title", "我的收藏");
                        startActivity(intent);
                        break;
                    case R.id.nav_settings:
                        intent.setClass(MainActivity.this, SettingsActivity.class);
                        //intent.putExtra("userID", baseAction.getCurrentUserID());
                        startActivity(intent);
                        break;
                    case R.id.nav_take:
                        intent.setClass(MainActivity.this, TaskListActivity.class);
                        //intent.putExtra("userID", baseAction.getCurrentUserID());
                        intent.putExtra("title", "我接受的任务");
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                headPortraitCircleImageView = (CircleImageView) navigationView.findViewById(R.id.head_portrait);
                nickName = (TextView) navigationView.findViewById(R.id.nick_name);
                telephone = (TextView) navigationView.findViewById(R.id.telephone);
                nickName.setText(userInfoBean.getNickName());
                telephone.setText(userInfoBean.getTelephone());
                Bitmap bitmap = asyncBitmapLoader.loadBitmap(headPortraitCircleImageView, userInfoBean.getHeadPortrait(), headPortraitCircleImageView.getLayoutParams().width, headPortraitCircleImageView.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
                    @Override
                    public void imageLoad(ImageView imageView, Bitmap bitmap) {
                        // TODO Auto-generated method stub
                        imageView.setImageBitmap(bitmap);
                        //item.picture = bitmap;
                    }
                });
                if (bitmap != null) {
                    headPortraitCircleImageView.setImageBitmap(bitmap);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.top_toolbar_style, menu);
        return true;
    }

    private void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
        try{
            headPortraitCircleImageView = (CircleImageView) navigationView.findViewById(R.id.head_portrait);
            nickName = (TextView) navigationView.findViewById(R.id.nick_name);
            telephone = (TextView) navigationView.findViewById(R.id.telephone);
            nickName.setText(userInfoBean.getNickName());
            telephone.setText(userInfoBean.getTelephone());
            Bitmap bitmap = asyncBitmapLoader.loadBitmap(headPortraitCircleImageView, userInfoBean.getHeadPortrait(), headPortraitCircleImageView.getLayoutParams().width, headPortraitCircleImageView.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
                @Override
                public void imageLoad(ImageView imageView, Bitmap bitmap) {
                    // TODO Auto-generated method stub
                    imageView.setImageBitmap(bitmap);
                    //item.picture = bitmap;
                }
            });
            if (bitmap != null) {
                headPortraitCircleImageView.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            makeToast("连接错误");
        }
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
                setToolBarTitleText("志愿");
                fragment = FilterFragment.newInstance();
                break;
            case 2:
                setToolBarTitleText("志趣");
                int userID = userAction.getCurrentUserID();
                fragment = BlankFragment.newInstance();
                //fragment = PersonalFragment.newInstance(userID);
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

    @Override
    public void OnSuccess(BaseJson baseJson) {
        userInfoBean = baseJson.getBean(UserInfoBean.class);
        Bitmap bitmap1 = asyncBitmapLoader.loadBitmap(smallHeadPortraitCircleImageView, userInfoBean.getHeadPortrait(), smallHeadPortraitCircleImageView.getLayoutParams().width, smallHeadPortraitCircleImageView.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
                //item.picture = bitmap;
            }
        });
        if (bitmap1 != null) {
            smallHeadPortraitCircleImageView.setImageBitmap(bitmap1);
        }
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        makeToast("连接错误");
    }
}
