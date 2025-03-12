package com.example.module.homepageview.model.classes;

import java.util.List;

public class Poetry {
    private int code;
    private String msg;
    private List<Poetry.Item> data;

    public static class Item {
        private String title;
        private String author;
        private String content;
        private String trans;
        private String allusion;
        private String text;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTrans() {
            return trans;
        }

        public void setTrans(String trans) {
            this.trans = trans;
        }

        public String getAllusion() {
            return allusion;
        }

        public void setAllusion(String allusion) {
            this.allusion = allusion;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", trans='" + trans + '\'' +
                    ", allusion='" + allusion + '\'' +
                    '}';
        }

        public Item(String title, String author, String content, String trans, String allusion, String text) {
            this.title = title;
            this.author = author;
            this.content = content;
            this.trans = trans;
            this.allusion = allusion;
            this.text = text;
        }

        public Item() {
        }
    }

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

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Poetry{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
