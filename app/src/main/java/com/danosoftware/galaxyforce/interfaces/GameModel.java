package com.danosoftware.galaxyforce.interfaces;

public interface GameModel extends Model
{
    /**
     * Start a new game.
     */
    public void play();

    /**
     * Ends a game. Supplies the wave we were on when game ended.
     */
    public void gameOver(int wave);

    /**
     * Display game options.
     */
    public void options();

    /**
     * Quit game.
     */
    public void quit();

    /**
     * Resume game. This is not the same as the model resume method that gets
     * called by the parent screen. This is a game resume method that must only
     * get called after a game pause.
     */
    public void resumeAfterPause();
}
