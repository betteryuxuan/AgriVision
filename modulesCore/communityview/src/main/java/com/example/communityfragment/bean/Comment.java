package com.example.communityfragment.bean;

public class Comment {
    private String id;
    private String userid;
    private String content;
    private String userName;
    private String userAavatar;
    private String time;

    private String repliesCount;
    private String parentId;
    private String rootId;
    private String postId;
    private String likeCount;
    private boolean isLiked;

    public Comment() {
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(String repliesCount) {
        this.repliesCount = repliesCount;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", userName='" + userName + '\'' +
                ", userAavatar='" + userAavatar + '\'' +
                ", time='" + time + '\'' +
                ", repliesCount='" + repliesCount + '\'' +
                ", parentId='" + parentId + '\'' +
                ", rootId='" + rootId + '\'' +
                ", postId='" + postId + '\'' +
                ", likeCount='" + likeCount + '\'' +
                ", isLiked=" + isLiked +
                '}';
    }
}
