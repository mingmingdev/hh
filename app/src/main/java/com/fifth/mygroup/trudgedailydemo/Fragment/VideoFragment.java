package com.fifth.mygroup.trudgedailydemo.Fragment;

import android.content.Context;
import android.net.Uri;
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
import android.widget.VideoView;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.fifth.mygroup.trudgedailydemo.R;
import com.fifth.mygroup.trudgedailydemo.adapteres.VideoListAdapter;
import com.fifth.mygroup.trudgedailydemo.beans.VideoData;
import com.fifth.mygroup.trudgedailydemo.beans.Videos;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * Created by lenovo on 2016/5/18.
 */
public class VideoFragment extends Fragment{
    private String url;
    private HttpUtils httpUtils;
    private Videos videos;
    private Context context;
    private ListView listView;
    private View footView;
    private boolean loadOver;
    private long timestamp;
    private Gson gson;
    private VideoListAdapter videoListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.video_fragment,null);
        context=getContext();
        httpUtils = new HttpUtils();
        listView= (ListView) view.findViewById(R.id.video_fragment);
        if(MainActivity.isNetWorkConnected) {
            if (getArguments() != null) {
                url = getArguments().getString("video");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpUtils.send(HttpMethod.GET, url,
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        showData(responseInfo.result);
                                        MainActivity.editor.putString("videoData", responseInfo.result);
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
            String videoData=MainActivity.sharedPreferences.getString("videoData",null);
            if(videoData!=null){
                showData(videoData);
            }
        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                VideoData videoData=videos.getData().get(position);
//                switch(view.getId()){
//                    case R.id.videoPlayFlag:
//                        parent.getChildAt(1).setVisibility(View.INVISIBLE);
//                        parent.getChildAt(2).setVisibility(View.INVISIBLE);
//                        parent.getChildAt(3).setVisibility(View.INVISIBLE);
////                        ((VideoView)view).setVideoURI(Uri.parse(videoData.getFile_url()));
//                        Toast.makeText(context, videoData.getFile_url(),Toast.LENGTH_SHORT).show();
////        viewHolder.videoView.start();
////        viewHolder.videoView.requestFocus();
//                        break;
//                    case R.id.videoLikeImg:
//                        break;
//                    case R.id.videoJudgeImg:
//                        break;
//                }
//                        Toast.makeText(context, videoData.getFile_url(),Toast.LENGTH_SHORT).show();
//            }
//        });


        return view;
    }

    private void showData(String result) {
        loadOver=true;
        gson=new Gson();
        videos=gson.fromJson(result,Videos.class);
        timestamp=videos.getTimestamp();
        videoListAdapter=new VideoListAdapter(videos.getData(),getContext());
        listView.setAdapter(videoListAdapter);
        footView=View.inflate(context,R.layout.listfootlayout,null);
        listView.addFooterView(footView);
        setListListener();
    }

    private void setListListener() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean lookOver;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && lookOver && loadOver) {
                    lookOver = false;
                    loadOver = false;
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ?timestamp=1463382000&
                    httpUtils.send(HttpMethod.GET, url + "?timestamp=" + timestamp + "&",
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
        loadOver=true;
        Videos videos_=gson.fromJson(result,Videos.class);
        timestamp=videos.getTimestamp();
        videoListAdapter.addVideos(videos_);
    }
}
