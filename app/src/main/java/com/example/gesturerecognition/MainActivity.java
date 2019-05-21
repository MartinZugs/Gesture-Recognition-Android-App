package com.example.gesturerecognition;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gesturerecognition.DefaultGestures.GestureManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /**
     * The database helper creates a database and allows us to create .db files
     * under which we can save the sensor readings of the device when the user
     * wants to create a new gesture
     */
    private GestureManager gm;
    private Float[][] dataContainer = new Float[4][3];
    private Beeper b;

    /**
     * isOn toggles whether the user wants to read their data currently or not.
     * Triggered by a button push
     */
    private boolean isOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Typical android setup stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Instantiate the Gesture Manager
        gm = new GestureManager(this);

        // Instantiate Beeper
        try {
            b = new Beeper(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a sensorManager and sensor variable
        android.hardware.SensorManager sensorManager;
        Sensor linear_acceleration, gravity, gyro, rotVec;

        // Instantiate and register each sensor variable to each of it's
        // corresponding hardware sensors
        sensorManager = (android.hardware.SensorManager) getSystemService(Context.SENSOR_SERVICE);
        linear_acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        rotVec = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        sensorManager.registerListener(MainActivity.this,
                linear_acceleration, android.hardware.SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(MainActivity.this,
                rotVec, android.hardware.SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(MainActivity.this,
                gravity, android.hardware.SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * This needs to be here to prevent the default parent function from running and
     * making weird things happen in the background of the app.  We don't want this.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /**
     * onSensorChanged
     *  - Called every time one of the sensors detects an event
     *  - Either an acceleration, gravity, or gyroscope event
     *  - Use the given value to change state or call function based on event type
     *
     * @param event - The currently calling sensor event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // If the sensor event was triggered by the accelerator
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION ) {
            dataContainer[0][0] = event.values[0];
            dataContainer[0][1] = event.values[1];
            dataContainer[0][2] = event.values[2];
        } // Else, if it was a gravity event
        else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            dataContainer[1][0] = event.values[0];
            dataContainer[1][1] = event.values[1];
            dataContainer[1][2] = event.values[2];
        } // Else if the sensor was a Rotational Vector reading
        else if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            dataContainer[3][0] = event.values[0];
            dataContainer[3][1] = event.values[1];
            dataContainer[3][2] = event.values[2];
        }


        // Check if any members of the dataContainer are empty
        for(int i = 0; i < dataContainer.length; i++) {
            for(int j = 0; j < dataContainer[i].length; j++) {
                // If there is nothing there, return
                if(dataContainer[i][j] == null) return;
            }
        }


        //TODO Make the string dynamically change

        // Push to the database
        gm.checkInput(dataContainer, "Hello");

    }

    /**
     * Toggles the on/off button which determines if the user wants to be talking currently
     *
     * @param v - View from button event
     */
    public void toggleOnOff(View v) {
        Button b = (Button)findViewById(R.id.OnOff);
        if(isOn) {
            isOn = false;
            b.setText("Off");
        }
        else {
            isOn = true;
            b.setText("On");
        }
    }

    public void changeScreen(View v)
    {
        setContentView(R.layout.activity_learn);
    }

    public void toActivityMain(View v)
    {
        setContentView(R.layout.activity_main);
    }

    /**
     * Pops up a text box that asks what the user wants their new gesture to say.
     * Then, it shows a button that has a number above it
     *   -  The number represents the number of times the user must reiterate the gesture until
     *      the computer has gathered enough data and learned it.
     *   -  The button indicates that the user wants to start recording their gesture
     *   -  We then record the gesture for 2 seconds
     * @param v - view from button event
     */
    public void CreateNewMotion(View v) throws InterruptedException {
        isOn = false;

        //waits for 1 seconds and says go
        long cTime = System.currentTimeMillis();
        long tTime = cTime + 1000;

        while(cTime < tTime) {
            cTime = System.currentTimeMillis();
        }

        b.saySomething("Go");

        gm.toggleRecord();

        cTime = System.currentTimeMillis();

        tTime = cTime + 1000;

        while(cTime < tTime) {
            cTime = System.currentTimeMillis();
        }

        gm.toggleRecord();

        /**
         * TODO: switch to a new page
         *      - *TEXT BOX* requesting the word that this new gesture says
         *      - *NUMBER* indicating how many gestures are left to be made
         *      - *BUTTON* to record the gesture
         *      - *LITTLE BUTTONS* showing the presence of the last gestures, with an
         *          x on it, indicating that when you click on it, you will delete that
         *          gesture recording.  This then increments the number of gestures left
         *          to be recorded
         *      - A *CANCEL BUTTON*
         *      - Once the last recording is finished, show a little *"DONE" BUTTON* in the
         *          top right corner of the screen.  When this is pressed, we save everything
         *          and go back to the home page where gestures can be made
         */
    }

    @Override
    public void onStart() {
        isOn = true;
        super.onStart();
    }

    @Override
    public void onPause(){
        isOn = false;
        super.onPause();
    }
}
