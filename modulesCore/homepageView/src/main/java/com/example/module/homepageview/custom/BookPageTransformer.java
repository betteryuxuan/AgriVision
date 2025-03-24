package com.example.module.homepageview.custom;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class BookPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 0) {
            page.setAlpha(1);
            page.setPivotX(page.getWidth());
            page.setRotationY(30 * position);
        } else if (position <= 1) {
            page.setAlpha(1 - position);
            page.setPivotX(0);
            page.setRotationY(30 * position);
        } else {
            page.setAlpha(0);
        }
    }
}
