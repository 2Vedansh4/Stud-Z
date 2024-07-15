package com.example.studz.ui.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studz.R;
import com.example.studz.customadapteranalysis;
import com.example.studz.ui.analysislist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class quizanalysis extends AppCompatActivity {
    private DatabaseReference reference;
    private DatabaseReference userChoicesRef;
    private DatabaseReference databaseReference;

    private List<QuizData> quizDataList;
    private List<String> listu;
    String subject;
    private FirebaseUser currentUser;
    TextView scores;
    String message ;
    public int score = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizanalysis);
        ListView listView = findViewById(R.id.applie);
        Intent intent = getIntent();
        message = intent.getStringExtra("chapter");
        subject = intent.getStringExtra("sujet");
        scores = findViewById(R.id.textView9);


        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users_chapter");


        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            userChoicesRef = database.getReference("user_choices").child(uid);
        }

        listu = new ArrayList<>();
        fetchData(message);

    }

    private void fetchData(String message) {
        reference.child(subject).child(message).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quizDataList = new ArrayList<>();

                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String question = snapshot1.child("question").getValue(String.class);
                        String option1 = snapshot1.child("option1").getValue(String.class);
                        String option2 = snapshot1.child("option2").getValue(String.class);
                        String option3 = snapshot1.child("option3").getValue(String.class);
                        String option4 = snapshot1.child("option4").getValue(String.class);
                        String answer = snapshot1.child("answer").getValue(String.class);

                        quizDataList.add(new QuizData(option1, option2, option3, option4, question, answer));
                    }

                    fetchUserData(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(quizanalysis.this, "Error loading data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserData(String message) {
        userChoicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (int k = 0; k < quizDataList.size(); k++) {
                        String userAdditionalData = snapshot.child(message + "" + k).getValue(String.class);
                        if (userAdditionalData != null) {
                            listu.add(userAdditionalData);
                        } else {
                            listu.add("" + k);
                        }
                    }

                    displayData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(quizanalysis.this, "Error loading user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayData() {
        analysislist[] items = new analysislist[quizDataList.size()];

        for (int i = 0; i < quizDataList.size(); i++) {
            QuizData quizData = quizDataList.get(i);
            items[i] = new analysislist(
                    quizData.getQuestion(),
                    quizData.getOption1(),
                    quizData.getOption2(),
                    quizData.getOption3(),
                    quizData.getOption4(),
                    quizData.getAnswer(),
                    listu.get(i)
            );
            if (quizData.getAnswer().equals(listu.get(i))) {
                score++;
            }
            setChapterVisited(message,score);

        }

        customadapteranalysis adapter = new customadapteranalysis(
                quizanalysis.this, R.layout.analysislist_item, items
        );

        ListView listView = findViewById(R.id.applie);

        listView.setAdapter(adapter);
        scores.setText("score:" + score + "/" + listu.size());

    }
    private void setChapterVisited(String chapterName, int score) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            DatabaseReference userReference = databaseReference.child(currentUser.getUid()).child(subject);

            // Check if the chapter already exists in the list
            userReference.child(chapterName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Chapter already exists, update the score
                        userReference.child(chapterName).setValue(score)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Score updated successfully
                                        } else {
                                            // Handle the error
                                        }
                                    }
                                });
                    } else {
                        // Chapter does not exist, create a new node with the score
                        userReference.child(chapterName).setValue(score)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Chapter and score added successfully
                                        } else {
                                            // Handle the error
                                        }
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error if any
                }
            });
        } else {
            // Handle the case where the user is not authenticated
        }
    }


    @Override
    public void onBackPressed() {
        // Remove user data when the back button is pressed
        if (currentUser != null) {
            String uid = currentUser.getUid();
            userChoicesRef.removeValue(); // Remove user's data
        }
        super.onBackPressed();
    }
}
