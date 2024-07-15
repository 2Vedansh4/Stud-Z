package com.example.studz.ui.code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studz.R;

import java.util.List;

public class RecyclerTaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<task_data> itemList;
    private Context context;

    public RecyclerTaskAdapter(Context context, List<task_data> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tasks, parent, false);
        return new TaskViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        task_data currentItem = itemList.get(position);
        holder.description.setText(currentItem.getDecription());
        holder.chapter_name.setText(currentItem.getChapter_name());
        holder.duration.setText(currentItem.getDuration());



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
