package com.example.studz.chaptertopics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studz.R;

public class adapter extends BaseAdapter {
    private String topic_name[];
    private Context context;

    public adapter(String[] topic_name, Context context) {
        this.topic_name = topic_name;
        this.context = context;
    }

    @Override
    public int getCount() {
        return topic_name.length;
    }

    @Override
    public Object getItem(int position) {
        return topic_name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        LayoutInflater inflater;

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_topic_item_layout, null);
        }

        TextView textView = gridView.findViewById(R.id.topic_text);
        textView.setText(topic_name[position]);

        return gridView;
    }
}
