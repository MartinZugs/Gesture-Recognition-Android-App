package com.example.gesturerecognition.DefaultGestures;

import com.example.gesturerecognition.Learner.DatabaseHelper;
import com.example.gesturerecognition.MainActivity;

import java.util.ArrayList;

public class GestureManager {

    private DatabaseHelper db;

    private boolean isRecording = false;

    public GestureManager(MainActivity a) {
        this.db = new DatabaseHelper(a);
        setDefaults();
    }

    private void setDefaults() {
        // set all of the default gesture objects
    }

    public void checkInput(ArrayList<Float> data) {
        // check each gesture object for patterns in the motion based on the incoming sensor input
        // or save to the database
        db.insertData(data);
    }
}
