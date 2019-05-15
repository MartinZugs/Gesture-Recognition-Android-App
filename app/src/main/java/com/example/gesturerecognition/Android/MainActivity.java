package com.example.gesturerecognition.Android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.gesturerecognition.Beeper;
import com.example.gesturerecognition.R;
import com.example.gesturerecognition.accelerometerObject;

import java.util.concurrent.TimeUnit;

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
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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
        // TODO: Create several general orientation state objects (upright, upside_down, on_back,
        //       on_front, on_right, on_left (possibly more complex than that)), and listen for
        //       certain motions from each state to create different sounds.
        updateView(ax, ay, az);
        if (ax > 2.0 || ax < -2.0) {
            try {
                beeper.hello();

            } catch (Exception e) {
                Log.d(TAG, "Shucks that didn't work :/");
            }
        }
        else if (az > 2.0 || az < -2.0)
        {
            try{
                beeper.bye();

            }   catch (Exception e)
            {
                Log.d(TAG,"Shucks that didn't work :/");
            }
        }
        else //(ax < 11.0 && ay < 11.0 && az < 11.0) {
            try {
                beeper.off();
            } catch (Exception e) {
                Log.d(TAG, "Shucks that didn't work :/");
            }
        }
    }
