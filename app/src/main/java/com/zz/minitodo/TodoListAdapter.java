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

public class TodoListAdapter extends BaseAdapter {
    private final Context context;
    private List<Todo> todoList;
    private final SharedListViewModel sharedListViewModel;
    private final String TAG = "TodoListAdapter";

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

    // ListView will call getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            Log.i(TAG, "position " + position);
            convertView = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false);

            mViewHolder = new ViewHolder();
            // we only find once
            mViewHolder.todoText = (TextView) convertView.findViewById(R.id.main_list_item_text);
            mViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.main_list_item_check);
            // cache the view tag
            convertView.setTag(mViewHolder);
        } else {
            // convertView is not null, meaning it already cached view holder
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        Todo todo = todoList.get(position);
        mViewHolder.todoText.setText(todo.text);

        mViewHolder.checkBox.setOnCheckedChangeListener(null);
        mViewHolder.checkBox.setChecked(todo.isChecked);

        removeCheckedItem(convertView,todo);
        return convertView;
    }

    private void removeCheckedItem(View view, Todo todo) {
        CheckBox checkBox = view.findViewById(R.id.main_list_item_check);
        checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                sharedListViewModel.moveToDone(todo);
                notifyDataSetChanged();
            }
        }));
    }
    private static class ViewHolder {
        TextView todoText;
        CheckBox checkBox;
    }
}
