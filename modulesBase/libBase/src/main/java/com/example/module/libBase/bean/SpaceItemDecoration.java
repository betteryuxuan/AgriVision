package com.example.module.libBase.bean;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        // 第一项
        if (position == 0) {
            outRect.left = space; // 给第一项左侧加上间距
        }
        // 最后一项
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.right = space; // 给最后一项右侧加上间距
        }

    }
}
