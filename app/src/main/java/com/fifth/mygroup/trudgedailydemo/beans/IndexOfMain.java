package com.fifth.mygroup.trudgedailydemo.beans;

import java.util.List;

/**
 * Created by lenovo on 2016/5/19.
 */
//{"data":[
//        {"name":"首页","url":"http://dailyapi.ibaozou.com/api/v31/documents/latest"},
//        {"name":"用户投稿","url":"http://dailyapi.ibaozou.com/api/v31/documents/contributes/latest"},
//        {"name":"视频","url":"http://dailyapi.ibaozou.com/api/v31/documents/videos/latest"}
//          ],
// "version":5}
public class IndexOfMain {
    private List<MainURL> data;
    private int version;

    public IndexOfMain(List<MainURL> data, int version) {
        this.data = data;
        this.version = version;
    }

    public IndexOfMain() {
    }

    public List<MainURL> getData() {
        return data;
    }

    public void setData(List<MainURL> data) {
        this.data = data;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
