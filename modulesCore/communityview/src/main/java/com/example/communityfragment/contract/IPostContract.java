package com.example.communityfragment.contract;

import com.example.communityfragment.bean.Comment;

import java.util.List;

public interface IPostContract {
    interface View {
        void onCommentsSuccess(List<Comment> comments);

        void onCommentsFailure();

        void onPublishCommentSuccess(Comment comment);

        void onPublishCommentFailure();

        void onDeleteSuccess(int postId);
    }

    interface Presenter {
        void comment(int postId, String comment, int parentId, int rootId);

        void onCommentsSuccess(List<Comment> comments);

        void onCommentsFailure();

        void onPublishCommentSuccess(Comment comment);

        void onPublishCommentFailure();

        void deletePost(int id);

        void deletePostSuccess(int postId);
    }

    interface Model {
        void comment(int postId, String comment, int parentId, int rootId);

        void getComments(int postId);

        void deletePost(int id);
    }


}
