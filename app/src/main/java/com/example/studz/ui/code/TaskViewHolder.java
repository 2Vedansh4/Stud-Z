package com.example.studz.ui.code;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studz.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView description;
    public TextView time;
    public TextView duration;
    public TextView chapter_name;



    public TaskViewHolder(View itemView) {
        super(itemView);
        description = itemView.findViewById(R.id.task_description);
        time = itemView.findViewById(R.id.task_timeleft);
        duration = itemView.findViewById(R.id.task_duration);
        chapter_name = itemView.findViewById(R.id.task_chapter);

    }
}
