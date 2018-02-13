package com.danosoftware.galaxyforce.waves;

import com.danosoftware.galaxyforce.flightpath.new_refactor.Path2;
import com.danosoftware.galaxyforce.flightpath.translators.PointTranslatorChain;

/*
 * Class references a sub-wave of aliens on a path
 */
public class SubWaveProperty2
{
    private final AlienType alienType;
    private final Path2 path;
    private final int numberOfAliens;
    private final float delayBetweenAliens;
    private final float delayOffet;
    private final boolean restartImmediately;
    private final PointTranslatorChain translators;


    /**
     * Create a new alien sub-wave using a supplied path and
     * delays in seconds.
     *
     * @param alienType
     * @param path
     * @param numberOfAliens
     * @param delayBetweenAliens
     * @param delayOffet
     * @param restartImmediately
     */
    public SubWaveProperty2(
            final AlienType alienType,
            final Path2 path,
            final int numberOfAliens,
            final float delayBetweenAliens,
            final float delayOffet,
            final boolean restartImmediately)
    {
        this.alienType = alienType;
        this.path = path;
        this.numberOfAliens = numberOfAliens;
        this.delayBetweenAliens = delayBetweenAliens;
        this.delayOffet = delayOffet;
        this.restartImmediately = restartImmediately;

        // creates an empty translator chain
        this.translators = new PointTranslatorChain();
    }

    /**
     * Create a new alien sub-wave using a supplied path and
     * delays in seconds. Plus translators.
     *
     * @param alienType
     * @param path
     * @param numberOfAliens
     * @param delayBetweenAliens
     * @param delayOffet
     * @param restartImmediately
     * @param translators
     */
    public SubWaveProperty2(
            final AlienType alienType,
            final Path2 path,
            final int numberOfAliens,
            final float delayBetweenAliens,
            final float delayOffet,
            final boolean restartImmediately,
            final PointTranslatorChain translators)
    {
        this.alienType = alienType;
        this.path = path;
        this.numberOfAliens = numberOfAliens;
        this.delayBetweenAliens = delayBetweenAliens;
        this.delayOffet = delayOffet;
        this.restartImmediately = restartImmediately;
        this.translators = translators;
    }

    public AlienType getAlienType() {
        return alienType;
    }

    public Path2 getPath() {
        return path;
    }

    public int getNumberOfAliens() {
        return numberOfAliens;
    }

    public float getDelayBetweenAliens() {
        return delayBetweenAliens;
    }

    public float getDelayOffet() {
        return delayOffet;
    }

    public boolean isRestartImmediately() {
        return restartImmediately;
    }

    public PointTranslatorChain getTranslators() {
        return translators;
    }
}
