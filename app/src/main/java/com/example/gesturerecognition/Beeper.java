package com.example.gesturerecognition;

import android.app.Activity;
import android.net.rtp.AudioStream;

import android.media.MediaPlayer;

import com.example.gesturerecognition.R;


public class Beeper {

    private static MediaPlayer mediaPlayer;

    // current status of clip
    boolean status = false;

    //allows usage of getApplicationContext()
    private Activity activity;

    public Beeper(Activity a) throws Exception {
        activity = a;
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.beep_sound);
    }

    public void on() throws Exception {
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        status = true;
    }

    public void off() throws Exception {
        mediaPlayer.stop();
        mediaPlayer.prepare();
        status = false;
    }
}