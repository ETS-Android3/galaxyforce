package com.danosoftware.galaxyforce.model.screens;

public interface SelectLevelModel
{

    public void setLevel(int level);

    public void changeZone(int newZone);

    /**
     * Screen currently being swiped and should be moved by provided offset.
     * 
     * @param xDelta
     */
    public void swipeUpdate(float xDelta);

    /**
     * Screen currently being swiped and should be moved by provided offset.
     * 
     * @param xOffset
     */
    public void swipeStart();

    /**
     * Screen swipe has finished at provided offset. Screen should be updated to
     * nearest appropriate x position.
     * 
     * @param xDelta
     */
    public void swipeFinish(float xDelta);
}
