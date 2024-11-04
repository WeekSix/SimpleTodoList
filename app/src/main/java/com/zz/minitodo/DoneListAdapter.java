package com.zz.minitodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zz.minitodo.models.Todo;
import com.zz.minitodo.viewModels.SharedListViewModel;

import java.util.List;

public class DoneListAdapter extends BaseAdapter {
    private final Context context;
    private List<Todo> doneList;
    private final SharedListViewModel sharedListViewModel;
    private final String TAG = "DoneListAdapter";

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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false);

            mViewHolder = new ViewHolder();
            mViewHolder.todoText = (TextView) convertView.findViewById(R.id.main_list_item_text);
            mViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.main_list_item_check);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        Todo task = doneList.get(position);
        mViewHolder.todoText.setText(task.text);

        mViewHolder.checkBox.setOnCheckedChangeListener(null);
        mViewHolder.checkBox.setChecked(task.isChecked);


        mViewHolder.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (!isChecked) {
                sharedListViewModel.moveToTodo(task);
                notifyDataSetChanged();
            }
        }));

        return convertView;
    }

    private void removeCheckedItem(View view, Todo task) {
        CheckBox checkBox = view.findViewById(R.id.main_list_item_check);
        checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (!isChecked) {
                sharedListViewModel.moveToTodo(task);
                notifyDataSetChanged();
            }
        }));
    }
    private static class ViewHolder {
        TextView todoText;
        CheckBox checkBox;
    }
}
