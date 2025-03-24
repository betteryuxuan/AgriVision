package com.example.module.homepageview.model.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Poetry implements Parcelable{
    private int code;
    private String msg;
    private List<Item> data;

    public static class Item implements Parcelable {
        private String title;
        private String author;
        private String content;
        private String trans;
        private String allusion;
        private String text;
        private String introduce;

        public Item(String title, String author, String content, String trans, String allusion, String text, String introduce) {
            this.title = title;
            this.author = author;
            this.content = content;
            this.trans = trans;
            this.allusion = allusion;
            this.text = text;
            this.introduce = introduce;
        }

        protected Item(Parcel in) {
            title = in.readString();
            author = in.readString();
            content = in.readString();
            trans = in.readString();
            allusion = in.readString();
            text = in.readString();
            introduce = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(author);
            dest.writeString(content);
            dest.writeString(trans);
            dest.writeString(allusion);
            dest.writeString(text);
            dest.writeString(introduce);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel in) {
                return new Item(in);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };

        // Getter and Setter methods for fields
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

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", trans='" + trans + '\'' +
                    ", allusion='" + allusion + '\'' +
                    ", text='" + text + '\'' +
                    ", introduce='" + introduce + '\'' +
                    '}';
        }

        public Item() {
        }
    }

    // Getter and Setter methods for Poetry class
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

    // Make Poetry class Parcelable
    public static final Parcelable.Creator<Poetry> CREATOR = new Parcelable.Creator<Poetry>() {
        @Override
        public Poetry createFromParcel(Parcel in) {
            Poetry poetry = new Poetry();
            poetry.code = in.readInt();
            poetry.msg = in.readString();
            poetry.data = in.createTypedArrayList(Item.CREATOR);
            return poetry;
        }

        @Override
        public Poetry[] newArray(int size) {
            return new Poetry[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(data);
    }

    public int describeContents() {
        return 0;
    }
}
