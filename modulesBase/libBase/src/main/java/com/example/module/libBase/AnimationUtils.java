package com.example.module.libBase;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AnimationUtils {
    // 缩放动画
    public static void setonlyAnimateView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f).setDuration(200);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f).setDuration(200);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.9f);

                animator1.start();
                animator2.start();
                animator3.start();

                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ObjectAnimator reverseAnimator1 = ObjectAnimator.ofFloat(view, "scaleX", 0.95f, 1f).setDuration(200);
                        ObjectAnimator reverseAnimator2 = ObjectAnimator.ofFloat(view, "scaleY", 0.95f, 1f).setDuration(200);
                        ObjectAnimator reverseAnimator3 = ObjectAnimator.ofFloat(view, "alpha", 0.9f, 1f);

                        reverseAnimator1.start();
                        reverseAnimator2.start();
                        reverseAnimator3.start();
                    }
                });
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.99f).setDuration(200);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.99f).setDuration(200);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.8f);

                animator1.start();
                animator2.start();
                animator3.start();

                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ObjectAnimator reverseAnimator1 = ObjectAnimator.ofFloat(view, "scaleX", 0.99f, 1f).setDuration(200);
                        ObjectAnimator reverseAnimator2 = ObjectAnimator.ofFloat(view, "scaleY", 0.99f, 1f).setDuration(200);
                        ObjectAnimator reverseAnimator3 = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 1f);

                        reverseAnimator1.start();
                        reverseAnimator2.start();
                        reverseAnimator3.start();
                    }
                });
                return true;
            }
        });
    }

    // 给有点击事件的view设置动画
    public static void setAnimateView(View view) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f).setDuration(200);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f).setDuration(200);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.95f);

        animator1.start();
        animator2.start();
        animator3.start();

        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator reverseAnimator1 = ObjectAnimator.ofFloat(view, "scaleX", 0.95f, 1f).setDuration(200);
                ObjectAnimator reverseAnimator2 = ObjectAnimator.ofFloat(view, "scaleY", 0.95f, 1f).setDuration(200);
                ObjectAnimator reverseAnimator3 = ObjectAnimator.ofFloat(view, "alpha", 0.95f, 1f);

                reverseAnimator1.start();
                reverseAnimator2.start();
                reverseAnimator3.start();
            }
        });
    }

    // 震动动画
    public static void setShakeAnimateView(View view) {
        float shakeOffset = 5f;

        ValueAnimator shakeAnimator = ValueAnimator.ofFloat(0, shakeOffset, -shakeOffset, shakeOffset, 0);
        shakeAnimator.setDuration(100);
        shakeAnimator.setRepeatCount(3); // 重复次数
        shakeAnimator.setRepeatMode(ValueAnimator.REVERSE);

        shakeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setTranslationX(value);
            }
        });

        shakeAnimator.start();
    }

    public static void setLikeAnimate(View view) {
        // 放大并缩小
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1f);  // 水平方向放大并还原
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1f);  // 垂直方向放大并还原

        scaleX.setDuration(400);
        scaleY.setDuration(400);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleX.start();
        scaleY.start();
    }

    public static void applyClickAnimation(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 按下时缩小 + 透明
                        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1f, 0.95f);
                        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1f, 0.95f);
                        ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1f, 0.7f);

                        AnimatorSet shrinkSet = new AnimatorSet();
                        shrinkSet.playTogether(scaleX, scaleY, alpha);
                        shrinkSet.setDuration(100);
                        shrinkSet.start();
                        break;

                    case MotionEvent.ACTION_UP: // 松开时恢复
                    case MotionEvent.ACTION_CANCEL:
                        ObjectAnimator scaleXBack = ObjectAnimator.ofFloat(v, "scaleX", 0.95f, 1f);
                        ObjectAnimator scaleYBack = ObjectAnimator.ofFloat(v, "scaleY", 0.95f, 1f);
                        ObjectAnimator alphaBack = ObjectAnimator.ofFloat(v, "alpha", 0.7f, 1f);

                        AnimatorSet restoreSet = new AnimatorSet();
                        restoreSet.playTogether(scaleXBack, scaleYBack, alphaBack);
                        restoreSet.setDuration(100);
                        restoreSet.start();
                        break;
                }
                return false; // 让事件继续传递（否则点击事件可能失效）
            }
        });
    }



}
