package com.swpbiz.basictodo;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;
import java.util.*;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TodosDataSource todosDataSource;

    private TodoItemsAdapter todosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todosDataSource = new TodosDataSource(this);

        try {
            todosDataSource.open();

        } catch (SQLException e) {
            Log.e(TAG, "failed to open database");
        }
        List<TodoItem> todoItems = todosDataSource.getAllOutstandingTodoItems();
        todosAdapter = new TodoItemsAdapter(this, todoItems);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, new TodoListFragment());
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_trash:
                delete_selectedTodoItems();
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void delete_selectedTodoItems() {
        List<TodoItem> itemsToBeDeleted = new ArrayList<TodoItem>();

        int currentItemsCount = todosAdapter.getCount();
        for (int i = 0; i < currentItemsCount; i++) {
            TodoItem currentItem = todosAdapter.getItem(i);
            if (currentItem != null && currentItem.isDeleted()) {
                todosDataSource.softDeleteTodoItem(currentItem);
                itemsToBeDeleted.add(currentItem);
            }
        }
        for (int i = 0; i < itemsToBeDeleted.size(); i++) {
            todosAdapter.remove(itemsToBeDeleted.get(i));
        }

        todosAdapter.notifyDataSetChanged();
    }

    public TodosDataSource getTodosDataSource() {
        return todosDataSource;
    }

    public TodoItemsAdapter getTodosAdapter() {
        return todosAdapter;
    }

}
