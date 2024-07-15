package com.example.studz;

public class CustomItem {

    private int imageResource;
    private String text;

    public CustomItem(int imageResource, String text) {
        this.imageResource = imageResource;
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText() {
        return text;
    }
}