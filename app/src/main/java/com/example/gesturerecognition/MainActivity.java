package com.example.gesturerecognition;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gesturerecognition.StateMachine.StateMachine;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // A state machine to call each of the motion functions
    private StateMachine sm;

    // Instantiate a sensorManager service to handle each sensor
    private SensorManager sensorManager;
    private Sensor linear_acceleration, gravity, gyro;

    // isOn toggles whether the user wants to read their data currently or not.
    // Triggered by a button push
    private boolean isOn = true;
    // x, y, and z are changed with every event call on the sensors, and they change dynamically
    // to do different functions based on the current event call
    // they will either be accelerometer readings, gravity readings, or gyroscope readings
    private float x = 0;
    private float y = 0;
    private float z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Typical android setup stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate the StateMachine
        sm = new StateMachine((MainActivity)this);

        // Instantiate and register each sensor variable to each of it's
        // corresponding hardware sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        linear_acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(MainActivity.this, linear_acceleration, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /**
     * onSensorChanged
     *  - Called every time one of the sensors detects an event
     *  - Either an acceleration, gravity, or gyroscope event
     *  - Use the given value to change state or call function
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        // If the sensor event was triggered by the accelerator, call checkMotion to play with it
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && isOn) {
            checkMotion(event.values[0], event.values[1], event.values[2]);
        } // Else, if it was a gravity event, check for the orientation
          // and change the state accordingly
        else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
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
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            if(x > .5 || z > .5 || y > .5 || x < -.5 || z < -.5 || y < -.5) {
                isOn = false;
            } else isOn = true;
        }
    }

    /**
     * Toggles the on/off button which determines if the user wants to be talking currently
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

    public void checkMotion (final float ax, final float ay, final float az)
    {
        if ((ax > 3 || ax < -3) && !(sm.getBeeper().isPlaying()) && ((ax > ay) && (ax > az) || (ax < ay) && (ax <az))) {
            sm.x_move();
        }
        else if ((az > 3 || az < -3) && !(sm.getBeeper().isPlaying()) && ((az > ay) && (az > ax) || (az < ay) && (az <ax))) {
            sm.z_move();
        }
        else if ((ay > 3 || ay < -3) && !(sm.getBeeper().isPlaying()) && ((ay > ax) && (ay > az) || (ay < ax) && (ay < az))) {
            sm.y_move();
        }
    }

    public void CreateNewMotion(View v) {

    }

    public TextView getLastMotion() {
        return findViewById(R.id.lastMotion);
    }

    @Override
    public void onPause(){
        super.onPause();
        super.onStop();
        finish();
    }
}
