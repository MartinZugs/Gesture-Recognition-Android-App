package com.example.gesturerecognition.StateMachine;

import com.example.gesturerecognition.Beeper;
import android.app.Activity;

public class StateMachine {

    private State state;

    private State ONBACK = new onBackState(this);
    private State ONFRONT = new onFrontState(this);
    private State UPRIGHT = new uprightState(this);
    private State OFF = new offState(this);

    private Beeper beeper;

    public StateMachine(Activity a) {
        try { beeper = new Beeper(a); } catch(Exception e) {}
    }

    public void toBack() { this.state = ONBACK; }
    public void toFront() { this.state = ONFRONT; }
    public void toUpright() { this.state = UPRIGHT; }
    public void toOff() { this.state = OFF; }

    public void x_move() { this.state.x_move(); }
    public void y_move() { this.state.y_move(); }
    public void z_move() { this.state.z_move(); }

    public Beeper getBeeper() { return this.beeper; }
}
