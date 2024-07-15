package com.example.studz.chaptertopics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.studz.R;
import com.example.studz.chapters.five.chfive;
import com.example.studz.chapters.four.chfour;
import com.example.studz.chapters.one.chone;
import com.example.studz.chapters.three.chthree;
import com.example.studz.chapters.two.chtwo;


public class TopicActivity extends AppCompatActivity {
    Toolbar toolbar;
    ExpandableHeightGridView gridView ;
    String chaptername;
    adapter adapter ;
    String arr[];
    ImageView chapterImage;

    String chapter1[] = {"chone","ball","cat","dog","elephant","goat","ijhj","kuhh","uuygu"};
    String chapter2[] = {"chtwo","ba","at","do","elephant","go","ijhj","kuhh","uuygu"};

    String chapter3[] = {"chthree","bal","c","g","elephant","gt","ijhj","kuhh","uuygu"};

    String chapter4[] = {"chfour","all","ca","dg","elephant","goat","ijhj","kuhh","uuygu"};
    String chapter5[] = {"chfive","bl","ct","og","elephant","got","ijhj","kuhh","uuygu"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        toolbar = findViewById(R.id.toolbar);
        gridView = findViewById(R.id.topics_name);
        gridView.setExpanded(true);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chaptername = getIntent().getStringExtra("chapterName");
        chapterImage = findViewById(R.id.chapterinmage);
        compareandOpen();


    }

    private void compareandOpen() {

        if(chaptername.equals("heading1")){
            arr = chapter1;
            Glide.with(TopicActivity.this).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img.jpg?alt=media&token=2d75c419-4e63-4138-84ba-813ed0470824").into(chapterImage);
            getSupportActionBar().setTitle("heading1");
        } else if (chaptername.equals("heading2")) {
            arr = chapter2;
            Glide.with(TopicActivity.this).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img.jpg?alt=media&token=2d75c419-4e63-4138-84ba-813ed0470824").into(chapterImage);
            getSupportActionBar().setTitle("heading2");


        }
        else if (chaptername.equals("heading3")) {
            arr = chapter3;
            Glide.with(TopicActivity.this).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img.jpg?alt=media&token=2d75c419-4e63-4138-84ba-813ed0470824").into(chapterImage);
            getSupportActionBar().setTitle("heading3");


        }
        else if (chaptername.equals("heading4")) {
            arr = chapter4;
            Glide.with(TopicActivity.this).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img.jpg?alt=media&token=2d75c419-4e63-4138-84ba-813ed0470824").into(chapterImage);
            getSupportActionBar().setTitle("heading4");



        }else if (chaptername.equals("heading5")) {
            Glide.with(TopicActivity.this).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img.jpg?alt=media&token=2d75c419-4e63-4138-84ba-813ed0470824").into(chapterImage);
            getSupportActionBar().setTitle("heading5");


            arr = chapter5;
        }else {
            arr = null;
        }

        adapter = new adapter(arr,TopicActivity.this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             openActivity(arr[position])    ;        }
        });
    }

    private void openActivity(String s) {
        switch (s){
            case "chone" :
                startActivity(new Intent(TopicActivity.this, chone.class));
                break;
            case "chtwo" :
                startActivity(new Intent(TopicActivity.this, chtwo.class));
                break;
            case "chthree" :
                startActivity(new Intent(TopicActivity.this, chthree.class));
                break;
            case "chfour" :
                startActivity(new Intent(TopicActivity.this, chfour.class));
                break;
            case "chfive" :
                startActivity(new Intent(TopicActivity.this, chfive.class));
                break;
        }
    }


}