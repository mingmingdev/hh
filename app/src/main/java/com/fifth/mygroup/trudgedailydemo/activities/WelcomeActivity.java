package com.fifth.mygroup.trudgedailydemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.fifth.mygroup.trudgedailydemo.R;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends InstrumentedActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if (msg.what==100) {
                launch();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.sendEmptyMessageDelayed(100, 3000);


    }

    private void launch() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
////        JPushInterface.onResume(this);
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
////        JPushInterface.onPause(this);
//    }
}
