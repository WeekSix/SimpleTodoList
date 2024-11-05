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

public class DoneListAdapter extends ViewHolderAdapter {
    private final Context context;
    private final SharedListViewModel sharedListViewModel;
    private final String TAG = "DoneListAdapter";
    private List<Todo> doneList;

    public DoneListAdapter(@NonNull Context context, List<Todo> data, SharedListViewModel sharedListViewModel) {
        this.context = context;
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
    public int getCount() {
        return doneList.size();
    }

    @Override
    public Object getItem(int position) {
        return doneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected ViewHolderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        return new DoneListViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolderAdapter.ViewHolder vh, int position) {
        DoneListViewHolder holder = (DoneListViewHolder) vh;
        holder.todoText.setText(doneList.get(position).text);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(doneList.get(position).isChecked);

        holder.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (!isChecked) {
                sharedListViewModel.moveToTodo(doneList.get(position));
                notifyDataSetChanged();
            }
        }));
    }

    private static class DoneListViewHolder extends ViewHolderAdapter.ViewHolder {
        TextView todoText;
        CheckBox checkBox;

        public DoneListViewHolder(View view) {
            super(view);

            todoText = view.findViewById(R.id.main_list_item_text);
            checkBox = view.findViewById(R.id.main_list_item_check);
        }
    }
}
