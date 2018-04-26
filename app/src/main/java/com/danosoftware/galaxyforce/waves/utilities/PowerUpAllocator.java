package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

/**
 * Responsible for allocating a list of power-ups to a wave of aliens.
 *
 * After construction, each alien must call the allocate() method and will
 * either receive a power-up or a null.
 *
 * Allocator guarantees that all power-ups will be allocated by the time
 * the last alien has requested an allocation.
 *
 * Since each alien can only be allocated 1 or 0 power-ups, if an allocator
 * is constructed with more power-ups than aliens, then an exception will
 * be thrown.
 */
public class PowerUpAllocator {

    private final Random rand = new Random();

    // stack holding unallocated power-ups
    private final Deque<PowerUpType> powerUps;

    // how many aliens remaining that will ask for power-up allocation
    private int aliensToAllocate;

    /**
     * Create an allocator responsible for allocating power-ups.
     *
     * @param powerUps - power-ups to allocate
     * @param aliens - number of aliens in wave
     */
    public PowerUpAllocator(
            List<PowerUpType> powerUps,
            int aliens) {

        // randomise list of power-ups
        Collections.shuffle(powerUps);

        // add power-ups to deque (stack)
        this.powerUps = new ArrayDeque();
        for (PowerUpType powerUp : powerUps) {
            this.powerUps.push(powerUp);
        }

        this.aliensToAllocate = aliens;

        if(powerUps.size() > aliensToAllocate) {
            throw new GalaxyForceException(
                    "Attempted to allocate " + powerUps.size() + " power-ups to only " + aliensToAllocate + " aliens."
            );
        }
    }

    /**
     * Request a power-up allocation.
     *
     * Will either receive a power-up or a null (no power-up).
     */
    public PowerUpType allocate() {
        PowerUpType allocated = null;

        if (aliensToAllocate == 0) {
            throw new GalaxyForceException(
                    "Attempted to call allocate() after all aliens have requested power-up allocation."
            );
        }

        // calculate chances of receiving a power-up based on the power-ups remaining
        // and number of aliens remaining.
        //
        // if the number of aliens remaining equals the number of power-ups remaining, then
        // the chances of receiving a power-up are 100%
        float chanceOfAllocation = (float) powerUps.size() / aliensToAllocate;
        if (rand.nextFloat() < chanceOfAllocation) {
            allocated = powerUps.pop();
        }
        aliensToAllocate--;
        return allocated;
    }
}
