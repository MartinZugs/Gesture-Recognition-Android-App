package com.jkotheimer.TalkMotion;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.jkotheimer.TalkMotion.R;
import com.jkotheimer.TalkMotion.Database.DatabaseHelper;
import java.util.ArrayList;

import static android.text.InputType.TYPE_CLASS_TEXT;

class TransitionHandler {
    
    TransitionHandler(MainActivity a, DatabaseHelper db) {
        this.a = a;
        this.db = db;
    }
    private final MainActivity a;
    private final DatabaseHelper db;
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    void changeGesture() {

        // Change the view over to the gesture update page
        a.setContentView(R.layout.update_gestures);

        // Gather the static array containing all of the gesture names
        String[] gestureNames = db.getNames();
        String[] gests = db.getGestures();

        // Get the container in which we will be placing all of the children
        LinearLayout LL = a.findViewById(R.id.LL);

        // create a new container to hold the text box and the picture in horizontally
        LinearLayout container = new LinearLayout(a);
        container.setOrientation(LinearLayout.HORIZONTAL);

        // Create a text input element and an image element for each gesture GUI element
        EditText et;
        GifWebView gv;

        // create a layout parameter to dynamically change the margin with each addition of an element
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = 0;

        // Iterate through each gesture name, create and append a new GUI child onto the parent
        for (int i = 0; i < gestureNames.length; i++) {
            // dynamically create the new text and image elements
            lp.setMargins(0,margin,0,0);
            et = new EditText(a);
            et.setText(gestureNames[i]);
            et.setWidth(650);
            et.setHeight(150);
            et.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
            et.setInputType(TYPE_CLASS_TEXT);
            et.setLayoutParams(lp);


            lp.setMargins(10, margin,0,0);
            gv = new GifWebView(a, "file:///android_asset/" + gests[i] + ".gif");
            gv.setLayoutParams(lp);
            gv.setPadding(-20,0,0,0);

            // Add our newly created elements onto the linear layout parent then add that to
            // the xml layout
            container.addView(et);
            container.addView(gv);
            LL.addView(container);

            // re-instantiate the elements to be iterated once more
            container = new LinearLayout(a);

            margin += 5;
        }
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
