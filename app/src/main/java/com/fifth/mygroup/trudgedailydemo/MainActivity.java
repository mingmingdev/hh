package com.fifth.mygroup.trudgedailydemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fifth.mygroup.trudgedailydemo.Fragment.HeadFragment;
import com.fifth.mygroup.trudgedailydemo.Fragment.UserFragment;
import com.fifth.mygroup.trudgedailydemo.Fragment.VideoFragment;
import com.fifth.mygroup.trudgedailydemo.Helper.BitmapCacheHelper;
import com.fifth.mygroup.trudgedailydemo.activities.NotificationActivity;
import com.fifth.mygroup.trudgedailydemo.activities.PersonalActivity;
import com.fifth.mygroup.trudgedailydemo.activities.SortActivity;
import com.fifth.mygroup.trudgedailydemo.activities.WelcomeActivity;
import com.fifth.mygroup.trudgedailydemo.adapteres.MainPagerAdapter;
import com.fifth.mygroup.trudgedailydemo.beans.IndexOfMain;
import com.fifth.mygroup.trudgedailydemo.constans.BaozouAPI;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    public static BitmapCacheHelper bitmapCacheHelper;
    public static boolean isLogined;

    private Toolbar toolbar;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    //网络
    public static boolean isNetWorkConnected;
    private HttpUtils httpUtils;

    public static BitmapUtils bitmapUtils;

    private static IndexOfMain indexOfMain;

    private MainPagerAdapter adapter;

    private DrawerLayout drawerLayout;

    //drawerLayout的功能
    private TextView drawerCollect,drawerJudge,drawerRead;
    private LinearLayout drawerHead,drawerSort,drawerChannel,drawerSearch,drawerSetting,drawerDowennload,
        drawerNeightMode;
