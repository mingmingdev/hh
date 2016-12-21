package com.fifth.mygroup.trudgedailydemo.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/5/19.
 */
public class TopStory implements Parcelable{
/**
 *  {
 "document_id": 26081,
 "display_type": 1,
 "title": "大张伟这个熊孩子居然和经纪人领证了，新娘竟是她！",
 "image": "http://7o51ui.com2.z0.glb.qiniucdn.com/ribaopic/2016/05/1463554866291-a2gl7vtw2v9w54ec6gb7f-9396fc3feafb7d29a822a8219ce89146",
 "thumbnail": "http://7o51ui.com2.z0.glb.qiniucdn.com/ribaopic/2016/05/1463554396591-vofmkmakxvrade8bssr5p-b66e87f125eb5aa0a876ffadb6a2f32d",
 "author_avatar": "http://ww1.sinaimg.cn/large/0060idsIjw1ewj4ffcrynj30dw0dw405.jpg",
 "author_name": "零小蝎",
 "author_id": 272142,
 "section_id": 125,
 "share_url": "http://baozouribao.com/documents/26081",
 "url": "http://dailyapi.ibaozou.com/api/v31/documents/26081",
 "hit_count": 41418,
 "section_name": "贵圈真乱",
 "section_image": "http://ww3.sinaimg.cn/small/0060u6Bxjw1ewkbig4xauj30f00a0wf5.jpg",
 "section_color": "7CD095",
 "hit_count_string": "4.14万",
 "timestamp": 1463569214,
 "comment_count": 279,
 "vote_count": 271
 },
 "publish_time": 1463623200000,
 "published_at": "2016-05-19 10:00",
 "recommenders": [
 {
 "id": 683404,
 "name": "重命名",
 "avatar": "http://ww2.sinaimg.cn/large/005OJcC9jw1f0nbckttywj305n05kwer.jpg"
 }
 ],
 "thumbnail": "http://7o51ui.com2.z0.glb.qiniucdn.com/ribao_recommendation_image/2016/05/1463622703684-onew174lqkmexvzzafob98-f6bbd1dfd917986c36cec26e05075b16",
 "author_id": 683404,
 "author_name": "重命名",
 "author_avatar": "http://ww2.sinaimg.cn/large/005OJcC9jw1f0nbckttywj305n05kwer.jpg"
 },
 */
    private int document_id=0;
    private int display_type=0;
    private String title="";
    private String image="";
    private String thumbnail="";
    private String author_avatar="";
    private int author_id=0;
    private int section_id=0;
    private String share_url="";
    private String url="";
    private int hit_count=0;
    private String section_name="";
    private String section_image="";
    private String section_color="";
    private String hit_count_string="";
    private long timestamp=0;
    private int comment_count=0;
    private int vote_count=0;
    private String author_name="";
    private List<Recommender> recommenders;
    private long publish_time=0;
    private String publish_at="";
    private int contribute=0;
    private String source_name="";

    protected TopStory(Parcel parcel){
        document_id=parcel.readInt();
        display_type=parcel.readInt();
        title=parcel.readString();
        image=parcel.readString();
        thumbnail=parcel.readString();
        author_avatar=parcel.readString();
        author_id=parcel.readInt();
        section_id=parcel.readInt();
        share_url=parcel.readString();
        url=parcel.readString();
        hit_count=parcel.readInt();
        section_name=parcel.readString();
        section_image=parcel.readString();
        section_color=parcel.readString();
        hit_count_string=parcel.readString();
        timestamp=parcel.readLong();
        comment_count=parcel.readInt();
        vote_count=parcel.readInt();
        author_name=parcel.readString();
        recommenders=new ArrayList<Recommender>();
        publish_time=parcel.readLong();
        publish_at=parcel.readString();
        contribute=parcel.readInt();
        source_name=parcel.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(thumbnail);
        dest.writeString(author_avatar);
        dest.writeString(share_url);
        dest.writeString(url);
        dest.writeString(section_name);
        dest.writeString(section_image);
        dest.writeString(section_color);
        dest.writeString(hit_count_string);
        dest.writeString(author_name);
        dest.writeString(publish_at);
        dest.writeString(source_name);
        dest.writeInt(document_id);
        dest.writeInt(display_type);
        dest.writeInt(author_id);
        dest.writeInt(section_id);
        dest.writeInt(hit_count);
        dest.writeInt(comment_count);
        dest.writeInt(vote_count);
        dest.writeInt(contribute);
        dest.writeLong(timestamp);
        dest.writeLong(publish_time);
        dest.readArrayList(Recommender.class.getClassLoader());
    }
    public TopStory() {
    }


    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public int getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(int display_type) {
        this.display_type = display_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAuthor_avatar() {
        return author_avatar;
    }

    public void setAuthor_avatar(String author_avatar) {
        this.author_avatar = author_avatar;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHit_count() {
        return hit_count;
    }

    public void setHit_count(int hit_count) {
        this.hit_count = hit_count;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSection_image() {
        return section_image;
    }

    public void setSection_image(String section_image) {
        this.section_image = section_image;
    }

    public String getSection_color() {
        return section_color;
    }

    public void setSection_color(String section_color) {
        this.section_color = section_color;
    }

    public String getHit_count_string() {
        return hit_count_string;
    }

    public void setHit_count_string(String hit_count_string) {
        this.hit_count_string = hit_count_string;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public List<Recommender> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(List<Recommender> recommenders) {
        this.recommenders = recommenders;
    }

    public long getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(long publish_time) {
        this.publish_time = publish_time;
    }

    public String getPublish_at() {
        return publish_at;
    }

    public void setPublish_at(String publish_at) {
        this.publish_at = publish_at;
    }

    public int getContribute() {
        return contribute;
    }

    public void setContribute(int contribute) {
        this.contribute = contribute;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<TopStory> CREATOR=new Creator<TopStory>() {
        @Override
        public TopStory createFromParcel(Parcel source) {
            return new TopStory(source);
        }

        @Override
        public TopStory[] newArray(int size) {
            return new TopStory[0];
        }
    };

}
