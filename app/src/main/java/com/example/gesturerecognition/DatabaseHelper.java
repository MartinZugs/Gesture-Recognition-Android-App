package com.example.gesturerecognition;

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

    public static final String  COL_1 = "ax";
    public static final String  COL_2 = "ay";
    public static final String  COL_3 = "az";
    public static final String  COL_4 = "gx";
    public static final String  COL_5 = "gy";
    public static final String  COL_6 = "gz";
    public static final String  COL_7 = "gyx";
    public static final String  COL_8 = "gyy";
    public static final String  COL_9 = "gyz";

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + LOW_TABLE_NAME + " (" + COL_1 + " STRING, " + COL_2 + " STRING, " + COL_3 + " STRING, " + COL_4 + " STRING, " + COL_5 + " STRING, " + COL_6 + " STRING, " + COL_7 + " STRING, " + COL_8 + " STRING, " + COL_9 + " STRING)");
        sqLiteDatabase.execSQL("create table " + HIGH_TABLE_NAME + " (" + COL_1 + " STRING, " + COL_2 + " STRING, " + COL_3 + " STRING, " + COL_4 + " STRING, " + COL_5 + " STRING, " + COL_6 + " STRING, " + COL_7 + " STRING, " + COL_8 + " STRING, " + COL_9 + " STRING)");
        sqLiteDatabase.execSQL("create table " + TEMP_TABLE_NAME + " (" + COL_1 + " STRING, " + COL_2 + " STRING, " + COL_3 + " STRING, " + COL_4 + " STRING, " + COL_5 + " STRING, " + COL_6 + " STRING, " + COL_7 + " STRING, " + COL_8 + " STRING, " + COL_9 + " STRING)");
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
        contentValues.put(COL_1, x);
        contentValues.put(COL_2, y);
        contentValues.put(COL_3, z);
        long result = db.insert(TEMP_TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
    }

    public boolean insertGravData(Float x, Float y, Float z){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, x);
        contentValues.put(COL_5, y);
        contentValues.put(COL_6, z);
        long result = db.insert(TEMP_TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
    }

    public boolean insertGyroData(Float x, Float y, Float z){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_7, x);
        contentValues.put(COL_8, y);
        contentValues.put(COL_9, z);
        long result = db.insert(TEMP_TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
    }
}
