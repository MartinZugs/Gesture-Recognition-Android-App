package com.example.gesturerecognition;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.gesturerecognition.StateMachine.StateMachine;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // A state machine to call each of the motion functions
    private StateMachine sm;

    // isOn toggles whether the user wants to read their data currently or not.
    // Triggered by a button push
    private boolean isOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Typical android setup stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the StateMachine
        sm = new StateMachine(this);

        // Instantiate and register each sensor variable to each of it's
        // corresponding hardware sensors
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor linear_acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(MainActivity.this, linear_acceleration, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * This function needs to be here to prevent the super method from doing something weird
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /**
     * onSensorChanged
     *  - Called every time one of the sensors detects an event
     *  - Either an acceleration, gravity, or gyroscope event
     *  - Use the given value to change state or call function
     * @param event - the event object containing the sensor data
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        // If the sensor event was triggered by the accelerator, call checkMotion to play with it
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && isOn) {
            checkMotion(event.values[0], event.values[1], event.values[2]);
        } // Else, if it was a gravity event, check for the orientation
        // and change the state accordingly
        else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
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
        /*else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            if(x > .5 || z > .5 || y > .5 || x < -.5 || z < -.5 || y < -.5) {
                isOn = false;
            } else isOn = true;
        }*/
    }

    /**
     * Toggles the on/off button which determines if the user wants to be talking currently
     * @param v - the view of the event call from the button press
     */
    public void toggleOnOff(View v) {
        Button b = findViewById(R.id.OnOff);
        if(isOn) {
            isOn = false;
            b.setText(R.string.off);
        }
        else {
            isOn = true;
            b.setText(R.string.on);
        }
    }

    // Starting the display code
    public void changeLogo(View v) {
        setContentView(R.layout.logo);
    }

    public void changeSettings(View v) {
        setContentView(R.layout.settings);
    }

    public void changeUpdate(View v) {
        setContentView(R.layout.update_gestures);
        XmlResourceParser page = this.getResources().getLayout(R.layout.update_gestures);
        String[] gestureNames = getResources().getStringArray(R.array.gestures);
        
        System.out.println(gestureNames.length);
    }

    public void changeMain(View v) {
        setContentView(R.layout.activity_main);
    }



    /**
     * Check the motion of each axis when there is an acceleration event
     * If there is extraneous x movement, call x_move on the state machine
     * So on an so forth for each other axes.
     */
    public void checkMotion (final float ax, final float ay, final float az)
    {
        if ((ax > 2.5 || ax < -2.5) && !(sm.isPlaying()) && ((ax > ay) && (ax > az) || (ax < ay) && (ax <az))) {
            sm.x_move();
        }
        else if ((az > 2.5 || az < -2.5) && !(sm.isPlaying()) && ((az > ay) && (az > ax) || (az < ay) && (az <ax))) {
            sm.z_move();
        }
        else if ((ay > 2.5 || ay < -2.5) && !(sm.isPlaying()) && ((ay > ax) && (ay > az) || (ay < ax) && (ay < az))) {
            sm.y_move();
        }
    }

    /**
     * When the app is paused, stop all functionality so the beeper doesn't make any random noises
     * in the background
     */
    @Override
    public void onPause(){
        super.onPause();
        super.onStop();
        finish();
    }
}
