package com.example.gesturerecognition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;

import static android.text.InputType.TYPE_CLASS_TEXT;

public class TransitionHandler {
    
    TransitionHandler(MainActivity a) {
        this.a = a;
    }
    private final MainActivity a;
    
    void changeGesture() {

        // Change the view over to the gesture update page
        a.setContentView(R.layout.update_gestures);

        // Gather the static array containing all of the gesture names
        String[] gestureNames = a.getResources().getStringArray(R.array.gestures);

        // Get the container in which we will be placing all of the children
        LinearLayout LL = a.findViewById(R.id.LL);

        // create a new container to hold the text box and the picture in horizontally
        LinearLayout container = new LinearLayout(a);
        container.setOrientation(LinearLayout.HORIZONTAL);

        // Create a text input element and an image element for each gesture GUI element
        EditText et = new EditText(a);
        ImageView iv = new ImageView(a);

        // create a layout parameter to dynamically change the margin with each addition of an element
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = 0;

        // Iterate through each gesture name, create and append a new GUI child onto the parent
        for (String gestureName : gestureNames) {
            // set the new margins
            lp.setMargins(0, margin, 0, 0);
            // dynamically create the new text and image elements
            et.setText(gestureName);
            et.setId(a.getResources().getIdentifier(gestureName, "id", a.getPackageName()));
            et.setWidth(650);
            et.setHeight(150);
            et.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
            et.setInputType(TYPE_CLASS_TEXT);
            et.setLayoutParams(lp);

            lp.setMargins(10, margin, 0, 0);
            iv.setLayoutParams(lp);
            iv.setImageBitmap(getBitMap(gestureName));
            iv.setAdjustViewBounds(true);
            iv.setMaxWidth(100);
            iv.setMaxHeight(100);

            // Add our newly created elements onto the linear layout parent then add that to
            // the xml layout
            container.addView(et);
            container.addView(iv);
            LL.addView(container);

            // re-instantiate the elements to be iterated once more
            et = new EditText(a);
            iv = new ImageView(a);
            container = new LinearLayout(a);

            margin += 5;
        }
    }

    private Bitmap getBitMap(String gName) {
        // TODO: import all of the cartoon gesture samples, and grab them based on the name
        //       called in this function
        InputStream rawFile = a.getResources().openRawResource(R.raw.wave);
        return BitmapFactory.decodeStream(rawFile);
    }

    void saveGesture() {

    }

    // Starting the display code
    void changeLogo(View v) {
        a.setContentView(R.layout.logo);
    }

    void changeSettings(View v) {
        a.setContentView(R.layout.settings);
    }

    void changeMain(View v) {
        a.setContentView(R.layout.activity_main);
    }
}
