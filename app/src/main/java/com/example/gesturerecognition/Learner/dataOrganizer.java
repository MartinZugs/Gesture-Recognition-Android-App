package com.example.gesturerecognition.Learner;

import android.hardware.SensorEvent;

import com.example.gesturerecognition.DefaultGestures.GestureManager;
import com.example.gesturerecognition.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class dataOrganizer {

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
    private ArrayList<ArrayList<Float>> extras = new ArrayList<>(CAP);
    private int numExtras = 0;

    private GestureManager gm;

    public dataOrganizer(MainActivity a) {
        gm = new GestureManager(a);
        // Fill data with -1's so that we can check to see if it has been filled with
        // a value from each sensor before pushing to the gesture manager
        for(int i = 0; i < CAP; i++) {
            data.add(i, (float)-1.0);
            extras.add(i, new ArrayList<>());
        }
        System.out.println(extras.toString());
    }

    public void addAccel(SensorEvent e) { newData(e.values, 0); }
    public void addGrav(SensorEvent e)  { newData(e.values, 3); }
    public void addGyro(SensorEvent e)  { newData(e.values, 6); }

    // As new data comes in, manage it by storing extra values and flushing when main data is full
    public void newData(float[] values, int index) {
        // try to flush the data if all of the values are filled
        // If this passes, data will be empty, else it will be partially full
        tryFlush();
        // If the spots where the sensor wants to save data are free, let it save the data
        if(data.get(index) == (float)-1.0)
            for(int i = index; i < index + 2; i++) data.set(i, values[i - index]);
        else { // Else, we know that there will be other -1's in data, so
                //we can save to the extras
            for(int i = 0; i < CAP; i++) {
                // If i is within the index range of one of our extra values, add it to that spot
                if(i > index && i < index + 3) extras.get(i).add(values[i - index]);
                // Else, put a -1 there to indicate a spot in need of replacing
                else extras.get(i).add((float) -1.0);
            }
            //System.out.println("ADDED AN ITEM TO EXTRAS");
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
        for(int i = 0; i < CAP; i++) if(data.get(i) == (float)-1.0) return;
        // Else, throw the data over to the gesture manager
        //System.out.println("FLUSHED DATA: " + data.toString());
        gm.checkInput(data);
        // Check the extras for empty slots and fill those with the corresponding
        // data slots, push then repeat.  If numExtras is 0, there are no extras to push
        if(numExtras == 0) return;
        for(int i = 0; i < numExtras; i++) {
            // temp will temporarily hold each member of the currently addressed extra row
            ArrayList<Float> temp = new ArrayList<>(CAP);
            for(int j = 0; j < CAP; j++) {
                if(extras.get(j).get(i) == (float)-1.0) {
                    extras.get(j).set(i, data.get(j));
                }
                temp.set(j, extras.get(j).get(i));
            }
            // At this point, we've iterated through a single extra member, so its time to
            // push that one to the GM
            gm.checkInput(temp);
        }
        // Now, all of the extras have been pushed to the GM, so we can flush extras and data
        // and finish up nice and clean with fresh ArrayLists
        for(int i = 0; i < CAP; i++) {
            data.set(i, (float) -1.0);
            extras.set(i, new ArrayList<>());
        }
        numExtras = 0;
        //System.out.println("FLUSHED EVERYTHING");
    }
}
