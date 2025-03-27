package com.example.communityfragment.presenter;

import android.content.Context;
import android.util.Log;

import com.example.communityfragment.bean.Comment;
import com.example.communityfragment.contract.IPostContract;
import com.example.communityfragment.model.PostModel;
import com.example.communityfragment.view.PostActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void comment(int postId, String comment, int parentId, int rootId) {
        Log.d("commentcomment", "comment: " + comment + " parentId: " + parentId + " rootId: " + rootId);
        mModel.comment(postId, comment, parentId, rootId);
    }

    public void getComments(int postId) {
        mModel.getComments(postId);
    }

    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        mView.onCommentsSuccess(flattenComments(comments));
    }

    @Override
    public void onCommentsFailure() {
        mView.onCommentsFailure();
    }

    @Override
    public void onPublishCommentSuccess(Comment comment) {
        mView.onPublishCommentSuccess(comment);
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

    public List<Comment> flattenComments(List<Comment> comments) {
        List<Comment> finalList = new ArrayList<>();
        List<Comment> levelOneComments = new ArrayList<>();
        Map<Integer, List<Comment>> childrenMap = new HashMap<>();

        // 实现一级评论的集合以及子评论的映射
        for (Comment comment : comments) {
            if (comment.getRootId() == 0) {
                levelOneComments.add(comment);
            } else {
                childrenMap.computeIfAbsent(comment.getRootId(), k -> new ArrayList<>()).add(comment);
            }
        }

        // 对子评论进行时间排序
        for (Comment comment : levelOneComments) {
            if (childrenMap.containsKey(comment.getId())) {
                childrenMap.put(comment.getId(), sortCommentsByTime(childrenMap.get(comment.getId())));
            }
        }

        // 递归方式将所有层级的评论添加到最终列表
        for (Comment comment : levelOneComments) {
            addCommentWithChildren(finalList, comment, childrenMap);
        }

        return finalList;
    }

    // 对子评论进行时间排序
    private List<Comment> sortCommentsByTime(List<Comment> comments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                LocalDateTime time1 = LocalDateTime.parse(o1.getTime(), formatter);
                LocalDateTime time2 = LocalDateTime.parse(o2.getTime(), formatter);
                return time1.compareTo(time2);
            }
        });
        return comments;
    }


    private static void addCommentWithChildren(List<Comment> finalList, Comment comment, Map<Integer, List<Comment>> childrenMap) {
        finalList.add(comment); // 先添加当前评论
        if (childrenMap.containsKey(comment.getId())) {
            for (Comment child : childrenMap.get(comment.getId())) {
                addCommentWithChildren(finalList, child, childrenMap); // 递归添加子评论
            }
        }
    }


}
