package com.example.gesturerecognition.DefaultGestures;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Gesture {

    /**
     * The range is an ArrayList of 2d arrays.  The ArrayList wrapper allows the gesture to have
     * any number of data points, but each array within it will have 2 columns of 9 values.
     * the first column will be the min sensor data readings per point, and the second one will
     * be the max.
     */
    private ArrayList<Float[][]> range = new ArrayList<>();

    /**
     * Upon instantiation, we will supply the whole ass database for the given
     * @param db
     */
    public Gesture(SQLiteDatabase db) {

    }
}
