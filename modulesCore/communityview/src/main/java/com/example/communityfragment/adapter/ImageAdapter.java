package com.example.communityfragment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.communityfragment.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private Context context;
    private List<String> imageUrls;
    private AlertDialog.Builder builder;
    private AlertDialog imageDialog;

    public ImageAdapter(Context context, List<String> imageUrls, OnImageActionListener listener) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.actionListener = listener;
    }

    private OnImageActionListener actionListener;

    public interface OnImageActionListener {
        void onAddImageClick();

        void onDeleteImage(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("ImageAdapterTAG", "onBindViewHolder: " + position);
        if (position < imageUrls.size()) {
            Glide.with(context).
                    load(imageUrls.get(position))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageView);

            holder.deleteImage.setVisibility(View.VISIBLE);
            holder.deleteImage.setOnClickListener(v -> {
                int adapterPosition = holder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < imageUrls.size()) {
                    Log.d("ImageAdapterTAG", "adapterPosition: " + adapterPosition);
//                    imageUrls.remove(adapterPosition);
                    actionListener.onDeleteImage(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                }
            });
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setImageDialog(holder.getAbsoluteAdapterPosition());
                }
            });
        } else {
            holder.deleteImage.setVisibility(View.GONE);
            Glide.with(context)
                    .load(R.drawable.ic_addimg)
                    .into(holder.imageView);
            holder.cardView.setOnClickListener(v -> actionListener.onAddImageClick());
        }

    }

    @Override
    public int getItemCount() {
        return imageUrls.size() < 9 ? imageUrls.size() + 1 : imageUrls.size();
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged();
    }

    // 展示当前照片
    private void setImageDialog(int position) {
        String imageUri = imageUrls.get(position);

        builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_image, null, false);
        ImageView photoView = dialogView.findViewById(R.id.photo_view_dialog);

        Glide.with(context)
                .load(imageUri)
                .error(R.drawable.ic_launcher_background)
                .into(photoView);

        builder.setView(dialogView);
        imageDialog = builder.create();
        imageDialog.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
        imageDialog.show();

        photoView.setOnClickListener(v1 -> imageDialog.dismiss());
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView deleteImage;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_publish_image);
            deleteImage = itemView.findViewById(R.id.img_publish_delete);
            cardView = itemView.findViewById(R.id.cv_publish_photo);
        }
    }
}
