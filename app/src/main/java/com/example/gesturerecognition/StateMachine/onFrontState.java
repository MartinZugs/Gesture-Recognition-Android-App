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
        t.setText("No");
        sm.getBeeper().saySomething("No");
    }

    @Override
    public void y_move() {
        t.setText("That one");
        sm.getBeeper().saySomething("That one");
    }

    @Override
    public void z_move() {
        t.setText("Come here");
        sm.getBeeper().saySomething("Come here");
    }
}
