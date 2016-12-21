package com.fifth.mygroup.trudgedailydemo.beans;

/**
 * Created by lenovo on 2016/5/19.
 */
public class MainURL {
    private String type;
    private String url;

    public MainURL(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public MainURL() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
