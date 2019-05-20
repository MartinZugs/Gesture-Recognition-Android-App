package com.example.gesturerecognition.DefaultGestures;

import com.example.gesturerecognition.Learner.DatabaseHelper;
import com.example.gesturerecognition.MainActivity;

import java.util.ArrayList;

public class GestureManager {

    private DatabaseHelper db;

    private boolean isRecording = false;

    public GestureManager(MainActivity a) {
        this.db = new DatabaseHelper(a);
    }

    public void checkInput(Float[][] data) {
        // check each gesture object for patterns in the motion based on the incoming sensor input
        // or save to the database
        if(isRecording) db.insertData(data);
        else {
            // check for gestures to make sounds
        }
    }

    public void toggleRecord() {
        if(isRecording) isRecording = false;
        else isRecording = true;
    }
}
