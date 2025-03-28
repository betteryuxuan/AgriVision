package com.example.communityfragment.presenter;

import android.net.Uri;
import android.util.Log;

import com.example.communityfragment.contract.IPublishContract;
import com.example.communityfragment.model.PublishModel;
import com.example.communityfragment.utils.FileUtils;
import com.example.communityfragment.view.PublishActivity;

import java.util.ArrayList;
import java.util.List;

public class PublishPresenter implements IPublishContract.Presenter {
    public static final String TAG = "PublishFunctionTAG";

    private PublishModel mModel;
    private PublishActivity mView;

    public PublishPresenter(PublishActivity view) {
        this.mView = view;
        mModel = new PublishModel(this, view);
    }

    @Override
    public void publish(String content, List<String> imagePath, int communityId) {
        Log.d(TAG, "选中的图片 完整路径: " + imagePath);
        List<String> realPathImages = new ArrayList<>();
        for (String path : imagePath) {
            String realPathFromUri = FileUtils.getRealPathFromUri(mView, Uri.parse(path));
            realPathImages.add(realPathFromUri);
        }
        mModel.publish(content, realPathImages, communityId, new IPublishContract.PublishCallback() {
            @Override
            public void onSuccess() {
                mView.publishSuccess();
            }

            @Override
            public void onFailure() {
                mView.publishFailure();
            }
        });
    }


}
