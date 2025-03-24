package com.example.module.homepageview.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.Poetry;

import java.util.ArrayList;
import java.util.List;

public class PoetryDetailRecyclerViewAdapter extends RecyclerView.Adapter<PoetryDetailRecyclerViewAdapter.PoetryDetailViewHolder> {

    private List<Poetry.Item> poetryList;
    private PoetryRecyclerViewAdapter.OnItemClickListener clickListener;
    private Context mContext;

    List<Integer> drawableList = new ArrayList<>();

    public PoetryDetailRecyclerViewAdapter(List<Poetry.Item> poetryList, PoetryRecyclerViewAdapter.OnItemClickListener clickListener, Context mContext) {
        this.poetryList = poetryList;
        this.clickListener = clickListener;
        this.mContext = mContext;
        drawableList.add(R.drawable.poetry_bac4);
        drawableList.add(R.drawable.poetry_bac5);
        drawableList.add(R.drawable.poetry_bac6);
    }

    @NonNull
    @Override
    public PoetryDetailRecyclerViewAdapter.PoetryDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poetry_detail_item, parent, false);
        return new PoetryDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoetryDetailRecyclerViewAdapter.PoetryDetailViewHolder holder, int position) {
        int drawable = drawableList.get(position % drawableList.size());
        holder.constraintLayout.setBackgroundResource(drawable);
        Poetry.Item poetry = poetryList.get(position);
        holder.title.setText(poetry.getTitle());
        holder.author.setText(poetry.getAuthor());
        holder.content.setText(poetry.getText());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
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

    public interface OnItemClickListener {
        void onItemClick(Poetry poetry);
    }


    public class PoetryDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView title, author, content;
        private ConstraintLayout constraintLayout;
        public PoetryDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_poetry_detail_title);
            author = itemView.findViewById(R.id.tv_poetry_detail_author);
            content = itemView.findViewById(R.id.tv_poetry_detail_text);
            constraintLayout = itemView.findViewById(R.id.cl_poetry_detail_layout);
        }
    }
}
