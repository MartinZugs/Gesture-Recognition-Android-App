package com.example.gesturerecognition;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.setModel(new accelerometerObject());
        try {
            this.setBeeper(new Beeper(this));
        } catch (Exception e) {
            Log.d(TAG, "Shucks that didn't work :/");
        }
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer listener");
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
        final float x = event.values[0];
        final float y = event.values[1];
        final float z = event.values[2];
        updateView(x, y, z);
        if (x > 12.0 || y > 12.0 || z > 12.0) {
            try {
                beeper.play();
            } catch (Exception e) {
                Log.d(TAG, "Shucks that didn't work :/");
            }
        }
    }
}
