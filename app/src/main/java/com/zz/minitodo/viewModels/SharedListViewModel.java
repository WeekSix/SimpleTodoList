package com.zz.minitodo.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.zz.minitodo.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class SharedListViewModel extends AndroidViewModel {
    private static SharedListViewModel instance;
    private final MutableLiveData<List<Todo>> todoList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Todo>> doneList = new MutableLiveData<>(new ArrayList<>());
    private String TAG = "SharedListViewModel";

    public SharedListViewModel(@NonNull Application application) {
        super(application);
    }

    public static SharedListViewModel getInstance(@NonNull Application application) {
        if (instance == null) {
            instance = new SharedListViewModel(application);
        }
        return instance;
    }
    public void addToTodoList(Todo task) {
        List<Todo> todos = new ArrayList<>(todoList.getValue());
        todos.add(task);
        todoList.setValue(todos);
    }

    public void addToDoneList(Todo task) {
        List<Todo> dones = new ArrayList<>(doneList.getValue());
        dones.add(task);
        doneList.setValue(dones);
    }
    public MutableLiveData<List<Todo>> getTodoList() {
        return todoList;
    }

    public MutableLiveData<List<Todo>> getDoneList() {
        return doneList;
    }

    public void moveToDone(Todo task) {
        List<Todo> todos = todoList.getValue();
        List<Todo> dones = doneList.getValue();

        if (todos != null && dones != null) {
            todos.remove(task);
            dones.add(task);
            task.isChecked = true;
            todoList.setValue(todos);
            doneList.setValue(dones);
        }
    }

    public void moveToTodo(Todo task) {
        List<Todo> todos = todoList.getValue();
        List<Todo> dones = doneList.getValue();
        if (todos != null && dones != null) {
            dones.remove(task);
            todos.add(task);
            task.isChecked = false;
            todoList.setValue(todos);
            doneList.setValue(dones);
        }
    }

    public void printTodoList(String list) {
        List<Todo> currentList = null;
        if (list == "TODO") {
            currentList = todoList.getValue();
            Log.d(TAG, "printing todo list");
        } else if (list == "DONE") {
            currentList = doneList.getValue();
            Log.d(TAG, "printing done list");

        }
        for (int i = 0; i < currentList.size(); i++) {
            Log.d("item " + i + ":", currentList.get(i).text);
        }
    }
}
