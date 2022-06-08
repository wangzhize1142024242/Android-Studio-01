package com.example.androidstudio01.bean;

import java.io.Serializable;

public class Note implements Serializable {

    private String takePhoto;
    private String createdTime;
    private String createdDate;
    private String title;
    private String content;
    private String circumstance;
    private String id;
    private String postpone;

    public String getPostpone() {
        return postpone;
    }

    public void setPostpone(String postpone) {
        this.postpone = postpone;
    }

    public String getCircumstance() {
        return circumstance;
    }

    public void setCircumstance(String circumstance) {
        this.circumstance = circumstance;
    }

    public String getTakePhoto() {
        return takePhoto;
    }

    public void setTakePhoto(String takePhoto) {
        this.takePhoto = takePhoto;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Note{" +
                "takePhoto='" + takePhoto + '\'' +
                "createdTime='" + createdTime + '\'' +
                "createdDate='" + createdDate + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
