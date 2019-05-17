package com.example.gesturerecognition;
import android.speech.tts.TextToSpeech;
import android.app.Activity;
import android.media.MediaPlayer;

import java.util.Locale;

public class Beeper {

    private static MediaPlayer mediaPlayer;
    TextToSpeech t1;

    //allows usage of getApplicationContext()
    private Activity activity;

    public Beeper(Activity a) throws Exception {
        activity = a;
        t1= new TextToSpeech(a.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    public void saySomething(String word) {
        t1.speak(word, TextToSpeech.QUEUE_FLUSH, null);
    }

    public boolean isPlaying() {
        if (t1.isSpeaking() == true) {
            return true;
        }
        return false;
    }
}