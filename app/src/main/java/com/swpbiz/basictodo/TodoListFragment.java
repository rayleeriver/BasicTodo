package com.swpbiz.basictodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListFragment extends Fragment {

    public TodoListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button addButton = (Button) rootView.findViewById(R.id.btAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) v.getRootView().findViewById(R.id.etItemText);
                if (editText != null && editText.getText() != null && editText.getText().length() > 0) {
                    TodoItem todoItem = new TodoItem();
                    todoItem.setText(editText.getText().toString());
                    TodoItem dbTodoItem = ((MainActivity) getActivity()).getTodosDataSource().createTodoItem(todoItem);
                    ((MainActivity) getActivity()).getTodosAdapter().add(dbTodoItem);
                    editText.setText(null);
                }
                ((MainActivity) getActivity()).getTodosAdapter().notifyDataSetChanged();
            }
        });

        ListView listView = (ListView) rootView.findViewById(R.id.todoListView);
        listView.setAdapter(((MainActivity) getActivity()).getTodosAdapter());
        return rootView;
    }
}