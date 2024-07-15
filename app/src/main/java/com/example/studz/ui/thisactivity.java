package com.example.studz.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.studz.Loss;
import com.example.studz.MainActivity;
import com.example.studz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class thisactivity extends AppCompatActivity {
    public EditText signupEmail, signupPassword;
    Button btnSignUp;
    TextView loginRedirectText;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thisactivity);
        firebaseAuth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.edEmail);
        signupPassword = findViewById(R.id.edPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        loginRedirectText = findViewById(R.id.txtSignIn);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser(); // Check if user is already logged in

        if (currentUser != null) {
            // User is already authenticated, redirect to MainActivity
            startActivity(new Intent(thisactivity.this, MainActivity.class));
            finish(); // Close this activity
        }

        //
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = signupEmail.getText().toString();
                String paswd = signupPassword.getText().toString();
                if (emailID.isEmpty()) {
                    signupEmail.setError("Provide your Email first!");
                    signupEmail.requestFocus();
                } else if (paswd.isEmpty()) {
                    signupPassword.setError("Set your password");
                    signupPassword.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(thisactivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(thisactivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(thisactivity.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(thisactivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(thisactivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(thisactivity.this, Loss.class);
                startActivity(I);
            }
        });

    }
}


