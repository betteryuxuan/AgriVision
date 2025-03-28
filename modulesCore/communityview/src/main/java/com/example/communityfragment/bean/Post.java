package com.example.communityfragment.bean;

import java.io.Serializable;

public class Post implements Serializable {
    private int id;
    private String userid;
    private String userName;
    private String userAvatar;
    private String content;
    private String createdTime;
    private String imageUrls;
    private String likeConunt;
    private boolean isLiked;
    private String commentCount;
    private String communityId;

    public Post() {
    }

    public Post(String content, String likeConunt, String userAvatar, String userid) {
        this.content = content;
        this.likeConunt = likeConunt;
        this.userAvatar = userAvatar;
        this.userid = userid;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getLikeConunt() {
        return likeConunt;
    }

    public void setLikeConunt(String likeConunt) {
        this.likeConunt = likeConunt;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Post{" +
                "commentCount='" + commentCount + '\'' +
                ", communityId='" + communityId + '\'' +
                ", isLiked=" + isLiked +
                ", content='" + content + '\'' +
//                ", createdTime='" + createdTime + '\'' +
                ", id=" + id +
//                ", imageUrls='" + imageUrls + '\'' +
                ", likeConunt='" + likeConunt + '\'' +
//                ", userAvatar='" + userAvatar + '\'' +
                ", userid='" + userid + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
