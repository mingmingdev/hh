package com.fifth.mygroup.trudgedailydemo.adapteres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.fifth.mygroup.trudgedailydemo.R;
import com.fifth.mygroup.trudgedailydemo.beans.TopStory;

import java.util.List;

/**
 * Created by lenovo on 2016/5/20.
 */
public class HeadListAdapter extends BaseAdapter{
    private List<TopStory> datas;
    private Context context;

    public HeadListAdapter(List<TopStory> datas,Context context){
        this.datas=datas;
        this.context=context;
    }
    public void addDatas(List<TopStory> data){
        datas.addAll(data);
        notifyDataSetChanged();
    }
    public void upData(List<TopStory> data){
        datas=data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.head_list_item,parent,false);
            viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_author_name= (TextView) convertView.findViewById(R.id.tv_author_name);
            ///viewHolder.tv_document_id= (TextView) convertView.findViewById(R.id.tv_document_id);
            viewHolder.iv_yh= (ImageView) convertView.findViewById(R.id.iv_yh);
            viewHolder.headReadCount= (TextView) convertView.findViewById(R.id.headReadCount);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        TopStory topStory=datas.get(position);
        viewHolder.tv_title.setText(topStory.getTitle());
        viewHolder.tv_author_name.setText(topStory.getAuthor_name());
        //viewHolder.tv_document_id.setText(newsItem.getDocument_id());
        viewHolder.headReadCount.setText(topStory.getHit_count_string());
        viewHolder.iv_yh.setImageResource(R.mipmap.ic_launcher);
        MainActivity.bitmapUtils.display(viewHolder.iv_yh,topStory.getThumbnail());

        return convertView;



    }
        class ViewHolder{
            TextView tv_title;
            TextView tv_author_name;
            TextView tv_document_id;
            ImageView iv_yh;
            TextView headReadCount;
        }
}
