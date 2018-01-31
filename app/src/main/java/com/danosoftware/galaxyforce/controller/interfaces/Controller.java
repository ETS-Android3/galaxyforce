package com.danosoftware.galaxyforce.controller.interfaces;

public interface Controller
{
    /**
     * update model using any set-up controllers.
     */
    public void update(float deltaTime);

    /**
     * add a touch controller to be monitored.
     * 
     * @param touchController
     */
    public void addTouchController(TouchController touchController);

    /**
     * Remove all existing touch controllers
     */
    public void clearTouchControllers();

    /**
     * reset controller on game re-start
     */
    public void reset();

}
