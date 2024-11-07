package com.zz.minitodo;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewHolder extends RecyclerView.ViewHolder {
    TextView todoText;
    CheckBox checkBox;

    public ListViewHolder(@NonNull View view) {
        super(view);
        todoText = (TextView) view.findViewById(R.id.main_list_item_text);
        checkBox = (CheckBox) view.findViewById(R.id.main_list_item_check);
    }
}