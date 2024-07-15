package com.example.studz.ui.code;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class task_data {
    int secs ;
    String decription ;
    String chapter_name ;
    String subject;
    String duration ;

    private DatabaseReference userChoicesRef;
    private FirebaseUser currentUser;

    public int getSecs() {
        return secs;
    }

    public void setSecs(int secs) {
        this.secs = secs;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public task_data(int secs, String decription, String chapter_name, String subject, String duration) {
        this.secs = secs;
        this.decription = decription;
        this.chapter_name = chapter_name;
        this.subject = subject;
        this.duration = duration;
    }
}
