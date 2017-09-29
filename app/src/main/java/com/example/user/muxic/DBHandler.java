package com.example.user.muxic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aman on 14-Aug-16.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 13;
    private static final String DB_NAME = "DATABASE";

    private static final String TABLE_SONGS = "SONGS";
    private static final String TABLE_LOGIN = "LOGIN";

    private static final String KEY_NAME = "Name";
    private static final String KEY_USERNAME = "UserName";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NOTES_TABLE = "Create Table " + TABLE_SONGS + "("
                + KEY_NAME + " Text " + ")";

        db.execSQL(CREATE_NOTES_TABLE);

        String CREATE_LOGIN_TABLE = "Create Table " + TABLE_LOGIN + "("
                + KEY_USERNAME + " Text " + ")";

        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }

    public void new_note(String Name ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name);

        db.insert(TABLE_SONGS, null, values);
        Log.d("query",String.valueOf(TABLE_SONGS));

    }

    public void new_login(String name ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);

        db.insert(TABLE_LOGIN, null, values);
        Log.d("query",String.valueOf(TABLE_LOGIN));

    }

    public List<String> access_data(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> pos_note=new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SONGS;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            do {

                pos_note.add(cursor.getString(0));

            } while(cursor.moveToNext());

        }
        cursor.close();
        Log.d("note123", String.valueOf(pos_note));
        return pos_note;

    }


    public List<String> access_login(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> note=new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LOGIN;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            do {

                note.add(cursor.getString(0));

            } while(cursor.moveToNext());

        }
        cursor.close();
        Log.d("note123", String.valueOf(note));
        return note;
    }


    public void resetTable_Records() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SONGS, null, null);
    }

    public void resetTable_login() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_LOGIN, null, null);
    }
}

