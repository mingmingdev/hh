package com.fifth.mygroup.trudgedailydemo.activities;

import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.fifth.mygroup.trudgedailydemo.R;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;
import java.util.logging.Handler;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class PersonalActivity extends AppCompatActivity implements PlatformActionListener{

    private Toolbar personToolBar;

    private RelativeLayout userLoginView;
    private Platform wechat;
    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ShareSDK.initSDK(this);
        //沉浸式状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        toolbar();
        wechat = ShareSDK.getPlatform(this, Wechat.NAME);
        if(!wechat.isAuthValid()) {
            wechat.setPlatformActionListener(this);
            wechat.authorize();
        }
        findViews();
        personInfo();

    }

    private void findViews() {
        userLoginView= (RelativeLayout) findViewById(R.id.userLoginView);
        userLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.isLogined) {
                    authorize(wechat);
                }
            }
        });

    }

    private void personInfo() {
        if(!MainActivity.isLogined){

        }
    }
    private void authorize(Platform plat) {
        if (plat == null) {
//            popupOthers();
            return;
        }

//判断指定平台是否已经完成授权
        if(plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
//                UIHandler.sendEmptyMessage(, this);
//                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        //获取用户资料
        plat.showUser(null);
    }

    private void toolbar() {
        personToolBar= (Toolbar) findViewById(R.id.personToolBar);
        personToolBar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        personToolBar.setTitleTextColor(Color.WHITE);
        personToolBar.setTitle("个人");
        personToolBar.setBackgroundResource(R.color.mytoolbarColor);
        personToolBar.inflateMenu(R.menu.person_toolbar);
        personToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Toolbar.OnMenuItemClickListener menuItemClickListener= new Toolbar.OnMenuItemClickListener(

        ) {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_settings1:
                        Toast.makeText(PersonalActivity.this,"选项1",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_settings2:
                        Toast.makeText(PersonalActivity.this,"选项2",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_settings3:
                        Toast.makeText(PersonalActivity.this,"选项3",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

        };

        personToolBar.setOnMenuItemClickListener(menuItemClickListener);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {


// result(res);// chǔ理结果
            Message msg = new Message();
            msg.what = 0;
            msg.obj = hashMap;
            handler.sendMessage(msg);
        }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message msg = new Message();
        msg.what = 1;
        msg.obj = platform;
        handler.sendMessage(msg);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = platform;
        handler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}
