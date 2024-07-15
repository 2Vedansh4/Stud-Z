package com.example.studz.ui.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;


public class quiz2 extends AppCompatActivity {

    TextView questionno , question ,chaptername ;
    Button nxt , prev ;
    private List<QuizData> list ;
    TextView mins ;
    TextView secs ;
    String message ;
    TextView dot ;
    int min = 60 ;
    int sec = 00 ;
    RadioGroup radioGroup ;
    CountDownTimer countdownTimer;
    RadioButton radioButton ;
    DatabaseReference reference ;
    ProgressBar progressBar ;
        String subject ;

    private DatabaseReference userChoicesRef;
    private FirebaseUser currentUser;
    int position = 0  ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        mins = findViewById(R.id.textView4);
        secs = findViewById(R.id.textView6);
        dot = findViewById(R.id.textView8);

       progressBar= findViewById(R.id.progressBar3 );
        nxt = findViewById(R.id.nxt);
        prev = findViewById(R.id.prev);
        questionno = findViewById(R.id.questionno);
        question = findViewById(R.id.question2);
        chaptername = findViewById(R.id.textView11);
        radioGroup = findViewById(R.id.radioGroup) ;
        progressBar.setVisibility(View.VISIBLE);
        nxt.setVisibility(View.GONE);
        prev.setVisibility(View.GONE);
        questionno.setVisibility(View.GONE);
        dot.setVisibility(View.GONE);


        question.setVisibility(View.GONE);

        chaptername.setVisibility(View.GONE);

        radioGroup.setVisibility(View.GONE);
        Intent intent2 = getIntent();
        String message = intent2.getStringExtra("chaptername");
       subject = intent2.getStringExtra("subjects");

        countdownTimer =  new CountDownTimer(min*60000+sec*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                sec--;
                if(sec == -1){
                    sec= 59;
                    min--;
                    mins.setText(String.valueOf(min));
                }
                secs.setText(String.valueOf(sec));

                }

            @Override
            public void onFinish() {
                Intent intent = new Intent(quiz2.this,  quizanalysis.class);
                intent.putExtra("chapter",chaptername.getText());
                intent.putExtra("sujet",subject);
                startActivity(intent);
                finish();

            }
        };

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            userChoicesRef = database.getReference("user_choices").child(uid);

         }
        reference = FirebaseDatabase.getInstance().getReference() ;


        chaptername.setText(message);
        reference.child(subject).child(message).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the questions array from the dataSnapshot
                    Iterable<DataSnapshot> questionsIterable = snapshot.getChildren();
                    list = new ArrayList<>();

                    for (DataSnapshot snapshot1 : questionsIterable) {
                        String question = snapshot1.child("question").getValue().toString();
                        String option1 = snapshot1.child("option1").getValue().toString();
                        String option2 = snapshot1.child("option2").getValue().toString();
                        String option3 = snapshot1.child("option3").getValue().toString();
                        String option4 = snapshot1.child("option4").getValue().toString();
                        String answer = snapshot1.child("answer").getValue().toString();

                        list.add(new QuizData(option1,option2,option3,option4,question,answer));
                        loadQuestion();


                    }
                    countdownTimer.start();
                    progressBar.setVisibility(View.GONE);

                    nxt.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    questionno.setVisibility(View.VISIBLE);

                    question.setVisibility(View.VISIBLE);

                    chaptername.setVisibility(View.VISIBLE);

                    radioGroup.setVisibility(View.VISIBLE);
                    dot.setVisibility(View.VISIBLE);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });


        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              saveUserChoice();
                loadNextQuestion();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserChoice();
                loadPrevQustion();
            }
        });
    }

    private void loadPrevQustion() {

        if ( 0 < position) {
            position--;
            loadQuestion();
        } else {
        }
//        if(position == list.size() -1){
//            nxt.setText("SUBMIT");
//        }
//        else {
//            nxt.setText("NEXT");
//        }
    }

    private void loadNextQuestion() {

        position++;
        if (position < list.size()) {
            loadQuestion();
        } else {
            Intent intent = new Intent(quiz2.this,  quizanalysis.class);
            intent.putExtra("chapter",chaptername.getText());
            intent.putExtra("sujet",subject);
            startActivity(intent);
            finish();
        }
//        if(position == list.size() -1){
//            nxt.setText("SUBMIT");
//        }
//        else {
//            nxt.setText("NEXT");
//        }
    }

    private void saveUserChoice() {

        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            if (selectedRadioButton != null){
            String selectedOption = selectedRadioButton.getText().toString();
                userChoicesRef.child(chaptername.getText() +""+ position ).setValue(selectedOption);
            }
            else{

            }// Save the user's choice to Firebase
        }
    }

    private void loadQuestion() {

        if (position < list.size()) {
            QuizData currentQuestion = list.get(position);

            // Set the question text
            question.setText(currentQuestion.getQuestion());
            questionno.setText(position +1 +"/" + list.size());

            // Clear existing radio buttons
            radioGroup.removeAllViews();

            // Create radio buttons for each option
            createRadioButton(currentQuestion.getOption1());
            createRadioButton(currentQuestion.getOption2());
            createRadioButton(currentQuestion.getOption3());
            createRadioButton(currentQuestion.getOption4());
        }

        userChoicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User data found
                    String userAdditionalData = snapshot.child(chaptername.getText() + "" + position).getValue(String.class);

                    // Iterate through radio buttons and select the one that matches userAdditionalData
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        View view = radioGroup.getChildAt(i);
                        if (view instanceof RadioButton) {
                            RadioButton radioButton = (RadioButton) view;
                            String optionText = radioButton.getText().toString();

                            // Compare the option text with userAdditionalData and select the matching radio button
                            if (optionText.equals(userAdditionalData)) {
                                radioButton.setChecked(true);
                                break; // No need to continue checking once a match is found
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(quiz2.this, "Error loading data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void createRadioButton(String optionText) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(optionText);
        int radioButtonId = View.generateViewId();
        radioButton.setId(radioButtonId);
        radioGroup.addView(radioButton);
    }

}