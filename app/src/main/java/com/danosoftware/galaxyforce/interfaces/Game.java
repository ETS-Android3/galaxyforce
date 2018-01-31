package com.danosoftware.galaxyforce.interfaces;

import android.content.Context;

import com.danosoftware.galaxyforce.billing.service.IBillingService;
import com.danosoftware.galaxyforce.view.GLGraphics;

public interface Game
{
    public void start();

    public void resume();

    public void pause();

    public void dispose();

    public void draw(float deltaTime);

    public void update(float deltaTime);

    public GLGraphics getGlGraphics();

    public Screen getScreen();

    public FileIO getFileIO();

    public void setScreen(Screen gameScreen);

    /**
     * Change to a screen that will eventually return back to this screen. A
     * subsequent call to screenReturn() will return back to the the previous
     * screen.
     * 
     * @param gameScreen
     */
    public void setReturningScreen(Screen gameScreen);

    /**
     * Change back to previous screen. That is the screen that we were on when
     * setReturningScreen() was called.
     */
    public void screenReturn();

    /**
     * return true if back button is handled internally and we don't want
     * application to exit.
     * 
     * @return true if back button handled internally
     */
    public boolean handleBackButton();

    public Audio getAudio();

    public Context getContext();

    public IBillingService getBillingService();
}