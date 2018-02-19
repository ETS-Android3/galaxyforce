package com.danosoftware.galaxyforce.input;

import android.view.View.OnTouchListener;

import com.danosoftware.galaxyforce.interfaces.Input.TouchEvent;

import java.util.List;

public interface TouchHandler extends OnTouchListener
{
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
