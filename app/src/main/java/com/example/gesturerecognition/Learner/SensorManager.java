package com.example.gesturerecognition.Learner;

import android.hardware.SensorEvent;

import com.example.gesturerecognition.DefaultGestures.GestureManager;

import java.util.ArrayList;
import java.util.Arrays;

public class SensorManager {

    /**
     * data will hold 9 Floats. Three members from each sensor.  When it is full, it will
     * be pushed to the gesture manager, which will in turn decide whether to save the data
     * to the database or check it against other gestures
     */
    private final int CAP = 9;
    private ArrayList<Float> data = new ArrayList<>(CAP);

    /**
     * extras is an array of ArrayLists.  This will allow us to add the data from an extraneous
     * sensor event to the first member of each array, and the next extraneous member to the next
     * index of each arraylist. Thus, we will have a structure as follows:
     *                          ArrayList #
     *  array:      0    1   2   3   4   5   6   7   8
     *  1st extra   ax  ay  az  gx  gy  gz  gyx gyy gyz
     *  2nd extra   ax  ay  az  gx  gy  gz  gyx gyy gyz
     *  ... extra   ax  ay  az  gx  gy  gz  gyx gyy gyz
     *
     *  So then next time the data ArrayList fills up, we are able to push it to the
     *  GestureManager, then push all the extras along with it.
     *
     *  The issue is that an extraneous sensor input will create one extra accelerometer
     *  data field, but no other gravity or gyroscope readings, so what we have to do then,
     *  is take those data members from the next pushed data ArrayList, and fill them into
     *  the empty spots in the extras data member.  This will duplicate some sensor input
     *  (which isn't at all a problem because it will just average out), but also allow us
     *  to have an even, constant amount of data collected for each sensor in the long run.
     */
    private ArrayList<Float>[] extras = new ArrayList[9];
    private int numExtras = 0;

    private GestureManager gm = new GestureManager();

    public SensorManager() {
        // Fill data with -1's so that we can check to see if it has been filled with
        // a value from each sensor before pushing to the gesture manager
        for(int i = 0; i < CAP; i++) data.set(i, Float.valueOf(-1));
    }

    public void addAccel(SensorEvent e) { newData(e.values, 0); }
    public void addGrav(SensorEvent e)  { newData(e.values, 3); }
    public void addGyro(SensorEvent e)  { newData(e.values, 6); }

    public void newData(float[] values, int index) {
        // try to flush the data if all of the values are filled
        // If this passes, data will be empty, else it will be partially full
        tryFlush();
        // If the spots where the sensor wants to save data are free, let it save the data
        if(data.get(index) == -1)
            for(int i = index; i < index + 3; i++) data.set(i, values[i - index]);
        else { // Else, we know that there will be other -1's in data, so
                //we can save to the extras
            for(int i = 0; i < CAP; i++) {
                // If i is within the index range of one of our extra values, add it to that spot
                if(i > index && i < index + 3) extras[i].set(numExtras, values[i - index]);
                // Else, put a -1 there to indicate a spot in need of replacing
                else extras[i].set(numExtras, Float.valueOf(-1));
            }
            numExtras++;
        }
    }

    /**
     * Try to flush the data out of our data list.  If all of the data members are filled
     * with something (none of them are -1), then we can push to the GestureManager
     * and if we push to the GM, we need to check extras for data and fill the rest of the data
     * with the current opposing members that weren't a part of that extras instance
     */
    public void tryFlush() {
        // If there are any -1's, return
        for(int i = 0; i < CAP; i++) if(data.get(i) == -1) return;
        // Else, throw the data over to the gesture manager
        gm.checkInput(data);
        // Check the extras for empty slots and fill those with the corresponding
        // data slots, push then repeat.  If numExtras is 0, there are no extras to push
        for(int i = 0; i < numExtras; i++) {
            // temp will temporarily hold each member of the currently addressed extra row
            ArrayList<Float> temp = new ArrayList<>(CAP);
            for(int j = 0; j < CAP; j++) {
                if(extras[j].get(i) == -1) {
                    extras[j].add(i, data.get(j));
                }
                temp.set(j, extras[j].get(i));
            }
            // At this point, we've iterated through a single extra member, so its time to
            // push that one to the GM
            gm.checkInput(temp);
        }
        // Now, all of the extras have been pushed to the GM, so we can flush extras and data
        // and finish up nice and clean with fresh ArrayLists
        Arrays.fill(extras, new ArrayList<Float>());
        for(int i = 0; i < CAP; i++) data.set(i, Float.valueOf(-1));
        numExtras = 0;
    }
}
