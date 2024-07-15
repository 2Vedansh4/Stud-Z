package com.example.studz;

public class MyDataEntity {
    private String date;
    private String time;
    private String title;
    private String longDescription;


    public MyDataEntity(String date, String time, String title, String longDescription) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.longDescription = longDescription;
    }

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
