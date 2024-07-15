package com.example.studz.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studz.CustomAdapter;
import com.example.studz.CustomItem;
import com.example.studz.R;

public class toquiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toquiz);

        Intent intent1 = getIntent();
        String subject = intent1.getStringExtra("Subject");

        CustomItem[] items;

        if (subject.equals("Physics")) {
            items = new CustomItem[]{
                    new CustomItem(R.drawable.clipart1224, "Projectile Motion"),
                    new CustomItem(R.drawable.clipart39548, "NLM"),
            };
        } else if (subject.equals("Chemistry")) {
            items = new CustomItem[]{
                    new CustomItem(R.drawable.clipart2467778, "Chemical Bonding"),
                    new CustomItem(R.drawable.clipart2205873, "Periodic Table"),
            };
        } else if (subject.equals("Mathematics")) {
            items = new CustomItem[]{
                    new CustomItem(R.drawable.clipart2802160, "Quadratic Equations"),
                    new CustomItem(R.drawable.clipart2443903, "Conic Sections"),
            };
        } else {
            // Handle the case when the subject is unknown or not provided
            return;
        }

        CustomAdapter adapter = new CustomAdapter(this, R.layout.list_item, items);

        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomItem clickedItem = (CustomItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(toquiz.this, quiz2.class);
                intent.putExtra("chaptername", clickedItem.getText());
                intent.putExtra("subjects",subject);
                startActivity(intent);

            }
        });
    }
}
