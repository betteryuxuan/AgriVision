package com.example.module.homepageview.view.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.Keyword;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private List<Keyword.Item> keywordList;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Keyword.Item keyword);
    }


    public SearchRecyclerViewAdapter(List<Keyword.Item> keywordList, OnItemClickListener clickListener) {
        this.keywordList = keywordList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.keyword_find_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.ViewHolder holder, int position) {
        Keyword.Item keyword = keywordList.get(position);
        holder.name.setText(keyword.getName());
        holder.text.setText(Html.fromHtml(keyword.getSnippet(), Html.FROM_HTML_MODE_LEGACY));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(keyword);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return keywordList == null ? 0 : keywordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout layout;
        private TextView name;
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.cl_search_layout);
            name = itemView.findViewById(R.id.tv_find_name);
            text = itemView.findViewById(R.id.tv_find_text);
        }
    }

    public void updateData(List<Keyword.Item> newData) {
        keywordList.clear();
        keywordList.addAll(newData);
        notifyDataSetChanged();  // 通知 RecyclerView 更新
    }
}
