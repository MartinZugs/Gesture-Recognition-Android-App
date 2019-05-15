package com.example.gesturerecognition;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gesturerecognition.StateMachine.StateMachine;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private StateMachine sm;

    private SensorManager sensorManager;
    Sensor linear_accelaration;
    Sensor gravity;
    Sensor gyro;

    float ax = 0;
    float ay = 0;
    float az = 0;
    float gx = 0;
    float gy = 0;
    float gz = 0;
    float gyx = 0;
    float gyy = 0;
    float gyz = 0;

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

    public void updateView(final float x, final float y, final float z, final float gx, final float gy, final float gz, final float gyx, final float gyy, final float gyz) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvx = (TextView) findViewById(R.id.x_value);
            final TextView tvy = (TextView) findViewById(R.id.y_value);
            final TextView tvz = (TextView) findViewById(R.id.z_value);
            final TextView tvgx = (TextView) findViewById(R.id.gx_value);
            final TextView tvgy = (TextView) findViewById(R.id.gy_value);
            final TextView tvgz = (TextView) findViewById(R.id.gz_value);
            final TextView tvgyx = (TextView) findViewById(R.id.gyx_value);
            final TextView tvgyy = (TextView) findViewById(R.id.gyy_value);
            final TextView tvgyz = (TextView) findViewById(R.id.gyz_value);
            String str_x = "X: " + Float.toString(x);
            String str_y = "Y: " + Float.toString(y);
            String str_z = "Z: " + Float.toString(z);
            String str_gx = "GX: " + Float.toString(gx);
            String str_gy = "GY: " + Float.toString(gy);
            String str_gz = "GZ: " + Float.toString(gz);
            String str_gyx = "GYX: " + Float.toString(gyx);
            String str_gyy = "GYY: " + Float.toString(gyy);
            String str_gyz = "GYZ: " + Float.toString(gyz);
            tvx.setText(str_x);
            tvy.setText(str_y);
            tvz.setText(str_z);
            tvgx.setText(str_gx);
            tvgy.setText(str_gy);
            tvgz.setText(str_gz);
            tvgyx.setText(str_gyx);
            tvgyy.setText(str_gyy);
            tvgyz.setText(str_gyz);
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
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyx = event.values[0];
            gyy = event.values[1];
            gyz = event.values[2];
        }

        updateView(ax, ay, az, gx, gy, gz, gyx, gyy, gyz);

        /*if(gyx > 2 || gyy > 2 || gyz > 2)
        {
            return;
        }*/

        if(gz > 8.0 || gz < -8.0)
        {
            sm.toBack();
        }
        else if(gy > 8.0 || gy < -8.0)
        {
            sm.toUpright();
        }
        checkMotion(ax, ay, az);
    }

    public void checkMotion (final float ax, final float ay, final float az)
    {
        if ((ax > 4.0 || ax < -4.0) && !(sm.getBeeper().isPlaying())) {
            sm.x_move();
        }
        else if ((az > 4.0 || az < -4.0) && !(sm.getBeeper().isPlaying())) {
            sm.z_move();
        }
        else if ((ay > 4.0 || ay < -4.0) && !(sm.getBeeper().isPlaying())) {
            sm.y_move();
        }
    }
}
