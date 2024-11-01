package com.zz.minitodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zz.minitodo.models.Todo;

import java.util.List;

public class TodoListConverter {
    private final Context context;
    private final List<Todo> todoList;

    public TodoListConverter(@NonNull Context context, List<Todo> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    public View getView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_list_item, null);
        Todo todo = todoList.get(position);

        ((TextView) view.findViewById(R.id.main_list_item_text)).setText(todo.text);
        return view;
    }


}
