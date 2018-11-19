package com.danosoftware.galaxyforce.services;

import android.view.View;

import com.danosoftware.galaxyforce.input.GameInput;
import com.danosoftware.galaxyforce.interfaces.Input;

public class Inputs {

    // private constructor
    private Inputs() {

    }

    // static reference to input
    private static Input input = null;

    // return the implementation
    public static Input getInput() {
        return input;
    }

    // static factory to create new game instance
    public static void newInput(View view, float scaleX, float scaleY) {

        if (view == null) {
            throw new IllegalArgumentException("Supplied View object can not be null.");
        }

        input = new GameInput(view, scaleX, scaleY);
    }

}
