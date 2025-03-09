package com.example.module.homepageview.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.Recommend;

import java.util.List;

public class RecommendRecyclerViewAdapter extends RecyclerView.Adapter<RecommendRecyclerViewAdapter.RecommendViewHolder> {

    private List<Recommend> list;
    private Context context;

    public RecommendRecyclerViewAdapter(List<Recommend> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecommendRecyclerViewAdapter.RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item, parent, false);
        RecommendRecyclerViewAdapter.RecommendViewHolder viewHolder = new RecommendViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendRecyclerViewAdapter.RecommendViewHolder holder, int position) {
        Recommend recommend = list.get(position);
        holder.image.setImageResource(recommend.getImage());
        holder.title.setText(recommend.getTitle());
        holder.content.setText(recommend.getContent());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;
        private TextView content;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_recommend_image);
            title = itemView.findViewById(R.id.tv_recommend_title);
            content = itemView.findViewById(R.id.tv_recommend_text);
        }
    }
}
