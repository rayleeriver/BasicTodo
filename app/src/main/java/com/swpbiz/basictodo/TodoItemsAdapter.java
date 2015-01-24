package com.swpbiz.basictodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {
    public TodoItemsAdapter(Context context, List<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem todoItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }

        TextView tvToTextView = (TextView) convertView.findViewById(R.id.tvTodoText);
        CheckBox cbTodoItemSelected = (CheckBox) convertView.findViewById(R.id.cbTodoItemSelected);
        cbTodoItemSelected.setChecked(todoItem.isDeleted());

        cbTodoItemSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoItem todoItem = (TodoItem) v.getTag();
                if (todoItem.isDeleted()) {
                    todoItem.setDeleted(false);
                } else {
                    todoItem.setDeleted(true);
                }
                notifyDataSetChanged();

            }
        });
        cbTodoItemSelected.setTag(todoItem);

        tvToTextView.setText(todoItem.getText());

        return convertView;
    }
}
