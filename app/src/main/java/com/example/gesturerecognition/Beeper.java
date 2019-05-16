package com.example.gesturerecognition;
import android.speech.tts.TextToSpeech;

import android.app.Activity;
import android.media.MediaPlayer;

import java.io.IOException;

public class Beeper {

    private static MediaPlayer mediaPlayer;
    private TextToSpeech tts;

    //allows usage of getApplicationContext()
    private MainActivity activity;

    public Beeper(MainActivity a) throws Exception {
        activity = a;
        tts = new TextToSpeech(a, (TextToSpeech.OnInitListener)a);
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

    public void sayComeHere() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.come_here);
        setListener();
        mediaPlayer.start();
    }

    public void sayHereUGo() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.here_u_go);
        setListener();
        mediaPlayer.start();
    }

    public void sayIDC() {
        mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.idc);
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