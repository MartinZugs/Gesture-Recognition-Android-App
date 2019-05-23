package com.example.gesturerecognition;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gesturerecognition.StateMachine.StateMachine;

import java.io.InputStream;

import static android.text.InputType.TYPE_CLASS_TEXT;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // A state machine to call each of the motion functions
    private StateMachine sm;

    private float ax, ay, az;

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
            ax = event.values[0];
            ay = event.values[1];
            ax = event.values[2];
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
        /*if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if(x > 1 || z > 1 || y > 1 || x < -1 || z < -1 || y < -1) {
                isOn = false;
            } else if(ax < 3 || ax > - 3.0 && az < 3 || az > - 3.0 && ay < 3 || ay > - 3.0) {
                isOn = true;
            }
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void changeUpdate(View v) {
        // Change the view over to the gesture update page
        setContentView(R.layout.update_gestures);
        // Gather the static array containing all of the gesture names
        String[] gestureNames = getResources().getStringArray(R.array.gestures);
        // Get the container in which we will be placing all of the children
        LinearLayout LL = findViewById(R.id.LL);
        // Create a text input element and an image element for each gesture GUI element
        EditText et = new EditText(this);
        ImageView iv = new ImageView(this);
        // create a layout parameter to dynamically change the margin with each addition of an element
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = 0;
        // Iterate through each gesture name, create and append a new GUI child onto the parent
        for (String gestureName : gestureNames) {
            // set the new margins
            lp.setMargins(0, margin, 0, 0);
            // dynamically create the new text and image elements
            et.setText(gestureName);
            et.setWidth(600);
            et.setHeight(150);
            et.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
            et.setInputType(TYPE_CLASS_TEXT);
            et.setLayoutParams(lp);

            iv.setLayoutParams(lp);
            iv.setImageBitmap(getBitMap(gestureName));
            iv.setAdjustViewBounds(true);
            iv.setMaxWidth(100);
            iv.setMaxHeight(100);

            // Add our newly created elements onto the linear layout parent
            LL.addView(et);
            LL.addView(iv);

            // re-instantiate the elements to be iterated once more
            et = new EditText(this);
            iv = new ImageView(this);

            // Increment the margin by 160
            margin += 3;
        }
        System.out.println(gestureNames.length);
    }

    private Bitmap getBitMap(String gName) {
        InputStream rawFile = getResources().openRawResource(R.raw.wave);
        return BitmapFactory.decodeStream(rawFile);
    }

    public void changeMain(View v) {
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.OnOff);
        if(!isOn) b.setText(R.string.off);
    }



    /**
     * Check the motion of each axis when there is an acceleration event
     * If there is extraneous x movement, call x_move on the state machine
     * So on an so forth for each other axes.
     */
    public void checkMotion (final float ax, final float ay, final float az)
    {
        if ((ax > 3 || ax < -3) && !(sm.isPlaying()) && ((ax > ay) && (ax > az) || (ax < ay) && (ax <az))) {
            sm.x_move();
        }
        else if ((az > 3 || az < -3) && !(sm.isPlaying()) && ((az > ay) && (az > ax) || (az < ay) && (az <ax))) {
            sm.z_move();
        }
        else if ((ay > 3 || ay < -3) && !(sm.isPlaying()) && ((ay > ax) && (ay > az) || (ay < ax) && (ay < az))) {
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
        isOn = false;
    }
}
