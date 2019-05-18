package com.example.gesturerecognition;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gesturerecognition.Learner.DatabaseHelper;
import com.example.gesturerecognition.Learner.SensorManager;
import com.example.gesturerecognition.StateMachine.StateMachine;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /**
     * The database helper creates a database and allows us to create .db files
     * under which we can save the sensor readings of the device when the user
     * wants to create a new gesture
     */
    private DatabaseHelper myDB;

    /**
     * The SensorManager will be used to organize all of our sensor readings
     * in a clean fashion and delegate them to a GestureManager for proper usage
     */
    private SensorManager manager;

    /**
     * A state machine to call each of the motion functions based on which orientation
     * state the phone is currently in
     */
    private StateMachine sm;

    /**
     * Instantiate a sensorManager service to handle each sensor
     * - linear_acceleration is used to rad each lateral movement of the device
     * - gravity is used to determine the current orientation state of the device
     * - gyro is used to determine if the device is currently rotating, and the axis
     *   on which it is rotating
      */
    private android.hardware.SensorManager sensorManager;
    private Sensor linear_acceleration, gravity, gyro;

    /**
     * isOn toggles whether the user wants to read their data currently or not.
     * Triggered by a button push
     */
    private boolean isOn = true;

    /**
     * x, y, and z are changed with every event call on the sensors, and they change dynamically
     * to do different functions based on the current event call.
     * They will either be accelerometer readings, gravity readings, or gyroscope readings
     */
    private float x = 0;
    private float y = 0;
    private float z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Typical android setup stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the StateMachine, database helper, and Sensor Manager
        sm = new StateMachine(this);
        myDB = new DatabaseHelper(this);
        manager = new SensorManager();

        // Instantiate and register each sensor variable to each of it's
        // corresponding hardware sensors
        sensorManager = (android.hardware.SensorManager) getSystemService(Context.SENSOR_SERVICE);
        linear_acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(MainActivity.this,
                linear_acceleration, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this,
                gravity, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this,
                gyro, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
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
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // If the sensor event was triggered by the accelerator, call checkMotion to play with it
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && isOn) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            checkMotion(x, y, z);
            manager.addAccel(event);
        } // Else, if it was a gravity event, check for the orientation
          // and change the state accordingly
        else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            manager.addGrav(event);
            if(z > 8.0 && z > y && z > x) {
                sm.toBack();
            }
            else if(z < -8.0 && z < y && z < x) {
                sm.toFront();
            }
            else if(y > 8.0 && y > z && y > z) {
                sm.toUpright();
            }
        } // Else if the sensor was a gyroscope reading, change the isOn variable accordingly
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            manager.addGyro(event);
        }

    }

    /**
     * Toggles the on/off button which determines if the user wants to be talking currently
     *
     * @param v
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

    /**
     * checkMotion checks the acceleration values for default gestures, and calls the
     * StateMachine's generic x/y/z-move functions, which delegate to the current state
     *
     * @param ax
     * @param ay
     * @param az
     */
    public void checkMotion (final float ax, final float ay, final float az)
    {
        if ((ax > 2.5 || ax < -2.5) && !(sm.getBeeper().isPlaying()) &&
                ((ax > ay) && (ax > az) || (ax < ay) && (ax <az))) {
            sm.x_move();
        }
        else if ((az > 2.5 || az < -2.5) && !(sm.getBeeper().isPlaying()) &&
                ((az > ay) && (az > ax) || (az < ay) && (az <ax))) {
            sm.z_move();
        }
        else if ((ay > 2.5 || ay < -2.5) && !(sm.getBeeper().isPlaying()) &&
                ((ay > ax) && (ay > az) || (ay < ax) && (ay < az))) {
            sm.y_move();
        }
    }

    /**
     * Pops up a text box that asks what the user wants their new gesture to say.
     * Then, it shows a button that has a number above it
     *   -  The number represents the number of times the user must reiterate the gesture until
     *      the computer has gathered enough data and learned it.
     *   -  The button indicates that the user wants to start recording their gesture
     *   -  We then record the gesture for 2 seconds
     * @param v
     */
    public void CreateNewMotion(View v) {
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

    public TextView getLastMotion() {
        return (TextView) findViewById(R.id.lastMotion);
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

    @Override
    public void onStop() {
        super.onStop();
    }

}
