package com.example.communityfragment.utils;

public class URLUtils {
    private static final String BASE_URL = "http://101.200.122.3:8080";
    // 查询帖子列表
    public static final String GET_POSTS_URL = BASE_URL + "/community-post/posts";
    // 查询指定社区帖子列表
    public static final String GET_SPECIFIC_POSTS_URL = BASE_URL + "/community-post/community";
    // 发布帖子
    public static final String PUBLISH_POST_URL = BASE_URL + "/community-post/post";
    // 删除帖子
    public static final String DELETE_POST_URL = BASE_URL + "/community-post/post";
    // 帖子投票
    public static final String VOTE_POST_URL = BASE_URL + "/community-post/post/vote";
    // 查询用户帖子
    public static final String GET_USERPOSTS_URL = BASE_URL + "/community-post/user/posts";

    // 上传图片
    public static final String UPLOAD_URL = BASE_URL + "/community-post/upload";

    // 发布评论
    public static final String PUBLISH_COMMENT_URL = BASE_URL + "/community-post/comment";
    // 查询一级评论
    public static final String GET_FIRST_LEVEL_COMMENTS_URL = BASE_URL + "/community-post/first-level-comment";

}
