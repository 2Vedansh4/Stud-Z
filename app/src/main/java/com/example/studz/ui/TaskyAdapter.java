package com.example.studz.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studz.R;
import com.example.studz.Tasks;

import java.util.List;

public class TaskyAdapter extends RecyclerView.Adapter<TaskyAdapter.TaskViewHolder> {
    private List<Tasks> taskList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Tasks task);
        void onDeleteClick(Tasks task);
    }

    public TaskyAdapter(List<Tasks> taskList, OnItemClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_addyourtask, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Tasks currentTask = taskList.get(position);
        holder.taskDescription.setText(currentTask.getDescription());
        holder.taskTime.setText(currentTask.getTime());

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClick(currentTask);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(currentTask);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskDescription;
        public TextView taskTime;
        public Button buttonEdit;
        public Button buttonDelete;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskDescription = itemView.findViewById(R.id.description);
            taskTime = itemView.findViewById(R.id.timeat);
            buttonEdit = itemView.findViewById(R.id.edittask);
            buttonDelete = itemView.findViewById(R.id.donetask);
        }
    }
}
