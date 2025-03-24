package com.example.module.libBase.bean;

public class SwitchPageEvent {
    private final int pageIndex;

    public SwitchPageEvent(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }
}
