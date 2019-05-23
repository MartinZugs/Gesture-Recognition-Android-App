package com.example.gesturerecognition.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gesturerecognition.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gesture_names.db";
    private static final String TABLE_NAME = "gesture_names";
    private static final String GESTURES = "gestures";
    private static final String WORDS = "words";

    private SQLiteDatabase db;
    private Context c;

    public DatabaseHelper(Context context, String initialDB) {
        super(context, initialDB, null, 1);
        this.c = context;
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // All we need is a table with 2 columns.  One to hold the gesture names, and one
        // to hold the words that the gestures represent
        db.execSQL("create table " + TABLE_NAME +
                "(" + GESTURES + " STRING, " + WORDS + " STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /**
     * Insert a new data point to the current database
     *
     * @param names - The array of names to be written over the current names in the database
     * @return boolean - Determines if the insertion succeeded or not
     */
    public boolean insertNames(String[] names) {
        String[] gestureNames = c.getResources().getStringArray(R.array.gestures);
        if(gestureNames.length != names.length) return false;
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < names.length; i++) {
            contentValues.put(GESTURES, gestureNames[i]);
        }
        /*
        long result = db.insert(MAIN_TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
        */
        return true;
    }

    public String[] getNames() {
        String[] names = {"s"};
        return names;
    }
}
