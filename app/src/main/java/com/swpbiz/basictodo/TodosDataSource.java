package com.swpbiz.basictodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.*;

public class TodosDataSource {

    private static final String TAG = TodosDataSource.class.getSimpleName();

    SQLiteDatabase database;
    TodoSQLiteHelper dbHelper;
    String[] columns = { TodoSQLiteHelper.COLUMN_ID, TodoSQLiteHelper.COLUMN_ITEM_TEXT, TodoSQLiteHelper.COLUMN_DELETED};

    public TodosDataSource(Context context) {
        dbHelper = new TodoSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public TodoItem createTodoItem(TodoItem todoItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoSQLiteHelper.COLUMN_ITEM_TEXT, todoItem.getText());
        contentValues.put(TodoSQLiteHelper.COLUMN_DELETED, todoItem.isDeleted()?1:0);

        long insertId = database.insert(TodoSQLiteHelper.TABLE_TODO_ITEM, null, contentValues);

        Cursor cursor = database.query(TodoSQLiteHelper.TABLE_TODO_ITEM, columns, TodoSQLiteHelper.COLUMN_ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        TodoItem newTodoItem = cursorToTodoItem(cursor);
        cursor.close();
        return newTodoItem;
    }

    public void softDeleteTodoItem(TodoItem todoItem) {
        long id = todoItem.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoSQLiteHelper.COLUMN_DELETED, 1);
        int rowsUpdated = database.update(TodoSQLiteHelper.TABLE_TODO_ITEM, contentValues, TodoSQLiteHelper.COLUMN_ID + " = " + id, null);
        Log.d(TAG, "rowsUpdated: " + rowsUpdated);
    }

    public List<TodoItem> getAllOutstandingTodoItems() {
        List<TodoItem> todoItems = new ArrayList<TodoItem>();

        Cursor cursor = database.query(TodoSQLiteHelper.TABLE_TODO_ITEM, columns, TodoSQLiteHelper.COLUMN_DELETED + " = " + 0, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TodoItem todoItem = cursorToTodoItem(cursor);
            todoItems.add(todoItem);
            cursor.moveToNext();
        }

        cursor.close();
        return todoItems;
    }

    private TodoItem cursorToTodoItem(Cursor cursor) {
        TodoItem todoItem = new TodoItem();
        todoItem.setId(cursor.getLong(0));
        todoItem.setText(cursor.getString(1));
        todoItem.setDeleted(cursor.getInt(2)==1?true:false);
        return todoItem;
    }
}
