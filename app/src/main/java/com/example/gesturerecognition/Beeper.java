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

    public void sayBye() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.bye);
        mediaPlayer.start();
        setListener();
    }

    public void sayHello() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.hey);
        mediaPlayer.start();
        setListener();
    }

    public void sayNo() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.no);
        mediaPlayer.start();
        setListener();
    }

    public void sayThatOne() {

    }

    private void setListener() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
            }
        });
    }

    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }
}