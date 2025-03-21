package com.example.communityfragment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.communityfragment.R;

import java.util.List;

public class ImageDisplayAdapter extends RecyclerView.Adapter<ImageDisplayAdapter.DisplayViewHolder> {
    private Context context;
    private List<String> imageUrls;
    private AlertDialog.Builder builder;
    private AlertDialog imageDialog;

    public ImageDisplayAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public DisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_diaplay, parent, false);
        return new DisplayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayViewHolder holder, int position) {
        Glide.with(context)
                .load(imageUrls.get(position))
                .placeholder(R.drawable.bg_white)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.8f)
                .into(holder.imageView);
        holder.cardView.setOnClickListener(v -> setImageDialog(position));
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

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

    static class DisplayViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView;

        public DisplayViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_display_photo);
            imageView = itemView.findViewById(R.id.img_display_image);
        }
    }
}