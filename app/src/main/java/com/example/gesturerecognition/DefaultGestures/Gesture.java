package com.example.gesturerecognition.DefaultGestures;

import com.example.gesturerecognition.Learner.SensorManager;

public interface Gesture {

    public void checkInput(SensorManager si);
    public boolean trigger();
}
