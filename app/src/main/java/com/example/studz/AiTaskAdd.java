package com.example.studz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studz.ui.GeminiPro;
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
import java.util.List;

public class AiTaskAdd extends AppCompatActivity {
    EditText editText;
    Button button;
    private DatabaseReference databaseReference;

    private static final String TAG = "AiTaskAdd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ai_task_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        button = findViewById(R.id.AIsubmit);
        editText = findViewById(R.id.Aitext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeminiPro model = new GeminiPro();
                String query = editText.getText().toString();
                query = query + " I want to add task in my app based on this text please give me date,time,title,description of task in format (DD/MM/YYYY,24:59,title,description) also replace nos by zero if not time or date found also if multiple date present give the best date and not multiple fomrat should be same nothing else to be given if information cant be found return -1 default year is 2024 ";
                            model.getResponse(query, new ResponseCallback() {
                        @Override
                    public void onResponse(String response) {
                        Toast.makeText(AiTaskAdd.this, response, Toast.LENGTH_SHORT).show();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            String[] parts = response.split(",");

                            setChapterVisited(userId, parts[0],response );
                        } else {
                            Toast.makeText(AiTaskAdd.this, "User not logged in", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(AiTaskAdd.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    public interface ResponseCallback {
        void onResponse(String response);
        void onError(Throwable throwable);
    }

    private void setChapterVisited(String userId, String response, String description) {
        DatabaseReference userReference = databaseReference.child(userId);

        // Check if the chapter already exists in the list
        userReference.child(response).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> descriptionsList;
                if (snapshot.exists()) {
                    // Retrieve the existing list of descriptions
                    descriptionsList = snapshot.getValue(new GenericTypeIndicator<List<String>>() {});
                    if (descriptionsList == null) {
                        descriptionsList = new ArrayList<>();
                    }
                } else {
                    // Create a new list if it does not exist
                    descriptionsList = new ArrayList<>();
                }

                // Add the new description to the list
                descriptionsList.add(description);

                // Update the database with the new list
                userReference.child(response).setValue(descriptionsList)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AiTaskAdd.this, "Chapter updated successfully", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Chapter updated successfully");
                                } else {
                                    Toast.makeText(AiTaskAdd.this, "Failed to update chapter", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Failed to update chapter", task.getException());
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
            }
        });
    }}
