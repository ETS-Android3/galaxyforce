package com.danosoftware.galaxyforce.waves;

import com.danosoftware.galaxyforce.enumerations.Direction;

/*
 * Class references a sub-wave of aliens without a path
 */
public class SubWavePropertyNoPath
{
    private AlienType alienType = null;
    private boolean xRandom;
    private boolean yRandom;
    private int xStart;
    private int yStart;
    private int numberOfAliens;
    private float delayBetweenAliens;
    private float delayOffet;
    private boolean restartImmediately;
    private Direction direction;

    /**
     * Properties to help create a new alien sub-wave using a supplied positions
     * (random or specified) and delays in seconds.
     * 
     * @param alienType
     * @param xRandom
     *            - use random x position
     * @param yRandom
     *            - use random y position
     * @param xStart
     *            - use fixed x start position
     * @param yStart
     *            - use fixed y start position
     * @param numberOfAliens
     * @param delayBetweenAliens
     * @param delayOffet
     * @param restartImmediately
     * @param direction
     */
    public SubWavePropertyNoPath(AlienType alienType, boolean xRandom, boolean yRandom, int xStart, int yStart, int numberOfAliens,
            float delayBetweenAliens, float delayOffet, boolean restartImmediately, Direction direction)
    {
        this.alienType = alienType;
        this.xRandom = xRandom;
        this.yRandom = yRandom;
        this.xStart = xStart;
        this.yStart = yStart;
        this.numberOfAliens = numberOfAliens;
        this.delayBetweenAliens = delayBetweenAliens;
        this.delayOffet = delayOffet;
        this.restartImmediately = restartImmediately;
        this.direction = direction;
    }

    public AlienType getAlienType()
    {
        return alienType;
    }

    public boolean isxRandom()
    {
        return xRandom;
    }

    public boolean isyRandom()
    {
        return yRandom;
    }

    public int getxStart()
    {
        return xStart;
    }

    public int getyStart()
    {
        return yStart;
    }

    public int getNumberOfAliens()
    {
        return numberOfAliens;
    }

    public float getDelayBetweenAliens()
    {
        return delayBetweenAliens;
    }

    public float getDelayOffet()
    {
        return delayOffet;
    }

    public boolean isRestartImmediately()
    {
        return restartImmediately;
    }

    public Direction getDirection()
    {
        return direction;
    }
}
