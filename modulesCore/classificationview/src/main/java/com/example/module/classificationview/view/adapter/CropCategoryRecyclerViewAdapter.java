package com.example.module.classificationview.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.module.classificationview.R;
import com.example.module.libBase.bean.Crop;

import java.util.List;

public class CropCategoryRecyclerViewAdapter extends RecyclerView.Adapter<CropCategoryRecyclerViewAdapter.CropFoodViewHolder> {

    List<Crop.CropDetail> list;
    private OnItemClickListener clickListener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Crop.CropDetail crop);
    }


    public CropCategoryRecyclerViewAdapter(List<Crop.CropDetail> list) {
        this.list = list;
    }

    public CropCategoryRecyclerViewAdapter(List<Crop.CropDetail> list, OnItemClickListener clickListener, Context context) {
        this.list = list;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CropFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cropcard_item, parent, false);
        CropFoodViewHolder viewHolder = new CropFoodViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CropFoodViewHolder holder, int position) {
        Crop.CropDetail crop = list.get(position);
        holder.textView.setText(crop.getName());
        Glide.with(context)
                .load(crop.getIcon())
                .into(holder.imageView); // 将图片加载到 ImageView
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(crop);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class CropFoodViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ConstraintLayout layout;
        private ImageView imageView;
        public CropFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_classification_cropname);
            imageView = itemView.findViewById(R.id.iv_classification_cropimage);
            layout = itemView.findViewById(R.id.cl_classification_cropcard);
        }
    }
}
