package com.example.gesturerecognition.StateMachine;

import android.widget.TextView;

public class uprightState implements State {

    private StateMachine sm;

    private TextView t;

    public uprightState(final StateMachine sm) {
        this.sm = sm;
        t = sm.getActivity().getLastMotion();
    }

    @Override
    public void x_move() {
        t.setText("Hello!");
        sm.getBeeper().saySomething("Hello");
    }

    @Override
    public void y_move() {
        t.setText("Yes");
        sm.getBeeper().saySomething("Yes");
    }

    @Override
    public void z_move() {
        t.setText("Bye");
        sm.getBeeper().saySomething("Goodbye");
    }
}
