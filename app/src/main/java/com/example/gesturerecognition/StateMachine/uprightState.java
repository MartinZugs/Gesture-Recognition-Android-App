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
        sm.getBeeper().sayHello();
        t.setText("Hello!");
    }

    @Override
    public void y_move() {
        sm.getBeeper().sayYes();
        t.setText("Yes");
    }

    @Override
    public void z_move() {
        sm.getBeeper().sayBye();
        t.setText("Bye");
    }
}
