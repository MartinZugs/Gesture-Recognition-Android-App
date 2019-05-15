package com.example.gesturerecognition;

import android.app.Activity;
import android.media.MediaPlayer;

public class Beeper {

    private static MediaPlayer mediaPlayer;

    //allows usage of getApplicationContext()
    private Activity activity;

    public Beeper(Activity a) throws Exception {
        activity = a;
    }

    public void bye() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.bye_peasants);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
            }
        });
        mediaPlayer.start();
    }

    public void hello() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.hey_yall);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
            }
        });
        mediaPlayer.start();
    }

    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        } else {
            return false;
        }
    }
}