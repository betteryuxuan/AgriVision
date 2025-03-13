package com.example.module.homepageview.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.Poetry;

import java.util.List;

public class PoetryRecyclerViewAdapter extends RecyclerView.Adapter<PoetryRecyclerViewAdapter.PoetryViewHolder> {

    private List<Poetry.Item> poetryList;
    private OnItemClickListener clickListener;
    private Context mContext;
    private LinearLayout layout;

    public interface OnItemClickListener {
        void onItemClick(Poetry.Item poetry);
    }

    public PoetryRecyclerViewAdapter(List<Poetry.Item> poetryList, OnItemClickListener clickListener, Context mContext) {
        this.poetryList = poetryList;
        this.clickListener = clickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PoetryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poetry_item, parent, false);
        return new PoetryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PoetryViewHolder holder, int position) {
        Poetry.Item poetry = poetryList.get(position);
        holder.title.setText(poetry.getTitle());
        holder.content.setText(poetry.getText());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(poetry);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return poetryList == null ? 0 : poetryList.size();
    }

    public class PoetryViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView content;

        public PoetryViewHolder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.tv_poetry_content);
            title = itemView.findViewById(R.id.tv_poetry_title);
            layout = itemView.findViewById(R.id.ll_poetry_layout);
        }
    }
}
