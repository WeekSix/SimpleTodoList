package com.zz.minitodo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zz.minitodo.models.Todo;
import com.zz.minitodo.viewModels.SharedListViewModel;

import java.util.ArrayList;
import java.util.List;

public class DoneActivity extends AppCompatActivity {

    private SharedListViewModel sharedListViewModel;
    private DoneListAdapter doneListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        // initialize the view-model class.
        sharedListViewModel = SharedListViewModel.getInstance(getApplication());

        // pass the view-model to the adapter
        RecyclerView doneListView = ((RecyclerView) findViewById(R.id.done_list_view));
        doneListView.setLayoutManager(new LinearLayoutManager(this));
        doneListAdapter = (new DoneListAdapter(new ArrayList<>(), sharedListViewModel));
        doneListView.setAdapter(doneListAdapter);

        sharedListViewModel.getDoneList().observe(this, doneList -> {
            doneListAdapter.updateDoneList(doneList);
        });


        actionBarSetup();
        sharedListViewModel.printTodoList("DONE");
    }

    private void actionBarSetup() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Done Tasks");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

