package com.example.studz.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
//import android.util.Log;
import android.widget.Toast;

import com.example.studz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Scroll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        Mypageradapter adapter = new Mypageradapter(this);

        DatabaseReference parentReference = FirebaseDatabase.getInstance().getReference("fragment_sequence").child("Chemical Bonding");
       parentReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                   // Iterate through the children nodes
                   String key = childSnapshot.getKey(); // Get the key (node name)
                   String value =  childSnapshot.getValue(String.class);
                   if(value.equals("1")){
                       adapter.addFragment(new fragment1());

                   }
                   if(value.equals("2")){
                       adapter.addFragment(new fragment2());
                   }
                   if(value.equals("3")){
                       adapter.addFragment(new fragment3());

                   }
                   // Do something with the key and value
                   Toast.makeText(Scroll.this, "FirebaseData"+ "Key: " + key + ", Value:" + value , Toast.LENGTH_SHORT).show(); ;
                 //  Log.d("FirebaseData", "Key: ");


           }
               viewPager2.setAdapter(adapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(Scroll.this, "bhag", Toast.LENGTH_SHORT).show();

           }
       });

        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

    }
}