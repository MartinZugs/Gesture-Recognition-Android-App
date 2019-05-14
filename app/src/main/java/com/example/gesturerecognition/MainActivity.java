package com.example.gesturerecognition;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private Beeper beeper;
    protected void setBeeper(final Beeper beeper) { this.beeper = beeper; }

    private accelerometerObject model;
    protected void setModel(final accelerometerObject model) {
        this.model = model;
    }

    private SensorManager sensorManager;
    Sensor accelerometer;
    Sensor gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.setModel(new accelerometerObject());
        try {
            this.setBeeper(new Beeper(this));
        } catch (Exception e) {
            Log.d(TAG, "Shucks that didn't work :/");
        }
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void updateView(final float x, final float y, final float z) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvx = (TextView) findViewById(R.id.x_value);
            final TextView tvy = (TextView) findViewById(R.id.y_value);
            final TextView tvz = (TextView) findViewById(R.id.z_value);
            String str_x = "X: " + Float.toString(x);
            String str_y = "Y: " + Float.toString(y);
            String str_z = "Z: " + Float.toString(z);
            tvx.setText(str_x);
            tvy.setText(str_y);
            tvz.setText(str_z);
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float ax = event.values[0];
        final float ay = event.values[1];
        final float az = event.values[2];
        for(int x = 0; x < event.values.length; x++)
        {System.out.println(event.values[x]);}
        updateView(ax, ay, az);
        if (ax > 10.0 || ay > 10.0 || az > 10.0) {
            try {
                beeper.on();
            } catch (Exception e) {
                Log.d(TAG, "Shucks that didn't work :/");
            }
        }
        if (ax < 10.0 && ay < 10.0 && az < 10.0) {
            System.out.println(ax + ay + az);

            try {
                beeper.off();
            } catch (Exception e) {
                Log.d(TAG, "Shucks that didn't work :/");
            }
        }
    }
}
