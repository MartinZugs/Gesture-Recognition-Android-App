package com.example.gesturerecognition;
import android.speech.tts.TextToSpeech;

import java.io.IOException;
import android.app.Activity;

import java.util.Locale;

public class Beeper {

    private TextToSpeech tts;
    TextToSpeech t1;

    //allows usage of getApplicationContext()
    private MainActivity activity;

    public Beeper(MainActivity a) throws Exception {
        activity = a;
        tts = new TextToSpeech(a, (TextToSpeech.OnInitListener)a);
    }

    private void setListener() {
        t1= new TextToSpeech(activity.getApplicationContext(), new TextToSpeech.OnInitListener() {
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