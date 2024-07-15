package com.example.studz;
    // CustomAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomAdapter extends ArrayAdapter<CustomItem> {

        public CustomAdapter(Context context, int resource, CustomItem[] objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            // Get the current item
            CustomItem currentItem = getItem(position);

            // Bind data to the view elements
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView = convertView.findViewById(R.id.textView);

            if (currentItem != null) {
                imageView.setImageResource(currentItem.getImageResource());
                textView.setText(currentItem.getText());
            }

            return convertView;
        }
    }


