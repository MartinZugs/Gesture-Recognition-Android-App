package com.example.gesturerecognition.DefaultGestures;

import com.example.gesturerecognition.Learner.DatabaseHelper;
import com.example.gesturerecognition.MainActivity;

import java.util.ArrayList;

public class GestureManager {

    private DatabaseHelper db;
    private ArrayList<Float[][]> values;
    private boolean isRecording = false;

    public GestureManager(MainActivity a) {
        this.db = new DatabaseHelper(a);
    }

    public void checkInput(Float[][] data, String gName) {
        // check each gesture object for patterns in the motion based on the incoming sensor input
        // or save to the database
        if(isRecording) values.add(data);
        else {
            if(!values.isEmpty())
            {
                db.insertSample(values, gName);
                values.clear();
            }
        }
    }

    public void toggleRecord() {
        if(isRecording) isRecording = false;
        else isRecording = true;
    }
}
