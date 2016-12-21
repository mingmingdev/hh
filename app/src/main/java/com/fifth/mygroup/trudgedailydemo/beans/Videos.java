package com.fifth.mygroup.trudgedailydemo.beans;

import java.util.List;

/**
 * Created by lenovo on 2016/5/20.
 */
public class Videos {
    private List<VideoData> data;
    private long timestamp;

    public Videos() {
    }

    public Videos(List<VideoData> data, long timestamp) {
        this.data = data;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<VideoData> getData() {
        return data;
    }

    public void setData(List<VideoData> data) {
        this.data = data;
    }
}
