package com.fifth.mygroup.trudgedailydemo.beans;

/**
 * Created by lenovo on 2016/5/19.
 */
public class Recommender {
    /**
     * {
     "id": 683404,
     "name": "重命名",
     "avatar": "http://ww2.sinaimg.cn/large/005OJcC9jw1f0nbckttywj305n05kwer.jpg"
     }
     */
    private int id;
    private String name;
    private String avatar;

    public Recommender() {
    }

    public Recommender(String avatar, int id, String name) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
