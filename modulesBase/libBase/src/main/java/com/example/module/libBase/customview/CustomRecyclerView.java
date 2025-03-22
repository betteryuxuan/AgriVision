package com.example.module.libBase.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;
public class CustomRecyclerView extends RecyclerView {
    private float downX;
    private float downY;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                // 初始时先让子控件处理事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(ev.getX() - downX);
                float deltaY = Math.abs(ev.getY() - downY);
                if (deltaX > deltaY) {
                    // 当检测到水平滑动时，允许父控件（ViewPager2）拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    // 竖向滑动则由 RecyclerView 自己处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 事件结束后恢复默认状态
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
