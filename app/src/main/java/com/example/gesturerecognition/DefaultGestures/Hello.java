package com.example.gesturerecognition.DefaultGestures;

import com.example.gesturerecognition.Learner.SensorManager;

import java.util.ArrayList;

public class Hello implements Gesture {

    ArrayList<ArrayList<SensorManager>> range = new ArrayList<>();
    ArrayList<SensorManager> input = new ArrayList<>();

    public Hello(ArrayList<ArrayList<SensorManager>> r) {
        this.range = r;
    }

    @Override
    public void checkInput(SensorManager si) {

    }

    @Override
    public boolean trigger() {
        return false;
    }
}