//    private
    private long firstExitTime;

    //保存
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sharedPreferences=getSharedPreferences("daily",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if(bitmapCacheHelper==null){
            bitmapCacheHelper=new BitmapCacheHelper();
        }
        if(bitmapUtils==null) {
            bitmapUtils = new BitmapUtils(this);
        }
        //沉浸式状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        fragments=new ArrayList<Fragment>();
        initView();

        //监测网络
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo !=null) {
//            Toast.makeText(this, "网络已打开", Toast.LENGTH_LONG).show();
            isNetWorkConnected=true;
        }else {

            isNetWorkConnected=false;
        }

        getIndex();

        //侧面菜单

        drawerLayout();

    }

    private void drawerLayout() {
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        //打开和关闭
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle= new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0,
                0);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        toolbar.setNavigationIcon(R.mipmap.btn_home_menu_nor);
        //内容监听
        LinearLayout login= (LinearLayout) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //findView
        //drawerCollect,getDrawerJudge,getDrawerRead;

        drawerRead= (TextView) findViewById(R.id.drawer_read);
        drawerCollect= (TextView) findViewById(R.id.drawer_collect);
        drawerJudge= (TextView) findViewById(R.id.drawer_judge);

        drawerHead= (LinearLayout) findViewById(R.id.drawer_head);
        drawerSort= (LinearLayout) findViewById(R.id.drawer_sort);
        drawerChannel= (LinearLayout) findViewById(R.id.drawer_channel);
        drawerSearch= (LinearLayout) findViewById(R.id.drawer_search);
        drawerSetting= (LinearLayout) findViewById(R.id.drawer_setting);
        drawerDowennload= (LinearLayout) findViewById(R.id.drawer_download);
        drawerNeightMode= (LinearLayout) findViewById(R.id.drawer_model);

        //监听
        drawerRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerRead();
            }
        });
        drawerJudge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerJudge();
            }
        });
        drawerCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerCollect();
            }
        });
        // drawerHead,drawerSort,drawerChannel,drawerSearch,drawerSetting,drawerDowennload,
        //      drawerNeightMode;
        drawerHead.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                drawerHead();
            }
        });
        drawerSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerSort();
            }
        });
        drawerChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerChannel();
            }
        });
        drawerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerSearch();
            }
        });
        drawerSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerSetting();
            }
        });
        drawerDowennload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerDownnload();
            }
        });
        drawerNeightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerMode();
            }
        });
    }
    //夜间模式切换
    private boolean nightMode;
    private void drawerMode() {
        nightMode=!nightMode;
        if(!nightMode) {
            getApplication().setTheme(android.R.style.Theme_Black);
        }else{
            getApplication().setTheme(android.R.style.Theme_Holo_Light_NoActionBar);
        }
        drawerColor();
        drawerNeightMode.setBackgroundColor(getResources().getColor(R.color.padding));
    }

    //drawerLayout颜色切换
    private void drawerColor() {
        drawerHead.setBackgroundColor(Color.WHITE);
        drawerSort.setBackgroundColor(Color.WHITE);
        drawerChannel.setBackgroundColor(Color.WHITE);
        drawerSearch.setBackgroundColor(Color.WHITE);
        drawerSetting.setBackgroundColor(Color.WHITE);
        drawerDowennload.setBackgroundColor(Color.WHITE);
        drawerNeightMode.setBackgroundColor(Color.WHITE);
        drawerLayout.closeDrawers();
    }

    //离线下载
    private void drawerDownnload() {
        Toast.makeText(this,"未做",Toast.LENGTH_SHORT).show();
        drawerColor();
        drawerDowennload.setBackgroundColor(getResources().getColor(R.color.padding));
    }

    //设置
    private void drawerSetting() {
        Toast.makeText(this,"未做",Toast.LENGTH_SHORT).show();
        drawerColor();
        drawerSetting.setBackgroundColor(getResources().getColor(R.color.padding));
    }

    //搜索功能
    private void drawerSearch() {
        Toast.makeText(this,"未做",Toast.LENGTH_SHORT).show();
        drawerColor();
        drawerSearch.setBackgroundColor(getResources().getColor(R.color.padding));
    }

    //频道功能
    private void drawerChannel() {
        Toast.makeText(this,"未做",Toast.LENGTH_SHORT).show();
        drawerColor();
        drawerChannel.setBackgroundColor(getResources().getColor(R.color.padding));
    }

    //排行榜
    private void drawerSort() {
        startActivity(new Intent(this, SortActivity.class));
        drawerColor();
        drawerSort.setBackgroundColor(getResources().getColor(R.color.padding));
    }

    //首页
    private void drawerHead() {
        Toast.makeText(this,"未做",Toast.LENGTH_SHORT).show();
        drawerColor();
        drawerHead.setBackgroundColor(getResources().getColor(R.color.padding));
    }

    //收藏功能
    private void drawerCollect() {
        Toast.makeText(this,"未做",Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawers();
    }

    //评论
    private void drawerJudge() {
        Toast.makeText(this,"未做",Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawers();
    }

    //阅读功能
    private void drawerRead() {
        Toast.makeText(this,"未做",Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawers();
    }

    //登陆功能实现
    private void login() {
        startActivity(new Intent(this, PersonalActivity.class));
        drawerLayout.closeDrawers();
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.mytoolBar);
//        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("暴走日报");
        toolbar.setBackgroundResource(R.color.mytoolbarColor);
        toolbar.inflateMenu(R.menu.main_toolbar);

                Toolbar.OnMenuItemClickListener menuItemClickListener= new Toolbar.OnMenuItemClickListener(

                ) {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_edit:
                                showPopupWindow();
                                break;
                            case R.id.action_share:
                                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                                break;
                            case R.id.action_settings1:
                                Toast.makeText(MainActivity.this,"选项1",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_settings2:
                                Toast.makeText(MainActivity.this,"选项2",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_settings3:
                                Toast.makeText(MainActivity.this,"选项3",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
            }

        };

        toolbar.setOnMenuItemClickListener(menuItemClickListener);
        //viewpager
        viewPager= (ViewPager) findViewById(R.id.main_pager);
        HeadFragment headFragment=new HeadFragment();
        UserFragment userFragment=new UserFragment();
        VideoFragment videoFragment=new VideoFragment();
        fragments.add(headFragment);
        fragments.add(userFragment);
        fragments.add(videoFragment);
        fragmentManager=getSupportFragmentManager();
        adapter=new MainPagerAdapter(fragmentManager,fragments);


        //tab
        tabLayout= (TabLayout) findViewById(R.id.main_tab);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText("首页");
        tabLayout.addTab(tab);

        tab=tabLayout.newTab();
        tab.setText("用户投稿");
        tabLayout.addTab(tab);

        tab=tabLayout.newTab();
        tab.setText("视频");
        tabLayout.addTab(tab);



//        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    private void getIndex(){
        if(MainActivity.isNetWorkConnected) {
            new Thread(new Runnable (){
                public void run() {
                    httpUtils = new HttpUtils();
                    httpUtils.send(HttpMethod.GET, BaozouAPI.INDEX,
                            new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    showData(responseInfo.result);
                                    editor.putString("data",responseInfo.result);
                                    editor.commit();
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {
                                    showData(sharedPreferences.getString("data",null));
                                }
                            });
                }
            }).start();

        } else {
            showData(sharedPreferences.getString("data",null));
        }
    }

    private void showData(String data) {
        Log.d("heheda",data);
        if(data!=null) {
            indexOfMain = new IndexOfMain();
            Gson gson = new Gson();
            indexOfMain = gson.fromJson(data, IndexOfMain.class);
            Bundle bundle0 = new Bundle();
            bundle0.putString("head", indexOfMain.getData().get(0).getUrl());
            fragments.get(0).setArguments(bundle0);
            Bundle bundle1 = new Bundle();
            bundle1.putString("user", indexOfMain.getData().get(1).getUrl());
            fragments.get(1).setArguments(bundle1);
            Bundle bundle2 = new Bundle();
            bundle2.putString("video", indexOfMain.getData().get(2).getUrl());
            fragments.get(2).setArguments(bundle2);
            viewPager.setAdapter(adapter);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
//        firstExitTime=System.currentTimeMillis();
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstExitTime > 2000) {
                firstExitTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        }

        return false;
    }
    private void showPopupWindow(){
        //一个自定义的布局 作为显示内容
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_window,null);
        //设置按钮的点击事件

        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });


        final PopupWindow popupWindow=new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                //如果返回true,touch事件将会被拦截
                //拦截后，PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        //如果不设置PopupWindow背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorBack));
        popupWindow.showAsDropDown(toolbar);
        LinearLayout lly_tj= (LinearLayout) contentView.findViewById(R.id.lly_tj);
        lly_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "推荐链接", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        LinearLayout lly_tg= (LinearLayout) contentView.findViewById(R.id.lly_tg);
        lly_tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "投稿文章", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

    }
}
