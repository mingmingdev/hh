package com.fifth.mygroup.trudgedailydemo.adapteres;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.fifth.mygroup.trudgedailydemo.R;
import com.fifth.mygroup.trudgedailydemo.activities.NewsItemActivity;
import com.fifth.mygroup.trudgedailydemo.beans.TopStory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/5/19.
 */
public class HeadPagerAdapter extends PagerAdapter{
    private List<ImageView> list=new ArrayList<ImageView>();
    private List<TopStory> topStories;
    private TextView textView;
    private Context context;


    public HeadPagerAdapter( final List<TopStory> topStories, final Context context,TextView textView){
        this.textView=textView;
        this.topStories=topStories;
        this.context=context;
        for(int i=0;i<topStories.size();i++){//每一张图片
            ImageView imageView=new ImageView(context);
            imageView.setOnClickListener(new MyPagerOnClickListener(context, i) {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), NewsItemActivity.class);
                    intent.putExtra("oneUrl", topStories.get(getNum()).getUrl());
                    context.startActivity(intent);
                }
            });
            String url=topStories.get(i).getImage();
            imageView.setImageResource(R.mipmap.btn_home_menu_nor);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            MainActivity.bitmapCacheHelper.getBitmap(url, imageView);
            list.add(imageView);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        container.addView(list.get(position));

        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView(list.get(position));
    }
    class MyPagerOnClickListener implements View.OnClickListener {
        private Context context;
        private int num;
        public MyPagerOnClickListener(Context context,int num){
            this.context=context;
            this.num=num;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public void onClick(View v) {

        }
    }

}
