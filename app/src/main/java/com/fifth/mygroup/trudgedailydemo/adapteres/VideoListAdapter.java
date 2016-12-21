package com.fifth.mygroup.trudgedailydemo.adapteres;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.fifth.mygroup.trudgedailydemo.R;
import com.fifth.mygroup.trudgedailydemo.beans.VideoData;
import com.fifth.mygroup.trudgedailydemo.beans.Videos;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by lenovo on 2016/5/20.
 */
public class VideoListAdapter extends BaseAdapter{
    private List<VideoData> videoDatas;
    private Context context;
//    private ViewHolder viewHolder;
    private Uri videoUri;
//    private VideoData videoData;



    public VideoListAdapter(List<VideoData> videoDatas,Context context){
        this.videoDatas=videoDatas;
        this.context=context;
    }
    @Override
    public int getCount() {
        return videoDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return videoDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        VideoData videoData=videoDatas.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.video_list,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.playImage= (ImageView) convertView.findViewById(R.id.videoImg);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.videoPlayLong);
            viewHolder.videoView= (VideoView) convertView.findViewById(R.id.videoList);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.videoPlayFlag);
            viewHolder.textViewDescription= (TextView) convertView.findViewById(R.id.videoText);
            viewHolder.like= (TextView) convertView.findViewById(R.id.videoLikeCount);
            viewHolder.judge= (TextView) convertView.findViewById(R.id.videoJudgeCount);
            viewHolder.playTime= (TextView) convertView.findViewById(R.id.videoPlayTime);
            viewHolder.frameLayout= (FrameLayout) convertView.findViewById(R.id.videoBackGround);
            viewHolder.imageShare= (ImageView) convertView.findViewById(R.id.videoShare);
            viewHolder.videoView.setMediaController(new MediaController(context));
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.playImage.setVisibility(View.VISIBLE);
        viewHolder.imageView.setVisibility(View.VISIBLE);
        viewHolder.textView.setVisibility(View.VISIBLE);
        viewHolder.like.setOnClickListener(new MyOnItemClickListener(position, viewHolder, videoData) {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "赞+1" + position, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.imageShare.setOnClickListener(new MyOnItemClickListener(position,viewHolder,videoData) {
            @Override
            public void onClick(View v) {
                showShare(videoData.getTitle(),videoData.getImage(),videoData.getShare_url());
            }
        });
        viewHolder.imageView.setOnClickListener(new MyOnItemClickListener(position,viewHolder,videoData) {
            @Override
            public void onClick(View v) {
                viewHolder.playImage.setVisibility(View.INVISIBLE);
                viewHolder.imageView.setVisibility(View.INVISIBLE);
                viewHolder.textView.setVisibility(View.INVISIBLE);
                viewHolder.videoView.setVideoURI(Uri.parse(videoData.getFile_url()));
                viewHolder.videoView.start();
//                viewHolder.videoView.requestFocus();
            }
        });
//        viewHolder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//                viewHolder.playImage.setVisibility(View.VISIBLE);
//                viewHolder.imageView.setVisibility(View.VISIBLE);
//                viewHolder.textView.setVisibility(View.VISIBLE);
//            }
//        });

        MainActivity.bitmapUtils.display(viewHolder.playImage, videoData.getImage());

        int playTime=videoData.getPlay_time();

        viewHolder.textView.setText(String.format("  "+"%02d",playTime/60)+":"+String.format(" "+"%02d",playTime%60)+"  ");

        viewHolder.textViewDescription.setText(videoData.getTitle());

        viewHolder.like.setText(videoData.getVote_count()+"");

        viewHolder.judge.setText(videoData.getComment_count()+"");

        viewHolder.playTime.setText(videoData.getPlay_count()+"");

        videoUri=Uri.parse(videoData.getFile_url());
//        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
////            boolean isPlaying=false;
//            @Override
//            public void onClick(View v) {
//                Message msg=new Message();
//                msg.arg1=position;
//                handler.sendMessage(msg);
//
//                }
////            }
//        });

        return convertView;
    }

    public void addVideos(Videos videos_) {
        videoDatas.addAll(videos_.getData());
        notifyDataSetChanged();
    }
    public void updateVideos(Videos videosUpdate){
        videoDatas.clear();
        videoDatas=videosUpdate.getData();
        notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView imageView;
        VideoView videoView;
        ImageView playImage;
        ImageView imageShare;
        TextView textView;
        TextView textViewDescription;
        TextView like;
        TextView judge;
        TextView playTime;
        FrameLayout frameLayout;
//        boolean isPlaying;
    }
    private void playVideo(){
        //                viewHolder.isPlaying=!viewHolder.isPlaying;

//                if(viewHolder.isPlaying) {
//                    viewHolder.videoView.pause();
//                    viewHolder.isPlaying=false;
//                    viewHolder.imageView.setVisibility(View.VISIBLE);
//                    viewHolder.playImage.setVisibility(View.VISIBLE);
//                    viewHolder.textView.setVisibility(View.VISIBLE);
//                }else {
//                    viewHolder.isPlaying=true;
//        viewHolder.playImage.setVisibility(View.INVISIBLE);
//        viewHolder.imageView.setVisibility(View.INVISIBLE);
//        viewHolder.textView.setVisibility(View.INVISIBLE);
//        viewHolder.videoView.setVideoURI(videoUri);
//        viewHolder.videoView.start();
//        viewHolder.videoView.requestFocus();
    }
    abstract class MyOnItemClickListener implements View.OnClickListener {
        int position;
        ViewHolder viewHolder;
        VideoData videoData;
        MyOnItemClickListener(int position,ViewHolder viewHolder,VideoData videoData){
            this.position=position;
            this.viewHolder=viewHolder;
            this.videoData=videoData;
        }
    }
    private void showShare(String title,String img,String url) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(img);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("暴走日报");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://dailyapi.ibaozou.com");

// 启动分享GUI
        oks.show(context);
    }

}
