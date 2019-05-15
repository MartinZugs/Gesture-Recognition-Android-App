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
        setListener();
        mediaPlayer.start();
    }

    public void sayHello() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.hey);
        setListener();
        mediaPlayer.start();
    }

    public void sayNo() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.no);
        setListener();
        mediaPlayer.start();
    }

    public void sayThatOne() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.that_one);
        setListener();
        mediaPlayer.start();
    }

    public void sayIDK() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.idk);
        setListener();
        mediaPlayer.start();
    }

    public void sayYes() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.yes);
        setListener();
        mediaPlayer.start();
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