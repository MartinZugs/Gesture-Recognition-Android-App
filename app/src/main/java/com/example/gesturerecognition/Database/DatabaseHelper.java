package com.example.gesturerecognition.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gesturerecognition.R;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gesture_names.db";
    private static final String TABLE_NAME = "gesture_names";
    private static final String GESTURES = "gestures";
    private static final String WORDS = "words";

    private SQLiteDatabase db;
    private Context c;
    private static String[] gestureNames;
    private static String[] gestureWords;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.c = context;
        this.db = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // All we need is a table with 2 columns.  One to hold the gesture names, and one
        // to hold the words that the gestures represent
        db.execSQL("create table " + TABLE_NAME +
                "(" + GESTURES + " STRING, " + WORDS + " STRING)");

        ContentValues contentValues = new ContentValues();
        gestureNames = c.getResources().getStringArray(R.array.gestures);
        gestureWords = c.getResources().getStringArray(R.array.names);

        for(int x = 0; x < gestureNames.length; x++) {
            contentValues.put(GESTURES, gestureNames[x]);
            contentValues.put(WORDS, gestureWords[x]);
            if(db.insert(TABLE_NAME, null, contentValues) == -1) return;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /**
     * Iterate through the new names and add them to the database
     *
     * @param names - The array of names to be written over the current names in the database
     * @return boolean - Determines if the insertion succeeded or not
     */
    public boolean insertNames(String[] names) {
        gestureNames = c.getResources().getStringArray(R.array.gestures);
        gestureWords = c.getResources().getStringArray(R.array.names);

        db.delete(TABLE_NAME, null, null);

        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < names.length; i++) {
            contentValues.put(WORDS, names[i]);
            contentValues.put(GESTURES, gestureNames[i]);
            if(db.insert(TABLE_NAME, null, contentValues) == -1) return false;
        }
        return true;
    }

    public String[] getNames() {
        ArrayList<String> names = new ArrayList<>();
        Cursor cursor = this.db.rawQuery(
                "SELECT " + WORDS + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(cursor.getColumnIndex(WORDS)));
                // get  the  data into array,or class variable
            } while (cursor.moveToNext());
        }
        String[] returnNames = new String[names.size()];
        for(int i = 0; i < names.size(); i++) {
            returnNames[i] = names.get(i);
        }
        return returnNames;
    }
}
