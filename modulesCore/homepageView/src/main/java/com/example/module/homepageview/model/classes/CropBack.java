package com.example.module.homepageview.model.classes;

import com.example.module.libBase.bean.Crop;

public class CropBack {
    private int code;
    private String msg;
    private Crop.CropDetail data; // 这里改成 Crop.CropDetail，而不是 List

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Crop.CropDetail getData() {
        return data;
    }

    public void setData(Crop.CropDetail data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CropBack{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
