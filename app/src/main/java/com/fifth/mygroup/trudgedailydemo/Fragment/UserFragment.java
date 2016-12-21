package com.fifth.mygroup.trudgedailydemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.fifth.mygroup.trudgedailydemo.R;
import com.fifth.mygroup.trudgedailydemo.activities.NewsItemActivity;
import com.fifth.mygroup.trudgedailydemo.adapteres.HeadListAdapter;
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
public class UserFragment extends Fragment{
    private String url;
    private HttpUtils httpUtils;
    private UserRecommender userRecommender;
    private View footView;
    private ListView listView;
    private Context context;
    private long timestamp;
    private Gson gson;
    private List<TopStory> datas;
    private HeadListAdapter adapter;
    private boolean loadOver;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_fragment, null);

        context=getContext();
        httpUtils = new HttpUtils();
        gson=new Gson();
        listView= (ListView) view.findViewById(R.id.user_content);
        footView=View.inflate(context,R.layout.listfootlayout,null);
        if(MainActivity.isNetWorkConnected) {
            if (getArguments() != null) {
                url = getArguments().getString("user");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpUtils.send(HttpMethod.GET, url,
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        loadOver = true;
                                        String responseString=responseInfo.result;
                                        showData(responseString);
                                        MainActivity.editor.putString("userData", responseInfo.result);
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
        }else{
            String videoData=MainActivity.sharedPreferences.getString("userData",null);
            if(videoData!=null){
                showData(videoData);
            }
        }

        return view;
    }

    private void showData(String responseString) {
        userRecommender = new UserRecommender();
        userRecommender = gson.fromJson(responseString, UserRecommender.class);
        datas = userRecommender.getData();
        timestamp = userRecommender.getTimestamp();
        adapter = new HeadListAdapter(datas, context);
        listView.setAdapter(adapter);
        setListListener();
        listView.addFooterView(footView);
        setListViewListener();
    }

    private void setListListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NewsItemActivity.class);
                intent.putExtra("oneUrl", datas.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    private void setListViewListener() {
//        Toast.makeText(context, timestamp + "", Toast.LENGTH_SHORT).show();
        listView.setOnScrollListener(new ListView.OnScrollListener() {
            private boolean lookOver;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(scrollState==SCROLL_STATE_IDLE && lookOver&&loadOver){
                    lookOver=false;
                    loadOver=false;
//                    Toast.makeText(context,url + "?timestamp=" + timestamp + "&",Toast.LENGTH_SHORT).show();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ?timestamp=1463382000&
                    httpUtils.send(HttpMethod.GET, url + "?timestamp=" + timestamp + "&",
                            new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    loadOver=true;
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
                    lookOver=true;
                    footView.setVisibility(View.VISIBLE);
                }else {
                    lookOver=false;
                }
            }
        });
    }
    private void addData(String result) {
        UserRecommender userRecommenderAdd = gson.fromJson(result, UserRecommender.class);
        timestamp=userRecommenderAdd.getTimestamp();
        List<TopStory> more=userRecommenderAdd.getData();
        datas.addAll(more);
        adapter.addDatas(more);
    }
}
