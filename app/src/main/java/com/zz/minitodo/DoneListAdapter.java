package com.zz.minitodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zz.minitodo.models.Todo;
import com.zz.minitodo.viewModels.SharedListViewModel;

import java.util.List;

public class DoneListAdapter extends RecyclerView.Adapter {
    private final SharedListViewModel sharedListViewModel;
    private final String TAG = "DoneListAdapter";
    private List<Todo> doneList;

    public DoneListAdapter(List<Todo> data, SharedListViewModel sharedListViewModel) {
        this.doneList = data;
        this.sharedListViewModel = sharedListViewModel;

        if (sharedListViewModel == null) {
            Log.e("DoneListAdapter", "sharedListViewModel is null in constructor!");
        } else {
            Log.i("DoneListAdapter", "sharedListViewModel initialized correctly.");
        }
    }

    public void updateDoneList(List<Todo> newDoneList) {
        this.doneList = newDoneList;
        // update the UI
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return doneList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListViewHolder viewHolder = (ListViewHolder) holder;
        viewHolder.todoText.setText(doneList.get(position).text);
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        viewHolder.checkBox.setChecked(doneList.get(position).isChecked);

        viewHolder.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (!isChecked) {
                sharedListViewModel.moveToTodo(doneList.get(position));
                notifyDataSetChanged();
            }
        }));
    }
}
