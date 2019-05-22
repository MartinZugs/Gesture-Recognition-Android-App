package com.example.gesturerecognition.StateMachine;

import com.example.gesturerecognition.MainActivity;
import com.example.gesturerecognition.Speaker;
import android.app.Activity;
import android.content.Context;

public class StateMachine {

    private State state;

    private State ONBACK = new onBackState(this);
    private State ONFRONT = new onFrontState(this);
    private State UPRIGHT = new uprightState(this);

    private Speaker speaker;
    private MainActivity a;

    public StateMachine(MainActivity a) {
        this.a = a;
        speaker = new Speaker(this.a);
    }

    public void toBack() { this.state = ONBACK; }
    public void toFront() { this.state = ONFRONT; }
    public void toUpright() { this.state = UPRIGHT; }

    public void x_move() { this.state.x_move(); }
    public void y_move() { this.state.y_move(); }
    public void z_move() { this.state.z_move(); }

    public boolean isPlaying() { return this.speaker.isPlaying(); }
    void say(String s) { this.speaker.saySomething(s); }

    Context getContext() { return this.a.getApplicationContext(); }
}
