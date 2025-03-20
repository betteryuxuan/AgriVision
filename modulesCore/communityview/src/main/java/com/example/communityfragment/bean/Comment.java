package com.example.communityfragment.bean;

public class Comment {
    private String id;
    private String userid;
    private String content;
    private String userName;
    private String userAavatar;
    private String time;

    public Comment() {
    }

    public Comment(String content, String time, String userAavatar, String userid, String userName) {
        this.content = content;
        this.time = time;
        this.userAavatar = userAavatar;
        this.userid = userid;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserAavatar() {
        return userAavatar;
    }

    public void setUserAavatar(String userAavatar) {
        this.userAavatar = userAavatar;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
