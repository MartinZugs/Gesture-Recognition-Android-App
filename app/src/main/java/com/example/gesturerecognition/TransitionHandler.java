package com.example.gesturerecognition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.Constraints;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gesturerecognition.Database.DatabaseHelper;

import java.io.InputStream;
import java.util.ArrayList;

import static android.text.InputType.TYPE_CLASS_TEXT;

public class TransitionHandler {
    
    TransitionHandler(MainActivity a, DatabaseHelper db) {
        this.a = a;
        this.db = db;
    }
    private final MainActivity a;
    private final DatabaseHelper db;
    
    void changeGesture() {

        // Change the view over to the gesture update page
        a.setContentView(R.layout.update_gestures);

        // Gather the static array containing all of the gesture names
        String[] gestureNames = db.getNames();

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
            // dynamically create the new text and image elements
            lp.setMargins(0,margin,0,0);
            et.setText(gestureName);
            et.setId(a.getResources().getIdentifier(gestureName, "id", a.getPackageName()));
            et.setWidth(650);
            et.setHeight(150);
            et.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
            et.setInputType(TYPE_CLASS_TEXT);
            et.setLayoutParams(lp);

            lp.setMargins(10, margin,0,0);
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
        LinearLayout ll = a.findViewById(R.id.LL);
        ArrayList<EditText> editTexts = new ArrayList<>();
        for( int i = 0; i < ll.getChildCount(); i++ ) {
            if (ll.getChildAt(i) instanceof LinearLayout){
                LinearLayout child = (LinearLayout) ll.getChildAt(i);
                for( int j = 0; j < child.getChildCount(); j++) {
                    if(child.getChildAt(j) instanceof EditText) {
                        editTexts.add((EditText) child.getChildAt(j));
                    }
                }
            }
        }

        String[] names = new String[editTexts.size()];
        for(int i = 0; i < editTexts.size(); i++) {
            names[i] = editTexts.get(i).getText().toString();
            System.out.println(names[i]);
        }
        db.insertNames(names);
        a.setContentView(R.layout.settings);
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
