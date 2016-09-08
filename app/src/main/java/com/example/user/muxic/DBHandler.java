package com.example.user.muxic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pawan on 14-Aug-16.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 13;
    private static final String DB_NAME = "DATABASE";
    private static final String TABLE_SONGS = "SONGS";
    private static final String DELETED_NOTES = "Deleted_Notes";

    private static final String KEY_NAME = "Name";
    private static final String KEY_TEXT = "Text";
    private static final String KEY_TIME = "Time";
    private static final String KEY_DATE = "Date";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NOTES_TABLE = "Create Table " + TABLE_SONGS + "("
                + KEY_NAME + " Text,"
                + KEY_TEXT + " Text,"
                + KEY_TIME + " Text,"
                + KEY_DATE + " Text,"
                + "Primary Key( " + KEY_DATE + ", " + KEY_TIME + "))";

        db.execSQL(CREATE_NOTES_TABLE);

        String DELETE_NOTES = "Create Table " + DELETED_NOTES + "("
                + KEY_NAME + " Text,"
                + KEY_TEXT + " Text,"
                + KEY_TIME + " Text,"
                + KEY_DATE + " Text,"
                + "Primary Key( " + KEY_DATE + ", " + KEY_TIME + "))";

        db.execSQL(DELETE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        db.execSQL("DROP TABLE IF EXISTS " + DELETED_NOTES);
        onCreate(db);
    }

    public void new_note(String Name, String Text, String Time, String Date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, Name);
        values.put(KEY_TEXT, Text);
        values.put(KEY_TIME, Time);
        values.put(KEY_DATE, Date);

        db.insert(TABLE_SONGS, null, values);
        db.close();
    }

    public void new_del_note(String Name, String Text, String Time, String Date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, Name);
        values.put(KEY_TEXT, Text);
        values.put(KEY_TIME, Time);
        values.put(KEY_DATE, Date);

        db.insert(DELETED_NOTES, null, values);
        db.close();
    }

    public void delete_note(String Date, String Time) {
        SQLiteDatabase db = this.getWritableDatabase();

        String select_query = "Select * from " + TABLE_SONGS + " where " + KEY_DATE + " = '" + Date + "' AND " + KEY_TIME + " = '" + Time + "'";
        String query = "delete from " + TABLE_SONGS + " where " + KEY_DATE + " = '" + Date + "' AND " + KEY_TIME + " = '" + Time + "'";
        Cursor csr1 = db.rawQuery(select_query,null);
        Cursor csr = db.rawQuery(query, null);

        csr1.moveToFirst();
        csr.moveToFirst();

        new_del_note(csr1.getString(0), csr1.getString(1), csr1.getString(2), csr1.getString(3));

        csr.close();
        csr1.close();
        db.close();
    }

    public void undo_delete_note(String Date, String Time) {
        SQLiteDatabase db = this.getWritableDatabase();

        String select_query = "Select * from " + DELETED_NOTES + " where " + KEY_DATE + " = '" + Date + "' AND " + KEY_TIME + " = '" + Time + "'";
        String query = "delete from " + DELETED_NOTES + " where " + KEY_DATE + " = '" + Date + "' AND " + KEY_TIME + " = '" + Time + "'";
        Cursor csr1 = db.rawQuery(select_query,null);
        Cursor csr = db.rawQuery(query, null);
        csr1.moveToFirst();
        csr.moveToFirst();

        new_note(csr1.getString(0),csr1.getString(1),csr1.getString(2),csr1.getString(3));

        csr.close();
        csr1.close();
        db.close();
    }

    public void delete_forever(String Date, String Time){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from " + DELETED_NOTES + " where " + KEY_DATE + " = '" + Date + "' AND " + KEY_TIME + " = '" + Time + "'";
        Cursor csr = db.rawQuery(query, null);
        csr.close();
        db.close();
    }

    public int get_count(){
        SQLiteDatabase db = this.getReadableDatabase();

        String count_query = "Select * from " + TABLE_SONGS;
        Cursor csr = db.rawQuery(count_query,null);
        int count = csr.getCount();

        db.close();
        csr.close();

        return count;
    }

    public int get_del_count(){
        SQLiteDatabase db = this.getReadableDatabase();

        String count_query = "Select * from " + DELETED_NOTES;
        Cursor csr = db.rawQuery(count_query,null);
        int count = csr.getCount();

        db.close();
        csr.close();

        return count;
    }

    public String[] get_single_note(int position){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] pos_note = new String[4];
        String query = "SELECT * FROM " + TABLE_SONGS;
        Cursor csr = db.rawQuery(query, null);
        csr.moveToFirst();
        int count = csr.getCount();
        int i = 0;
        while (count > 0) {
            if(i==position){
                pos_note[0] = csr.getString(0);
                pos_note[1] = csr.getString(1);
                pos_note[2] = csr.getString(2);
                pos_note[3] = csr.getString(3);
            }

            i++;
            csr.moveToNext();
            count--;
        }
        csr.close();
        db.close();
        return pos_note;
    }

    public String[] get_del_single_note(int position){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] pos_note = new String[4];
        String query = "SELECT * FROM " + DELETED_NOTES;
        Cursor csr = db.rawQuery(query, null);
        csr.moveToFirst();
        int count = csr.getCount();
        int i = 0;
        while (count > 0) {
            if(i==position){
                pos_note[0] = csr.getString(0);
                pos_note[1] = csr.getString(1);
                pos_note[2] = csr.getString(2);
                pos_note[3] = csr.getString(3);
            }

            i++;
            csr.moveToNext();
            count--;
        }
        csr.close();
        db.close();
        return pos_note;
    }

    public String[] get_name() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_SONGS;
        Cursor csr = db.rawQuery(query, null);
        csr.moveToFirst();
        int count = csr.getCount();
        String [] name = new String[count];

        int i = 0;
        while (count > 0) {
            name[i] = csr.getString(0);
            i++;
            csr.moveToNext();
            count--;
        }

        csr.close();
        db.close();
        return name;
    }

    public String[] get_del_name() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + DELETED_NOTES;
        Cursor csr = db.rawQuery(query, null);
        csr.moveToFirst();
        int count = csr.getCount();
        String [] name = new String[count];

        int i = 0;
        while (count > 0) {
            name[i] = csr.getString(0);
            i++;
            csr.moveToNext();
            count--;
        }

        csr.close();
        db.close();
        return name;
    }

    public String[] get_date() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_SONGS;
        Cursor csr = db.rawQuery(query, null);
        csr.moveToFirst();
        int count = csr.getCount();
        String [] date = new String[count];

        int i = 0;
        while (count > 0) {
            date[i] = csr.getString(2);
            i++;
            csr.moveToNext();
            count--;
        }

        csr.close();
        db.close();
        return date;
    }

    public String[] get_del_date() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + DELETED_NOTES;
        Cursor csr = db.rawQuery(query, null);
        csr.moveToFirst();
        int count = csr.getCount();
        String [] date = new String[count];

        int i = 0;
        while (count > 0) {
            date[i] = csr.getString(2);
            i++;
            csr.moveToNext();
            count--;
        }

        csr.close();
        db.close();
        return date;
    }
}
