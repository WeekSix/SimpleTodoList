package com.zz.minitodo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zz.minitodo.models.Todo;
import com.zz.minitodo.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private void setupUI(@NonNull List<Todo> todos) {
        ListView mlistView = ((ListView) findViewById(R.id.main_list_view));
        mlistView.setAdapter(new TodoListAdapter(this,todos));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Toast.makeText(this, "Fab clicked", Toast.LENGTH_SHORT).show();
        });
        setupUI(mockData());
    }

    @NonNull
    private View getListItemView(@NonNull Todo todo) {
        View view = getLayoutInflater().inflate(R.layout.main_list_item, null);
        ((TextView) view.findViewById(R.id.main_list_item_text)).setText(todo.text);
        return view;
    }

    @NonNull
    private List<Todo> mockData() {
        List<Todo> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Todo("todo " + i, DateUtils.stringToDate("2015 7 29 0:00")));
        }
        return list;
    }


}