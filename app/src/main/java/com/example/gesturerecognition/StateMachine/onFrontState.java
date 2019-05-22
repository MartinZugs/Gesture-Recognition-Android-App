package com.example.gesturerecognition.StateMachine;

import com.example.gesturerecognition.R;

public class onFrontState implements State {

    private StateMachine sm;

    onFrontState(final StateMachine sm) { this.sm = sm; }

    @Override
    public void x_move() {
        sm.say(sm.getContext().getString(R.string.onfront_x));
    }

    @Override
    public void y_move() {
        sm.say(sm.getContext().getString(R.string.onfront_y));
    }

    @Override
    public void z_move() {
        sm.say(sm.getContext().getString(R.string.onfront_z));
    }
}
