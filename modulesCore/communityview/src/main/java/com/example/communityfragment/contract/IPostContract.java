package com.example.communityfragment.contract;

import com.example.communityfragment.bean.Comment;

import java.util.List;

public interface IPostContract {
    interface View {
        void onCommentsSuccess(List<Comment> comments);

        void onCommentsFailure();

        void onPublishCommentSuccess();

        void onPublishCommentFailure();

        void onDeleteSuccess(int postId);
    }

    interface Presenter {
        void comment(int postId, String comment, String parentId, String rootId);

        void onCommentsSuccess(List<Comment> comments);

        void onCommentsFailure();

        void onPublishCommentSuccess();

        void onPublishCommentFailure();

        void deletePost(int id);

        void deletePostSuccess(int postId);
    }

    interface Model {
        void comment(int postId, String comment, String parentId, String rootId);

        void getComments(int postId);

        void deletePost(int id);
    }


}
