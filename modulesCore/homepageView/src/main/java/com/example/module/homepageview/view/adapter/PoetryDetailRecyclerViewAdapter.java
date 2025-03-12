package com.example.module.homepageview.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
        drawableList.add(R.drawable.peotry_bac1);
        drawableList.add(R.drawable.peotry_bac2);
        drawableList.add(R.drawable.poetry_bac3);
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
    }

    @Override
    public int getItemCount() {
        return poetryList == null ? 0 : poetryList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Poetry poetry);
    }


    public class PoetryDetailViewHolder extends RecyclerView.ViewHolder {
        public PoetryDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
