package com.example.gesturerecognition.StateMachine;

import com.example.gesturerecognition.Beeper;
import com.example.gesturerecognition.MainActivity;

import android.app.Activity;

public class StateMachine {

    private MainActivity a;

    private State state;

    private State ONBACK;
    private State ONFRONT;
    private State UPRIGHT;

    private Beeper beeper;

    public StateMachine(MainActivity a) {
        this.a = a;
        try { beeper = new Beeper(this.a); } catch(Exception e) {}
        ONBACK = new onBackState(this);
        ONFRONT = new onFrontState(this);
        UPRIGHT = new uprightState(this);
        this.state = ONFRONT;
    }

    public void toBack() { this.state = ONBACK; }
    public void toFront() { this.state = ONFRONT; }
    public void toUpright() { this.state = UPRIGHT; }

    public void x_move() { this.state.x_move(); }
    public void y_move() { this.state.y_move(); }
    public void z_move() { this.state.z_move(); }

    public Beeper getBeeper() { return this.beeper; }
    public MainActivity getActivity() { return this.a; }
}
