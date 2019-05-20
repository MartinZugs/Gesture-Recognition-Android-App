package com.example.gesturerecognition.Learner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String  DATABASE_NAME = "SensorData.db";
    public static final String  MAIN_TABLE_NAME = "SensorData";

    private static final String[] COLS = {  "ax", "ay", "az",
                                            "gx", "gy", "gz",
                                            "gyx","gyy","gyz",
                                            "rx","ry","rz"};

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // We just want to hold the long string to instantiate each table without having to
        // copy the same long line a bunch of times
        String colInit = " (" + COLS[0] + " STRING, " + COLS[1] + " STRING, " + COLS[2] +
                " STRING, " + COLS[3] + " STRING, " + COLS[4] + " STRING, " + COLS[5] +
                " STRING, " + COLS[6] + " STRING, " + COLS[7] + " STRING, " + COLS[8] + " STRING,"
                + COLS[9] + " STRING,"+ COLS[10] + " STRING,"+ COLS[11] + " STRING)";

        sqLiteDatabase.execSQL("create table " + MAIN_TABLE_NAME + colInit);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

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
        contentValues.put(COLS[9], sensorData[3][0]);
        contentValues.put(COLS[10], sensorData[3][1]);
        contentValues.put(COLS[11], sensorData[3][2]);
        long result = db.insert(MAIN_TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
    }
}
