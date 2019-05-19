package com.example.gesturerecognition.Learner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String  DATABASE_NAME = "SensorData.db";
    public static final String  LOW_TABLE_NAME = "LowSensorData";
    public static final String  HIGH_TABLE_NAME = "HighSensorData";
    public static final String  TEMP_TABLE_NAME = "TempSensorData";

    private static final int CAP = 9;

    private static final String[] COLS = {  "ax", "ay", "az",
                                            "gx", "gy", "gz",
                                            "gyx","gyy","gyz" };

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // We just want to hold the long string to instantiate each table without having to
        // copy the same long line a bunch of times
        String colInit = " (" + COLS[1] + " STRING, " + COLS[2] + " STRING, " + COLS[3] +
                " STRING, " + COLS[4] + " STRING, " + COLS[5] + " STRING, " + COLS[6] +
                " STRING, " + COLS[7] + " STRING, " + COLS[8] + " STRING, " + COLS[9] + " STRING)";

        sqLiteDatabase.execSQL("create table " + LOW_TABLE_NAME + colInit);
        sqLiteDatabase.execSQL("create table " + HIGH_TABLE_NAME + colInit);
        sqLiteDatabase.execSQL("create table " + TEMP_TABLE_NAME + colInit);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LOW_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HIGH_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TEMP_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertAccData(Float x, Float y, Float z){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLS[1], x);
        contentValues.put(COLS[2], y);
        contentValues.put(COLS[3], z);
        long result = db.insert(TEMP_TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
    }

    public boolean insertGravData(Float x, Float y, Float z){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLS[4], x);
        contentValues.put(COLS[5], y);
        contentValues.put(COLS[6], z);
        long result = db.insert(TEMP_TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
    }

    public boolean insertGyroData(Float x, Float y, Float z){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLS[7], x);
        contentValues.put(COLS[8], y);
        contentValues.put(COLS[9], z);
        long result = db.insert(TEMP_TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
    }
}
