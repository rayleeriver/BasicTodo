package com.swpbiz.basictodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TodoSQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = TodoSQLiteHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TODO_ITEM = "todo_item";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_TEXT = "item_text";
    public static final String COLUMN_DELETED = "deleted";

    private static final String DATABASE_CREATE = "create table " + TABLE_TODO_ITEM
        + "( " + COLUMN_ID +" integer primary key autoincrement, " + COLUMN_ITEM_TEXT +" text not null, " + COLUMN_DELETED + " integer );";

    public TodoSQLiteHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: oldVersion=" + oldVersion + ", newVersion=" + newVersion);
        // do nothing
    }
}
