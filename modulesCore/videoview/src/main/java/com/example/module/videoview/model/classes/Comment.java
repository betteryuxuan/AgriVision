package com.example.module.videoview.model.classes;

public class Comment {

    private String image;
    private String name;
    private String comment;

    public Comment(String image, String name, String comment) {
        this.image = image;
        this.name = name;
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
