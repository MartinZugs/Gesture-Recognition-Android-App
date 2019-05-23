package com.example.gesturerecognition.StateMachine;

import android.content.res.Resources;

import com.example.gesturerecognition.R;

public class uprightState implements State {

    uprightState(final StateMachine sm) {
        this.sm = sm;
        resources = sm.getContext().getResources();
    }

    private final StateMachine sm;
    private final Resources resources;

    @Override
    public void x_move() {
        sm.say("upright_x");
    }

    @Override
    public void y_move() {
        sm.say("upright_y");
    }

    @Override
    public void z_move() {
        sm.say("upright_z");
    }
}
