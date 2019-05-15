package com.example.gesturerecognition.StateMachine;

public class onBackState implements State {

    private StateMachine sm;

    public onBackState(final StateMachine sm) { this.sm = sm; }

    @Override
    public void x_move() {
        sm.getBeeper().sayIDC();
    }

    @Override
    public void y_move() {
        sm.getBeeper().sayHereUGo();
    }

    @Override
    public void z_move() {
        sm.getBeeper().sayIDK();
    }
}