package com.example.gesturerecognition.DefaultGestures;

import android.database.sqlite.SQLiteDatabase;

import com.example.gesturerecognition.Learner.SensorManager;
import java.util.ArrayList;

public class GestureManager {

    private SQLiteDatabase db;

    private boolean isRecording = false;

    public GestureManager(SQLiteDatabase db) {
        this.db = db;
        setDefaults();
    }

    private void setDefaults() {
        // set all of the default gesture objects
    }

    public void checkInput(ArrayList<Float> data) {
        // check each gesture object for patterns in the motion based on the incoming sensor input
        // or save to the database

    }
}
