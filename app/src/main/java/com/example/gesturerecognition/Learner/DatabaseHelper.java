package com.example.gesturerecognition.Learner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * DATABASEHELPER DOCUMENTATION
 *
 * DatabaseHelper is an extension of the SQLiteOpenHelper, and as such, is intended to make
 * saving to the database easier for our specific use.  In the case of this application, we
 * will be saving all of the sensor data to the database whenever the user wants to record
 * a new gesture.  Similarly, we want it to be easy to read data from all databases while
 * listening for gestures.  This implementation of the database helper will allow us to do
 * such by doing these things:
 * - Create a new database file for every gesture
 * - Every database has a large corpus of tables, containing the data from a single sample
 *      of the gesture.
 * - Each table contains 9 columns: 3 for accelerometer readings, 3 for rotation, 3 for gravity.
 * - TODO: explain how we will implement the reading of data in an efficient way
 */

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

    /**
     * Open an existing database for reading
     *
     * @param path - The path to the database file to be read
     */
    public void openDataBase(String path) {
         this.db = SQLiteDatabase.openDatabase(path, null, 0);
    }

    /**
     * Create a new database when the user adds a new gesture
     *
     * @param name - The name of the gesture
     *             TODO: change the name of the newly created database
     */
    public void createNewDB(String name) { db = this.getWritableDatabase(); }

    /**
     * Insert a new data point to the current database
     *
     * @param sensorData - A 3x3 2D array of each sensor reading
     * @return boolean - Determines if the insertion succeeded or not
     */
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
