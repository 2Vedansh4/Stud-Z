package com.example.studz;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studz.ui.TaskyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tasky extends AppCompatActivity {

    private static final long UPDATE_DELAY = 3000; // 3 seconds delay

    private RecyclerView recyclerView;
    private TaskyAdapter taskyAdapter;
    private List<Tasks> taskList;
    private FirebaseUser currentUser;
    private DatabaseReference userReference;

    private Button aitaskadd;
    private Button viewcalendar;
    private TextView datess;
    private Button today;
    private Calendar selectedDate = Calendar.getInstance();

    // Handler for delayed update
    private Handler handler = new Handler();

    // Runnable for delayed update
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            // Fetch data after 3 seconds
            if (datess.getText() != null) {
                fetchListOfStringsForDate(datess.getText().toString());
            }
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewcalendar = findViewById(R.id.viewcalendar);
        recyclerView = findViewById(R.id.recyclerContact);
        aitaskadd = findViewById(R.id.automatictask);
        datess = findViewById(R.id.textView2);
        today = findViewById(R.id.todays);

        // Create a Calendar instance and set it to the current time
        Calendar calendar = Calendar.getInstance();

        // Get the current date
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        datess.setText(formattedDate);

        // Firebase setup
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            userReference = database.getReference("users").child(uid);
            recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager here
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }

        // Set button click listeners
        today.setOnClickListener(view -> {
            datess.setText(formattedDate);
            fetchListOfStringsForDate(formattedDate);
        });

        viewcalendar.setOnClickListener(view -> showDatePickerDialog());

        aitaskadd.setOnClickListener(view -> {
            Intent intent = new Intent(Tasky.this, AiTaskAdd.class);
            startActivity(intent);
        });

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskyAdapter = new TaskyAdapter(taskList, new TaskyAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Tasks task) {
                Toast.makeText(Tasky.this, "hey", Toast.LENGTH_SHORT).show();
                // Handle edit task
            }

            @Override
            public void onDeleteClick(Tasks task) {
                Toast.makeText(Tasky.this, "bye", Toast.LENGTH_SHORT).show();
                // Handle delete task
            }
        });
        recyclerView.setAdapter(taskyAdapter);

        // Initial data fetch
        fetchListOfStringsForDate(formattedDate);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    selectedDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());
                    datess.setText(formattedDate);
                    fetchListOfStringsForDate(formattedDate);
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Schedule delayed update after 3 seconds
        handler.postDelayed(updateRunnable, UPDATE_DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove the scheduled update callbacks when activity is paused
        handler.removeCallbacks(updateRunnable);
    }

    private void fetchListOfStringsForDate(String date) {
        if (userReference != null) {
            DatabaseReference dateRef = userReference.child(date);
            dateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    taskList.clear();
                    for (DataSnapshot stringSnapshot : snapshot.getChildren()) {
                        String value = stringSnapshot.getValue(String.class);
                        if (value != null) {
                            String[] parts = value.split(",");
                            taskList.add(new Tasks(parts[2], parts[3], parts[0], parts[1]));
                        }
                    }
                    taskyAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Tasky.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
