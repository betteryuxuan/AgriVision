package com.example.communityfragment.model;

import android.content.Context;
import android.util.Log;

import com.example.communityfragment.bean.Post;
import com.example.communityfragment.contract.IPublishContract;
import com.example.communityfragment.presenter.PublishPresenter;
import com.example.communityfragment.utils.URLUtils;
import com.example.module.libBase.TokenManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishModel implements IPublishContract.Model {
    public static final String TAG = "PublishFunctionTAG";

    private PublishPresenter presenter;
    private Context context;
    private OkHttpClient client = new OkHttpClient();

    public PublishModel(PublishPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public void publish(String content, List<String> imagePaths, int communityId, IPublishContract.PublishCallback callback) {
        Log.d(TAG, "发布帖子: " + content + " " + imagePaths + " " + communityId);
        String token = TokenManager.getToken(context);

        uploadImages(imagePaths, token, new UploadCallback() {
            @Override
            public void onComplete(List<String> uploadedImageUrls) {
                JSONObject json = new JSONObject();
                try {
                    json.put("content", content);
                    json.put("image", new JSONArray(uploadedImageUrls).toString());
                    json.put("community_id", communityId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                Log.d(TAG, "发布帖子请求体: " + json.toString());

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
                Request request = new Request.Builder()
                        .url(URLUtils.PUBLISH_POST_URL)
                        .post(requestBody)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "发帖失败: " + e.getMessage());
//                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(TAG, "发帖: " + responseBody);
                        if (response.isSuccessful()) {
//                            JSONObject object = new JSONObject(responseBody);
//                            if (object.getInt("code") == 1) {
//                                Post post = new Post();
//                                try {
//                                    JSONArray data = object.getJSONArray("data");
//
//                                } catch (JSONException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
                            callback.onSuccess();
                        } else {
//                            callback.onError(new Exception("发帖失败，响应码：" + response.code()));
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
//                callback.onError(e);
            }
        });
    }

    private void uploadImages(List<String> imagePaths, String token, UploadCallback callback) {
        if (imagePaths == null || imagePaths.isEmpty()) {
            // 如果没有图片，则直接返回空列表
            callback.onComplete(new ArrayList<>());
            return;
        }

        List<String> uploadedImageUrls = new ArrayList<>();
        AtomicInteger uploadCount = new AtomicInteger(0);

        for (String path : imagePaths) {
            Log.d(TAG, "上传图片路径: " + path);
            File imageFile = new File(path);
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            requestBodyBuilder.addFormDataPart("file", imageFile.getName(), fileBody);

            Request request = new Request.Builder()
                    .url(URLUtils.UPLOAD_URL)
                    .addHeader("Authorization", "Bearer " + token)
                    .post(requestBodyBuilder.build())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "上传图片失败: " + e.getMessage());
                    callback.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        Log.d(TAG, "图片上传返回: " + responseBody);
                        try {
                            JSONObject json = new JSONObject(responseBody);
                            String imgUrl = json.getString("data");
                            synchronized (uploadedImageUrls) {
                                uploadedImageUrls.add(imgUrl);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError(e);
                            return;
                        }
                    } else {
                        callback.onError(new Exception("图片上传失败，响应码：" + response.code()));
                        return;
                    }

                    if (uploadCount.incrementAndGet() == imagePaths.size()) {
                        callback.onComplete(uploadedImageUrls);
                    }
                }
            });
        }
    }

    interface UploadCallback {
        void onComplete(List<String> uploadedImageUrls);

        void onError(Exception e);
    }
}
