package com.example.gesturerecognition.Learner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String[] COLS = {  "ax", "ay", "az",
                                            "gx", "gy", "gz",
                                            "rx","ry","rz"};
    SQLiteDatabase db;

    public DatabaseHelper(Context context, String initialDB) {
        super(context, initialDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { this.db = db; }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { onCreate(db); }

    public void openDataBase(String path) {
         this.db = SQLiteDatabase.openDatabase(path, null, 0);
    }

    public void createNewDB(String name) { db = this.getWritableDatabase(); }

    public boolean insertData(Float[][] sensorData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLS[0], sensorData[0][0]);
        contentValues.put(COLS[1], sensorData[0][1]);
        contentValues.put(COLS[2], sensorData[0][2]);
        contentValues.put(COLS[3], sensorData[1][0]);
        contentValues.put(COLS[4], sensorData[1][1]);
        contentValues.put(COLS[5], sensorData[1][2]);
        contentValues.put(COLS[6], sensorData[2][0]);
        contentValues.put(COLS[7], sensorData[2][1]);
        contentValues.put(COLS[8], sensorData[2][2]);
        //long result = db.insert(MAIN_TABLE_NAME, null, contentValues);
        //if(result == -1) return false;
        //else return true;
        return true;
    }
}
