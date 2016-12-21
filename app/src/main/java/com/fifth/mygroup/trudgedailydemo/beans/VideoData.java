package com.fifth.mygroup.trudgedailydemo.beans;

/**
 * Created by lenovo on 2016/5/20.
 */
public class VideoData {
    /** {
     "document_id": 26163,
     "display_type": 3,
     "play_time": 26,
     "title": "无业游民狗VS有工作的狗。。导盲犬一脸的高冷啊",
     "image": "http://7o51ui.com2.z0.glb.qiniucdn.com/ribaovideo/2016/05/1463640990749-3rb3274adh5zynkcctdegv-d5aa0cab08798c49bbf7df9fc27d752e",
     "play_count": 647,
     "comment_count": 6,
     "vote_count": 22,
     "file_url": "http://gslb.miaopai.com/stream/9kn40ybxDNQshRA09z5bzA__.mp4",
     "share_url": "http://baozouribao.com/documents/26163",
     "publish_time": 1463734800000,
     "play_count_string": "647"
     },
     */
    private String title;
    private String image;
    private String file_url;
    private String share_url;
    private String play_count_string;
    private int document_id;
    private int display_type;
    private int play_time;
    private int play_count;
    private int comment_count;
    private int vote_count;
    private long publish_time;

    public VideoData() {
    }

    public VideoData(String title, String image, String file_url,
                     String share_url, String play_count_string,
                     int document_id, int display_type, int play_time,
                     int play_count, int comment_count, int vote_count,
                     long publish_time) {
        this.title = title;
        this.image = image;
        this.file_url = file_url;
        this.share_url = share_url;
        this.play_count_string = play_count_string;
        this.document_id = document_id;
        this.display_type = display_type;
        this.play_time = play_time;
        this.play_count = play_count;
        this.comment_count = comment_count;
        this.vote_count = vote_count;
        this.publish_time = publish_time;
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

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getPlay_count_string() {
        return play_count_string;
    }

    public void setPlay_count_string(String play_count_string) {
        this.play_count_string = play_count_string;
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

    public int getPlay_time() {
        return play_time;
    }

    public void setPlay_time(int play_time) {
        this.play_time = play_time;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
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

    public long getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(long publish_time) {
        this.publish_time = publish_time;
    }
}
