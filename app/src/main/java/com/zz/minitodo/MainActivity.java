package com.zz.minitodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zz.minitodo.models.Todo;
import com.zz.minitodo.viewModels.SharedListViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private SharedListViewModel sharedListViewModel;
    private TodoListAdapter todoListAdapter;

    private void setupUI() {
        sharedListViewModel = SharedListViewModel.getInstance(getApplication());

        ListView todoListView = ((ListView) findViewById(R.id.main_list_view));
        todoListAdapter = new TodoListAdapter(this, new ArrayList<>(), sharedListViewModel);
        todoListView.setAdapter(todoListAdapter);

        sharedListViewModel.getTodoList().observe(this, todoList -> {
            todoListAdapter.updateTodoList(todoList);
        });

//        mockData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> showAddTaskDialog());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setupUI();

    }

    private void showAddTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);

        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnAdd = dialogView.findViewById(R.id.btn_add);
        EditText input = dialogView.findViewById(R.id.edit_task_name);


        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.TransparentDialog)
                .setView(dialogView)
                .create();

        btnCancel.setOnClickListener(v -> alertDialog.dismiss());
        btnAdd.setOnClickListener(v -> {
            String taskText = input.getText().toString().trim();
            if (!taskText.isEmpty()) {
                Todo newTodo = new Todo(taskText);
                sharedListViewModel.addToTodoList(newTodo);
            }
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedListViewModel.printTodoList("TODO");
        sharedListViewModel.printTodoList("DONE");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_done_list) {
            // Show item done list
            showItemDone();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showItemDone() {
        Intent intent = new Intent(MainActivity.this, DoneActivity.class);
        startActivity(intent);
    }


    private void mockData() {
        for (int i = 0; i < 15; i++) {
            Todo todo = new Todo("todo " + i);
            sharedListViewModel.addToTodoList(todo);
        }
    }

}