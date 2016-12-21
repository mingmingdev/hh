package com.fifth.mygroup.trudgedailydemo.beans;

import java.util.List;

/**
 * Created by lenovo on 2016/5/20.
 */
public class UserRecommender {
    private List<TopStory> data;
    private long timestamp;

    public UserRecommender(long timestamp, List<TopStory> data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public UserRecommender() {
    }

    public List<TopStory> getData() {
        return data;
    }

    public void setData(List<TopStory> data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
