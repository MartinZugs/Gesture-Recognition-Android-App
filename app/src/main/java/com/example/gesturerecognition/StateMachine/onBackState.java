package com.example.gesturerecognition.StateMachine;

import com.example.gesturerecognition.R;

public class onBackState implements State {

    private StateMachine sm;

    onBackState(final StateMachine sm) { this.sm = sm; }

    @Override
    public void x_move() { sm.say(sm.getContext().getString(R.string.onback_x)); }

    @Override
    public void y_move() {
        sm.say(sm.getContext().getString(R.string.onback_y));
    }

    @Override
    public void z_move() {
        sm.say(sm.getContext().getString(R.string.onback_z));
    }
}
