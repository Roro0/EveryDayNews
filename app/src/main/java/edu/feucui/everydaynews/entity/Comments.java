package edu.feucui.everydaynews.entity;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Comments {

    int cid;//评论id
    String uid;//用户名
    String portrait;//用户头像 url
    String stamp;//发布时间yyyyMMdd
    String content;//评论内容

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
