package com.zz.minitodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zz.minitodo.models.Todo;
import com.zz.minitodo.viewModels.SharedListViewModel;

import java.util.List;

public class TodoListAdapter extends ViewHolderAdapter {
    private final Context context;
    private final SharedListViewModel sharedListViewModel;
    private final String TAG = "TodoListAdapter";
    private List<Todo> todoList;

    public TodoListAdapter(@NonNull Context context, List<Todo> todoList, SharedListViewModel sharedListViewModel) {
        this.context = context;
        this.todoList = todoList;
        this.sharedListViewModel = sharedListViewModel;

        if (sharedListViewModel == null) {
            Log.e(TAG, "sharedListViewModel is null in constructor!");
        } else {
            Log.i(TAG, "sharedListViewModel initialized correctly.");
        }
    }

    public void updateTodoList(List<Todo> newTodoList) {
        this.todoList = newTodoList;  // update the new todoList
        notifyDataSetChanged();  // update the UI
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void onBindViewHolder(ViewHolderAdapter.ViewHolder vh, int position) {
        // bind data to viewHolder
        Todo todo = todoList.get(position);

        ((TodoViewHolder) vh).todoText.setText(todo.text);
        ((TodoViewHolder) vh).checkBox.setOnCheckedChangeListener(null);
        ((TodoViewHolder) vh).checkBox.setChecked(todo.isChecked);

        ((TodoViewHolder) vh).checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                sharedListViewModel.moveToDone(todo);
                notifyDataSetChanged();
            }
        }));
    }

    protected ViewHolderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        return new TodoViewHolder(view);
    }

    private static class TodoViewHolder extends ViewHolderAdapter.ViewHolder {
        TextView todoText;
        CheckBox checkBox;

        public TodoViewHolder(@NonNull View view) {
            super(view);
            todoText = (TextView) view.findViewById(R.id.main_list_item_text);
            checkBox = (CheckBox) view.findViewById(R.id.main_list_item_check);
        }
    }
}
