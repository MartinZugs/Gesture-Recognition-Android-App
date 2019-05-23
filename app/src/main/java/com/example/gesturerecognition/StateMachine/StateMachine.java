package com.example.gesturerecognition.StateMachine;

import com.example.gesturerecognition.Database.DatabaseHelper;
import com.example.gesturerecognition.MainActivity;
import com.example.gesturerecognition.Speaker;
import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;

public class StateMachine {

    private State state;

    private Speaker speaker;
    private MainActivity a;
    private DatabaseHelper db;

    public StateMachine(MainActivity a, DatabaseHelper db) {
        this.a = a;
        this.db = db;
        speaker = new Speaker(this.a);
        ONBACK = new onBackState(this);
        ONFRONT = new onFrontState(this);
        UPRIGHT = new uprightState(this);
    }

    private final State ONBACK;
    private final State ONFRONT;
    private final State UPRIGHT;

    public void toBack() { this.state = ONBACK; }
    public void toFront() { this.state = ONFRONT; }
    public void toUpright() { this.state = UPRIGHT; }

    public void x_move() { this.state.x_move(); }
    public void y_move() { this.state.y_move(); }
    public void z_move() { this.state.z_move(); }

    public boolean isPlaying() { return this.speaker.isPlaying(); }
    void say(String s) { this.speaker.saySomething(s); }

    Context getContext() { return this.a.getApplicationContext(); }
    String getDataBaseInfo(String info) {
        String[] names = this.db.getNames();
        for(String name : names) if(name == info) return name;
        return null;
    }
}
