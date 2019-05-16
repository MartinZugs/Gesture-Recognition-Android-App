package com.example.gesturerecognition.StateMachine;

import android.widget.TextView;

public class onFrontState implements State {

    private StateMachine sm;

    private TextView t;

    public onFrontState(final StateMachine sm) {
        this.sm = sm;
        t = sm.getActivity().getLastMotion();
    }

    @Override
    public void x_move() {
        sm.getBeeper().sayNo();
        t.setText("No");
    }

    @Override
    public void y_move() {
        sm.getBeeper().sayThatOne();
        t.setText("That one");
    }

    @Override
    public void z_move() {
        sm.getBeeper().sayComeHere();
        t.setText("Come here");
    }
}
