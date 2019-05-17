package com.example.gesturerecognition.StateMachine;

import android.widget.TextView;
import com.example.gesturerecognition.R;
import org.w3c.dom.Text;

public class onBackState implements State {

    private StateMachine sm;

    private TextView t;

    public onBackState(final StateMachine sm) {
        this.sm = sm;
        t = sm.getActivity().getLastMotion();
    }

    @Override
    public void x_move() {
        t.setText("I don't care!");
        sm.getBeeper().saySomething("I don't care");
    }

    @Override
    public void y_move() {
        t.setText("Here you go.");
        sm.getBeeper().saySomething("Here you go");
    }

    @Override
    public void z_move() {
        t.setText("I don't know");
        sm.getBeeper().saySomething("I don't know");
    }
}
