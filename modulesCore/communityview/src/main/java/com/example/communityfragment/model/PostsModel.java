package com.example.communityfragment.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.communityfragment.bean.Post;
import com.example.communityfragment.contract.IPostsContract;
import com.example.communityfragment.presenter.PostsPresenter;
import com.example.communityfragment.utils.URLUtils;
import com.example.module.libBase.SPUtils;
import com.example.module.libBase.TokenManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostsModel implements IPostsContract.Model {
    public static final String TAG = "CommunityModelTAG";
    private PostsPresenter mPresenter;
    private Context mContext;

    private OkHttpClient client = new OkHttpClient();

    public PostsModel(PostsPresenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    @Override
    public void getData(int communityId, int page, int pageSize) {
        String token = TokenManager.getToken(mContext);
        String URL;
        if (communityId == 5) {
            // 热榜
            URL = URLUtils.GET_POSTS_URL + "?page=" + page + "&size=" + pageSize ;
        } else if (communityId == 4) {
            // 全部
            URL = URLUtils.GET_USERPOSTS_URL + "?page=" + page + "&size=" + pageSize + "&order=" + "time";
        } else {
            URL = URLUtils.GET_SPECIFIC_POSTS_URL + "/" + communityId + "/posts" + "?page=" + page + "&size=" + pageSize;
        }

        Request request = new Request.Builder()
                .url(URL)
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mPresenter.onDataReceivedFailure();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                Log.d(TAG, "onResponse: " + responseBody);
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(responseBody);
                        if (object.getInt("code") == 1) {
                            JSONArray data = object.getJSONObject("data").getJSONArray("posts");
                            List<Post> posts = new ArrayList<>();
                            for (int i = 0; i < data.length(); i++) {
                                posts.add(fromJson(data.getJSONObject(i)));
                            }
                            mPresenter.onDataReceived(posts);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    mPresenter.onDataReceivedFailure();
                }
            }
        });
    }

    private Post fromJson(JSONObject json) throws JSONException {
        Post post = new Post();
        post.setId(json.getInt("id"));
        post.setContent(json.getString("content"));
        post.setImageUrls(json.getString("image"));

        JSONObject author = json.getJSONObject("author");
        post.setUserid(String.valueOf(author.getInt("id")));
        post.setUserName(author.getString("username"));
        post.setUserAvatar(author.getString("avatar"));

        post.setLikeConunt(String.valueOf(json.getInt("like_count")));
        post.setLiked(json.getBoolean("liked"));
        post.setCommentCount(String.valueOf(json.getInt("comment_count")));

        post.setCreatedTime(json.getString("created_at"));

        JSONObject community = json.getJSONObject("community");
        post.setCommunityId(String.valueOf(community.getInt("id")));

        return post;
    }

    @Override
    public void deletePost(int postId) {
        String URL = URLUtils.DELETE_POST_URL + "/" + postId;
        Request request = new Request.Builder()
                .url(URL)
                .delete()
                .addHeader("Authorization", "Bearer " + TokenManager.getToken(mContext))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, response.toString());
                if (response.isSuccessful()) {
                    mPresenter.deletePostSuccess(postId);
                }
            }
        });
    }

    @Override
    public void votePost(int postId, Boolean isLiked) {
        JSONObject json = new JSONObject();
        int directionInt = isLiked ? 0 : 1;
        try {
            json.put("post_id", postId);
            json.put("direction", directionInt);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json.toString());
        Request request = new Request.Builder()
                .url(URLUtils.VOTE_POST_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + TokenManager.getToken(mContext))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    Log.d(TAG, "点赞成功" + responseBody);
                }
            }
        });
    }

    @Override
    public String getUserName() {
        return SPUtils.getString(mContext, "username", null);
    }


}
