package com.example.gesturerecognition.StateMachine;

public class uprightState implements State {

    private StateMachine sm;

    public uprightState(final StateMachine sm) { this.sm = sm; }

    @Override
    public void x_move() {
        sm.getBeeper().sayHello();
    }

    @Override
    public void y_move() {
        //sm.getBeeper().sayTrain();
    }

    @Override
    public void z_move() {
        sm.getBeeper().sayBye();
    }
}
