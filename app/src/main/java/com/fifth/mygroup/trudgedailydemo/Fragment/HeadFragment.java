package com.fifth.mygroup.trudgedailydemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.fifth.mygroup.trudgedailydemo.R;
import com.fifth.mygroup.trudgedailydemo.activities.NewsItemActivity;
import com.fifth.mygroup.trudgedailydemo.adapteres.HeadListAdapter;
import com.fifth.mygroup.trudgedailydemo.adapteres.HeadPagerAdapter;
import com.fifth.mygroup.trudgedailydemo.beans.HeadData;
import com.fifth.mygroup.trudgedailydemo.beans.TopStory;
import com.fifth.mygroup.trudgedailydemo.beans.UserRecommender;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.util.List;

/**
 * Created by lenovo on 2016/5/18.
 */
public class HeadFragment extends Fragment{
    private HttpUtils httpUtils;
    private String url;
    private HeadData headData;

    private ViewPager viewPager;
    private HeadPagerAdapter adapter;
    private ListView listView;
    private View footView;
    private HeadListAdapter listAdapter;
    private static boolean loadOver;

    private LinearLayout linearLayout;
    private ImageView[] tips;
    private int count;

    private TextView textView;

    private Gson gson;

    private List<TopStory> topStories;
    private List<TopStory> datas;
    private long timestamp;
    private Context context;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if(url!=null&&count>0) {
                if (msg.what == 100) {
                    int i = viewPager.getCurrentItem();
                    if (i == topStories.size() - 1) {
                        i = -1;
                    }
                    i++;
                    viewPager.setCurrentItem(i, true);
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.headfragment,null);
        context=getContext();
        listView= (ListView) view.findViewById(R.id.head_content);
        httpUtils = new HttpUtils();
        //data
        if(MainActivity.isNetWorkConnected) {
            if (getArguments() != null) {
                url = getArguments().getString("head");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpUtils.send(HttpMethod.GET, url,
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        showData(responseInfo.result);
                                        MainActivity.editor.putString("headData", responseInfo.result);
                                        MainActivity.editor.commit();
                                    }
                                    @Override
                                    public void onFailure(HttpException e, String s) {
                                        Toast.makeText(context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).start();
            }
            //无网络
            else {
                if(footView!=null){
                    listView.removeFooterView(footView);
                }
            }
        }else{
            String data=MainActivity.sharedPreferences.getString("headData", null);
            if ( data!= null) {
                showData(data);
            }
        }
        //ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),NewsItemActivity.class);
//                Bundle bundle=new Bundle();
                intent.putExtra("oneUrl",datas.get(position-1).getUrl());
                startActivity(intent);
            }
        });
        footView=View.inflate(getContext(),R.layout.listfootlayout,null);
        listView.addFooterView(footView);
        return view;
    }
    private void setListViewListener() {
//        Toast.makeText(context,timestamp+"" ,Toast.LENGTH_SHORT).show();
        listView.setOnScrollListener(new ListView.OnScrollListener() {
            private boolean lookOver;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == SCROLL_STATE_IDLE && lookOver && loadOver) {
                    lookOver = false;
                    loadOver = false;
//                    Toast.makeText(context, timestamp + "&", Toast.LENGTH_SHORT).show();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ?timestamp=1463382000&
                    httpUtils.send(HttpMethod.GET, url + "?timestamp=" +timestamp + "&",
                            new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    loadOver = true;
                                    addData(responseInfo.result);
                                    footView.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {
                                    Toast.makeText(context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
//                    }).start();
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    lookOver = true;
                    footView.setVisibility(View.VISIBLE);
                } else {
                    lookOver = false;
                }
            }
        });
    }

    private void addData(String result) {
        UserRecommender userRecommender_ = gson.fromJson(result, UserRecommender.class);
        this.timestamp=userRecommender_.getTimestamp();
        List<TopStory> more=userRecommender_.getData();
        datas.addAll(more);
        listAdapter.addDatas(more);
    }

    private void showData(String result) {
        loadOver=true;
        //数据请求解析
        headData = new HeadData();
        gson = new Gson();
        headData = gson.fromJson(result, HeadData.class);
        topStories = headData.getTop_stories();
        datas = headData.getData();
        count = topStories.size();
        this.timestamp=headData.getTimestamp();

        if (topStories != null && datas != null) {
            View headView=View.inflate(context,R.layout.headviewpager,null);
            viewPager= (ViewPager) headView.findViewById(R.id.head_viewpager);
            textView= (TextView) headView.findViewById(R.id.head_pagerText);

            linearLayout= (LinearLayout) headView.findViewById(R.id.head_indexCircle);
            adapter = new HeadPagerAdapter(topStories, getContext(), textView);
            viewPager.setAdapter(adapter);

//            setPagerListener();
            textView.setText(topStories.get(0).getTitle());

            listView.addHeaderView(headView);
            listAdapter = new HeadListAdapter(datas, getContext());
            listView.setAdapter(listAdapter);
            startPager();
            setListViewListener();
        }
        //将点点加入到ViewGroup中
        tips = new ImageView[count];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(5, 5));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.bullet_white);
            } else {
                tips[i].setBackgroundResource(R.mipmap.bullet_grey);
            }

            LinearLayout.LayoutParams layoutParams = new
                    LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 3;
//                                        layoutParams.rightMargin = 3;
            linearLayout.addView(imageView, layoutParams);
        }

    }

//    private void setPagerListener() {
//        viewPager.setOn
//    }

    private void setTips(int position){
        for(int i=0;i<count;i++){
            linearLayout.getChildAt(i).setBackgroundResource(R.mipmap.bullet_grey);
        }
        linearLayout.getChildAt(position).setBackgroundResource(R.mipmap.bullet_white);
    }
    private void startPager(){
        //轮播
        new Thread(new Runnable() {
            @Override
            public void run() {


                while (true) {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(100);
                }
            }
        }).start();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText(topStories.get(position).getTitle());
                setTips(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
