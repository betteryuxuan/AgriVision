package com.example.communityfragment.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.communityfragment.bean.Comment;
import com.example.communityfragment.contract.IPostContract;
import com.example.communityfragment.presenter.PostPresenter;

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

public class PostModel implements IPostContract.Model {
    public static final String TAG = "PostModelTAG";

    private PostPresenter mPresenter;
    private Context mContext;
    private OkHttpClient client = new OkHttpClient();

    public PostModel(Context mContext, PostPresenter mPresenter) {
        this.mContext = mContext;
        this.mPresenter = mPresenter;
    }

    @Override
    public void getComments(int postId) {
//        String GETCOMMENTS_URL = COMMENTS_URL + "?post_id=" + postId;
//
//        Request request = new Request.Builder()
//                .url(GETCOMMENTS_URL)
//                .get()
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseData = response.body().string();
//                Log.d(TAG, "获取评论: " + postId + " " + responseData);
//                if (response.isSuccessful()) {
//                    try {
//                        JSONObject json = new JSONObject(responseData);
//                        if (json.isNull("data")) {
//                            Log.d(TAG, "无评论");
//                            mPresenter.onCommentsSuccess(new ArrayList<>());
//                        } else {
//                            JSONArray jsonArray = json.getJSONArray("data");
//                            List<Comment> comments = new ArrayList<>();
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject item = jsonArray.getJSONObject(i);
//
//                                JSONObject commentObj = item.getJSONObject("comment");
//                                Comment comment = new Comment();
//                                comment.setId(String.valueOf(commentObj.getInt("id")));
//                                comment.setContent(commentObj.getString("content"));
//                                comment.setTime(commentObj.getString("created_at"));
//                                comment.setUserid(commentObj.getString("user_id"));
//
//                                if (item.has("user") && !item.isNull("user")) {
//                                    JSONObject userObj = item.getJSONObject("user");
//                                    comment.setUserName(userObj.getString("username"));
//                                    comment.setUserAavatar(userObj.getString("avatar_url"));
//                                }
//                                comments.add(comment);
//                                Log.d(TAG, "帖子 : " + comment.toString());
//                            }
//                            mPresenter.onCommentsSuccess(comments);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.e(TAG, "onResponse: " + response.code());
//                }
//            }
//        });
    }

    @Override
    public void comment(int postId, String userId, String comment) {
//        JSONObject json = new JSONObject();
//        try {
//            json.put("post_id", postId);
//            json.put("user_id", String.valueOf(userId));
//            json.put("content", comment);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
//
//        Request request = new Request.Builder()
//                .url(COMMENT_URL)
//                .post(requestBody)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.d(TAG, "onFailure: " + e.getMessage());
//                mPresenter.onPublishCommentFailure();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                String responseData = response.body().string();
//                Log.d(TAG, "发布评论: " + postId + " " + responseData);
//                if (response.isSuccessful()) {
//                    mPresenter.onPublishCommentSuccess();
//                } else {
//                    mPresenter.onPublishCommentFailure();
//                }
//            }
//        });
    }

    @Override
    public void deletePost(int postId) {
//        String MYDELETE_URL = DELETE_URL + "?id=" +postId;
//        Request request = new Request.Builder()
//                .url(MYDELETE_URL)
//                .delete()
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    mPresenter.deletePostSuccess(postId);
//                }
//            }
//        });
    }
}
