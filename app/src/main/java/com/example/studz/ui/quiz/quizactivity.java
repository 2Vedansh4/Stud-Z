package com.example.studz.ui.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class quizactivity extends AppCompatActivity {
    private TextView questionTxt , indicator ;
    private LinearLayout container ;
    private Button nextBtn, ShareBtn ;
    private int score  = 0  ;
    private int position = 0 ;
    private int count = 0 ;

    DatabaseReference reference ;
    private List<QuizData> list ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizactivity);
        container = findViewById(R.id.linearLayout2);
        nextBtn = findViewById(R.id.nextbtn);
        ShareBtn = findViewById(R.id.sharebtn);
        questionTxt = findViewById(R.id.question);
        indicator = findViewById(R.id.indicator);
        ProgressBar loadingIndicator = findViewById(R.id.progressBar);
        questionTxt.setVisibility(View.GONE);
        indicator.setVisibility(View.GONE);
        container.setVisibility(View.GONE);
        nextBtn.setVisibility(View.GONE);
        ShareBtn.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);


        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference() ;

        Intent intent1 = getIntent();
        String message = intent1.getStringExtra("chaptername");


        reference.child(message).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 :snapshot.getChildren()){
                    loadingIndicator.setVisibility(View.GONE);
                    String question = snapshot1.child("question").getValue().toString();
                    String option1 = snapshot1.child("option1").getValue().toString();
                    String option2 = snapshot1.child("option2").getValue().toString();
                    String option3 = snapshot1.child("option3").getValue().toString();
                    String option4 = snapshot1.child("option4").getValue().toString();
                    String answer = snapshot1.child("answer").getValue().toString();

                list.add(new QuizData(option1,option2,option3,option4,question,answer));}

                if(list.size()>0) {
                    loadQuestion(questionTxt, 0, list.get(position).getQuestion());
                    for (int i = 0; i < 4; i++) {
                        container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button) v);
                            }
                        });
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Show elements after the animation
                            questionTxt.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.VISIBLE);
                            container.setVisibility(View.VISIBLE);
                            nextBtn.setVisibility(View.VISIBLE);
                            ShareBtn.setVisibility(View.VISIBLE);
                        }
                    }, 1000);
                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            nextBtn.setEnabled(false);
//                            nextBtn.setAlpha(0.7f);
                            enabled(true);
                            position++;
                            if (position == list.size()) {
                                Intent intent = new Intent(quizactivity.this, Score.class);
                                intent.putExtra("score", score);
                                intent.putExtra("total", list.size());
                                startActivity(intent);
                                finish();
                                return;
                            }
                            count = 0;
                            loadQuestion(questionTxt, 0, list.get(position).getQuestion());
                        }
                    });
                    ShareBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String body = "*" + list.get(position).getQuestion() + "*\n" +
                                    "(a)" + list.get(position).getOption1() + "\n" +
                                    "(b)" + list.get(position).getOption2() + "\n" +
                                    "(c)" + list.get(position).getOption3() + "\n" +
                                    "(d)" + list.get(position).getOption4();
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("Text/Plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Learning App");
                            intent.putExtra(Intent.EXTRA_TEXT, body);
                            startActivity(Intent.createChooser(intent, "Share via"));


                        }
                    });

                }else{
                    Toast.makeText(quizactivity.this, "no data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingIndicator.setVisibility(View.GONE);

                Toast.makeText(quizactivity.this, "Error loading data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(quizactivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAnswer(Button selectedoption) {
        enabled(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectedoption.getText().toString().equals(list.get(position).getAnswer())){
            score ++ ;
            selectedoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
        }else {
            selectedoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
           Button correctOption = container.findViewWithTag(list.get(position).getAnswer());
           correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));


        }
    }
    private void enabled(Boolean enable){
        for(int i = 0 ; i < 4 ; i++){
            container.getChildAt(i).setEnabled(enable);
            if(enable){
                container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }
    }
    private void loadQuestion(View view, int value, String data) {
        for(int i = 0 ;i<4 ; i++){
            container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));

        }
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
        .setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if(value == 0 && count <4){
                            String option = "";
                            if(count == 0 )
                                option = list.get(position).getOption1();
                            else if(count == 1)
                                option = list.get(position).getOption2();
                            else if(count == 2)
                                option = list.get(position).getOption3();
                            else if(count == 3)
                                option = list.get(position).getOption4();
                             loadQuestion(container.getChildAt(count), 0 , option);
                             count ++ ;
                        }
                    }
                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        if(value == 0 ){
                            try{
                            ((TextView)view).setText(data);
                            indicator.setText(position+1 + "/"+ list.size());
                              }catch(ClassCastException e){
                                ((Button)view).setText(data);
                            }
                            view.setTag(data);
                            loadQuestion(view , 1,data);
                    }}
                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                    }
                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                    }
                }) ;
    }
}