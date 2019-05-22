package com.example.gesturerecognition.StateMachine;

import com.example.gesturerecognition.R;

public class uprightState implements State {

    private StateMachine sm;

    uprightState(final StateMachine sm) { this.sm = sm; }

    @Override
    public void x_move() { sm.say(sm.getContext().getString(R.string.upright_x)); }

    @Override
    public void y_move() { sm.say(sm.getContext().getString(R.string.upright_x)); }

    @Override
    public void z_move() { sm.say(sm.getContext().getString(R.string.upright_x)); }
}
