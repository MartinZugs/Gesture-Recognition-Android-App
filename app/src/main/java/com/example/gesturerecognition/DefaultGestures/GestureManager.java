package com.example.gesturerecognition.DefaultGestures;

import com.example.gesturerecognition.Learner.SensorManager;
import java.util.ArrayList;

public class GestureManager {

    private ArrayList<Gesture> gestures = new ArrayList<>();
    private ArrayList<ArrayList<SensorManager>> H =  new ArrayList<>();

    private boolean isRecording = false;

    public GestureManager() {
        setDefaults();
        gestures.add(new Hello(H));
    }

    private void setDefaults() {
        // set all of the default gesture objects
    }

    public void checkInput(ArrayList<Float> data) {
        // check each gesture object for patterns in the motion based on the incoming sensor input
        // or save to the database
    }
}
