package com.example.studz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class test extends AppCompatActivity {
    Button btnLogOut;
    TextView txtUser;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        firebaseAuth = FirebaseAuth.getInstance();
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        txtUser = (TextView) findViewById(R.id.txtUser);
        user = firebaseAuth.getCurrentUser();

        if (user == null ) {
            Intent intent = new Intent(getApplicationContext(), Loss.class);
            startActivity(intent);
            finish();
        }
        else {
            txtUser.setText(user.getEmail());
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                //Intent I = new Intent(UserActivity.this, LoginActivity.class);
                //startActivity(I);
                Intent intent = new Intent(getApplicationContext(), Loss.class);
                startActivity(intent);
                finish();

            }
        });

    }
}