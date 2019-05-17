package com.example.gesturerecognition.StateMachine;

public class onFrontState implements State {

    private StateMachine sm;

    public onFrontState(final StateMachine sm) { this.sm = sm; }

    @Override
    public void x_move() {
        sm.getBeeper().saySomething("No");
    }

    @Override
    public void y_move() {
        sm.getBeeper().saySomething("That one");
    }

    @Override
    public void z_move() {
        sm.getBeeper().saySomething("Come here");
    }
}
