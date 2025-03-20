package com.example.communityfragment.contract;

import com.example.communityfragment.bean.Post;

import java.util.List;

public interface IPostsContract {
    public interface View {
        void onDataReceived(List<Post> postList);

        void deletePostSuccess(int postId);

        void onDataReceivedFailure();
    }

    public interface Presenter {
        void getData(int communityId, int page, int pageSize);

        void onDataReceived(List<Post> postList);

        void deletePostSuccess(int postId);

        void votePost(int postId, Boolean direction);

        String getUserName();

        void onDataReceivedFailure();
    }

    public interface Model {
        void getData(int communityId, int page, int pageSize);

        void deletePost(int postId);

        void votePost(int postId, Boolean isliked);

        String getUserName();
    }

}
