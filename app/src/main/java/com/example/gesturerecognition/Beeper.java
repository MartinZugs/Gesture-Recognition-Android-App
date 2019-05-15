package com.example.gesturerecognition;

import android.app.Activity;
import android.net.rtp.AudioStream;

import android.media.MediaPlayer;

import com.example.gesturerecognition.R;


public class Beeper {

    private static MediaPlayer mediaPlayer;

    //allows usage of getApplicationContext()
    private Activity activity;

    public Beeper(Activity a) throws Exception {
        activity = a;
    }

    public void bye() {
        if (mediaPlayer.isPlaying()) return;
        else {
            mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.bye_peasants);
            mediaPlayer.start();
        }
    }

    public void hello() {
        if (mediaPlayer.isPlaying()) return;
        else {
            mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.hey_yall);
            mediaPlayer.start();
        }
    }

    public boolean isPlaying() { return mediaPlayer.isPlaying(); }

    /*public void off() throws Exception {
        mediaPlayer.stop();
        mediaPlayer.prepare();
    }*/
}