package com.example.communityfragment.presenter;

import android.content.Context;

import com.example.communityfragment.bean.Post;
import com.example.communityfragment.contract.IPostsContract;
import com.example.communityfragment.model.PostsModel;

import java.util.List;

public class PostsPresenter implements IPostsContract.Presenter {

    private IPostsContract.View mView;
    private IPostsContract.Model mModel;
    private Context mContext;

    public PostsPresenter(IPostsContract.View view, Context context) {
        mView = view;
        mContext = context;
        mModel = new PostsModel(this, context);
    }

    @Override
    public void getData(int communityId, int page, int pageSize) {
        mModel.getData(communityId, page, pageSize);
    }

    @Override
    public void onDataReceived(List<Post> postList) {
        mView.onDataReceived(postList);
    }

    @Override
    public void deletePostSuccess(int postId) {
        mView.deletePostSuccess(postId);
    }

    @Override
    public void votePost(int postId, Boolean direction) {
        mModel.votePost(postId, direction);
    }

    @Override
    public void onDataReceivedFailure() {
        mView.onDataReceivedFailure();
    }

    public void deletePost(int postId) {
        mModel.deletePost(postId);
    }


}
