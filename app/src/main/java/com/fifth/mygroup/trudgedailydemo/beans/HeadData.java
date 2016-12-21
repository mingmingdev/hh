package com.fifth.mygroup.trudgedailydemo.beans;

import java.util.List;

/**
 * Created by lenovo on 2016/5/19.
 */
public class HeadData {
    private List<TopStory> top_stories;
    private List<TopStory> data;
    private long timestamp;

    public HeadData(List<TopStory> top_stories, List<TopStory> data, long timestamp) {
        this.top_stories = top_stories;
        this.data = data;
        this.timestamp = timestamp;
    }

    public HeadData() {
    }

    public List<TopStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStory> top_stories) {
        this.top_stories = top_stories;
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
