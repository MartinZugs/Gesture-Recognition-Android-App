package com.example.gesturerecognition.StateMachine;

public class onBackState implements State {

    private StateMachine sm;

    public onBackState(final StateMachine sm) { this.sm = sm; }

    @Override
    public void x_move() {
        sm.getBeeper().sayNo();
    }

    @Override
    public void y_move() {
        sm.getBeeper().sayThatOne();
    }

    @Override
    public void z_move() {
        sm.getBeeper().sayHere();
    }
}
