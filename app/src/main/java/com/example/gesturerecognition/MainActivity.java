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

    private StateMachine sm;

    private SensorManager sensorManager;
    Sensor linear_accelaration;
    Sensor gravity;
    Sensor gyro;

    private boolean isOn = true;

    private float ax = 0;
    private float ay = 0;
    private float az = 0;
    private float gx = 0;
    private float gy = 0;
    private float gz = 0;
    private float gyx = 0;
    private float gyy = 0;
    private float gyz = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = new StateMachine(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        linear_accelaration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(MainActivity.this, linear_accelaration, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
        } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            gx = event.values[0];
            gy = event.values[1];
            gz = event.values[2];
        }
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyx = event.values[0];
            gyy = event.values[1];
            gyz = event.values[2];
        }


        if(gz > 8.0 && gz > gy && gz > gx) {
            sm.toBack();
        }
        else if(gz < -8.0 && gz < gy && gz < gx) {
            sm.toFront();
        }
        else if(gy > 8.0 && gy > gz && gy > gz) {
            sm.toUpright();
        }

        if(gyx > .5 || gyz > .5 || gyy > .5 || gyx < -.5 || gyz < -.5 || gyy < -.5)
        {
            sm.toOff();
        }
        checkMotion(ax, ay, az);
    }

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

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }
}
