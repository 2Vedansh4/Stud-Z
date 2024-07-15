package com.example.studz.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studz.R;

public class Score extends AppCompatActivity {
    TextView scoreTxt  ;
    int score ,total ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score = getIntent().getIntExtra("score",0);
        total = getIntent().getIntExtra("total",0);
        scoreTxt = findViewById(R.id.scorie);
        scoreTxt.setText(String.valueOf(score)+"/" + String.valueOf(total));



    }
}