package com.fifth.mygroup.trudgedailydemo.activities;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fifth.mygroup.trudgedailydemo.R;
import com.fifth.mygroup.trudgedailydemo.beans.TopStory;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsItemActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private WebView webView;
    private String url;
    private Button button;
    private ImageView imageView;
    private HttpUtils httpUtils;
    private String body;

    private float height ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);

        url = getIntent().getStringExtra("oneUrl");
        httpUtils = new HttpUtils();
        initView();
        click();
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    private void click() {
        imageView = (ImageView) findViewById(R.id.webViewShare);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        button = (Button) findViewById(R.id.webViewSeeComment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsItemActivity.this, "看评论", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        toolBar = (Toolbar) findViewById(R.id.itemToolBar);

        webView = (WebView) findViewById(R.id.webView);
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        int scale = dm.densityDpi;
//        if (scale == 240) { //
//            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (scale == 160) {
//            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        } else {
//            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        }

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setDisplayZoomControls(false);
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setHorizontalScrollBarEnabled(false);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.setInitialScale(70);
//        webView.setHorizontalScrollbarOverlay(true);
//        Toast.makeText(this,url,Toast.LENGTH_LONG).show();
        if (url.contains("dailyapi.ibaozou.com")) {
            httpUtils.send(HttpMethod.GET, url,
                    new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            try {
                                JSONObject obj = new JSONObject(responseInfo.result);
                                body = obj.getString("body");

                                //允许webview上的网页使用javasctipt
                                webView.getSettings().setJavaScriptEnabled(true);
                                //支持alert()这样的Javascript脚本，就是允许网页弹窗
                                webView.setWebChromeClient(new WebChromeClient());
                                //如果不使用此代码，超链接会跳入到浏览器
                                webView.setWebViewClient(new WebViewClient());
                                webView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(NewsItemActivity.this, "抱歉，加载不了了，亲", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            webView.loadUrl(url);
        }
        button = (Button) findViewById(R.id.webViewSeeComment);
        imageView = (ImageView) findViewById(R.id.webViewShare);
        toolBar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolBar.inflateMenu(R.menu.webviewmenu);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.webViewFlush:
                        Toast.makeText(NewsItemActivity.this, "刷新", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.webViewSetting:
                        Toast.makeText(NewsItemActivity.this, "设置 ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.webViewOpen:
                        Toast.makeText(NewsItemActivity.this, "在浏览器打开", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


        webView.setOnTouchListener(new View.OnTouchListener() {
            private float lastY;
            private int count;

            private boolean canShare = true;
            private boolean canComment=true;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                height=webView.getContentHeight()*webView.getScale();

                if (  !canComment&&height== v.getScrollY() + v.getHeight()) {
                    canComment = true;
                    ObjectAnimator out = ObjectAnimator.ofFloat(button, "translationY", 200, 0);
                    out.setDuration(150);
                    out.start();
                }
                if ( canComment && height > v.getScrollY() + v.getHeight() ) {
                    canComment = false;
                    ObjectAnimator out = ObjectAnimator.ofFloat(button, "translationY", 0, 200);
                    out.setDuration(150);
                    out.start();
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        count = 0;
                        lastY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if (count < 1) {
                            if (!canShare && event.getRawY() > lastY) {
                                canShare = true;
                                ObjectAnimator oa_x = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1);
                                ObjectAnimator oa_y = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 1);
                                AnimatorSet set = new AnimatorSet();
                                set.setDuration(300);
                                set.playTogether(oa_x, oa_y);
                                set.start();
                            }
                            if (canShare && event.getRawY() < lastY) {
                                canShare = false;
                                ObjectAnimator oa_x = ObjectAnimator.ofFloat(imageView, "scaleX", 1, 0);
                                ObjectAnimator oa_y = ObjectAnimator.ofFloat(imageView, "scaleY", 1, 0);
                                AnimatorSet set = new AnimatorSet();
                                set.setDuration(300);
                                set.playTogether(oa_x, oa_y);
                                set.start();
                            }
                            count++;
                        }
                        lastY = event.getRawY();
                        break;
                }


                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}
