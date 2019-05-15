package com.example.gesturerecognition;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Beeper beeper;
    protected void setBeeper(final Beeper beeper) { this.beeper = beeper; }

    private SensorManager sensorManager;
    Sensor linear_accelaration;
    Sensor gravity;
    float ax = 0;
    float ay = 0;
    float az = 0;
    float gx = 0;
    float gy = 0;
    float gz = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        try {
            this.setBeeper(new Beeper(this));
        } catch (Exception e) {}

        linear_accelaration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(MainActivity.this, linear_accelaration, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void updateView(final float x, final float y, final float z, final float gx, final float gy, final float gz) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvx = (TextView) findViewById(R.id.x_value);
            final TextView tvy = (TextView) findViewById(R.id.y_value);
            final TextView tvz = (TextView) findViewById(R.id.z_value);
            final TextView tvgx = (TextView) findViewById(R.id.gx_value);
            final TextView tvgy = (TextView) findViewById(R.id.gy_value);
            final TextView tvgz = (TextView) findViewById(R.id.gz_value);
            String str_x = "X: " + Float.toString(x);
            String str_y = "Y: " + Float.toString(y);
            String str_z = "Z: " + Float.toString(z);
            String str_gx = "GX: " + Float.toString(gx);
            String str_gy = "GY: " + Float.toString(gy);
            String str_gz = "GZ: " + Float.toString(gz);
            tvx.setText(str_x);
            tvy.setText(str_y);
            tvz.setText(str_z);
            tvgx.setText(str_gx);
            tvgy.setText(str_gy);
            tvgz.setText(str_gz);
        });
    }

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

        // TODO: Create several general orientation state objects (upright, upside_down, on_back,
        //       on_front, on_right, on_left (possibly more complex than that)), and listen for
        //       certain motions from each state to create different sounds.

        updateView(ax, ay, az, gx, gy, gz);

        /*if ((ax > 3.0 || ax < -3.0) && !(beeper.isPlaying())) {
            try {
                beeper.hello();
            } catch (Exception e) {}
        }
        else if ((az > 2.0 || az < -2.0) && !(beeper.isPlaying())) {
            try{
                beeper.bye();
            }   catch (Exception e) {}
        }*/
    }
}
