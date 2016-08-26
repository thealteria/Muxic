package com.example.user.muxic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aman on 8/25/2016.
 */
public abstract class DBManager extends SQLiteOpenHelper{
    private static final String DB_FILE_NAME= "test.db";
    private String tableName;


    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
