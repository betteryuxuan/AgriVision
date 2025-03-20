package com.example.communityfragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.communityfragment.R;
import com.example.communityfragment.bean.Comment;
import com.example.communityfragment.utils.TimeUtils;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context mContext;
    private List<Comment> comments;

    public CommentAdapter(Context mContext, List<Comment> comments) {
        this.mContext = mContext;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.content.setText(comment.getContent());
        Glide.with(mContext)
                .load(comment.getUserAavatar())
                .placeholder(R.drawable.ic_default)
                .into(holder.avatar);
        holder.UserName.setText(comment.getUserName());
        holder.time.setText(TimeUtils.getRelativeTime(comment.getTime()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void updateData(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private ImageView avatar;
        private TextView UserName;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tv_comment_content);
            avatar = itemView.findViewById(R.id.img_comment_useravatar);
            UserName = itemView.findViewById(R.id.tv_comment_username);
            time = itemView.findViewById(R.id.tv_comment_time);
        }
    }
}
