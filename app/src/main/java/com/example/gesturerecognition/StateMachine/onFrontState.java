package com.example.gesturerecognition.StateMachine;

import android.content.res.Resources;

import com.example.gesturerecognition.R;

public class onFrontState implements State {

    onFrontState(final StateMachine sm) {
        this.sm = sm;
        resources = sm.getContext().getResources();
    }

    private final StateMachine sm;
    private final Resources resources;

    @Override
    public void x_move() {
        sm.say(resources.getStringArray(R.array.gestures)[resources.getInteger(R.integer.onfront_x)]);
    }

    @Override
    public void y_move() {
        sm.say(resources.getStringArray(R.array.gestures)[resources.getInteger(R.integer.onfront_y)]);
    }

    @Override
    public void z_move() {
        sm.say(resources.getStringArray(R.array.gestures)[resources.getInteger(R.integer.onfront_z)]);
    }
}
