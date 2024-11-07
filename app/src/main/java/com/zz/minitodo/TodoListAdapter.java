package com.zz.minitodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zz.minitodo.models.Todo;
import com.zz.minitodo.viewModels.SharedListViewModel;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private final SharedListViewModel sharedListViewModel;
    private final String TAG = "TodoListAdapter";
    private List<Todo> todoList;

    public TodoListAdapter(List<Todo> todoList, SharedListViewModel sharedListViewModel) {
        this.todoList = todoList;
        this.sharedListViewModel = sharedListViewModel;
    }

    public void updateTodoList(List<Todo> newTodoList) {
        this.todoList = newTodoList;  // update the new todoList
        notifyDataSetChanged();  // update the UI
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Todo todo = todoList.get(position);

        holder.todoText.setText(todo.text);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(todo.isChecked);

        holder.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                sharedListViewModel.moveToDone(todo);
                notifyItemRemoved(position);
            }
        }));
    }
    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
