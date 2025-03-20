package com.example.communityfragment.presenter;

import android.content.Context;

import com.example.communityfragment.bean.Comment;
import com.example.communityfragment.contract.IPostContract;
import com.example.communityfragment.model.PostModel;
import com.example.communityfragment.view.PostActivity;

import java.util.List;

public class PostPresenter implements IPostContract.Presenter {
    public static final String TAG = "PostFunctionTAG";

    private Context mContext;
    private IPostContract.Model mModel;
    private IPostContract.View mView;

    public PostPresenter(PostActivity context) {
        mContext = context;
        mView = context;
        mModel = new PostModel(context, this);
    }

    @Override
    public void comment(int postId, String userId, String comment) {
        mModel.comment(postId, userId, comment);
    }

    public void getComments(int postId) {
        mModel.getComments(postId);
    }

    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        mView.onCommentsSuccess(comments);
    }

    @Override
    public void onCommentsFailure() {
        mView.onCommentsFailure();
    }

    @Override
    public void onPublishCommentSuccess() {
        mView.onPublishCommentSuccess();
    }

    @Override
    public void onPublishCommentFailure() {
        mView.onPublishCommentFailure();
    }

    @Override
    public void deletePost(int id) {
        mModel.deletePost(id);
    }

    @Override
    public void deletePostSuccess(int postId) {
        mView.onDeleteSuccess(postId);
    }

}
