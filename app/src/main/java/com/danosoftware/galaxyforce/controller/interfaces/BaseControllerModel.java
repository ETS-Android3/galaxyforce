package com.danosoftware.galaxyforce.controller.interfaces;

import com.danosoftware.galaxyforce.sprites.game.interfaces.Sprite;

import java.util.List;

public interface BaseControllerModel
{

    /**
     * Gets the current x weighting of the model.
     * 
     * @return x weighting
     */
    public float getWeightingX();

    /**
     * Gets the current y weighting of the model.
     * 
     * @return y weighting
     */
    public float getWeightingY();

    /**
     * get a list of sprites associated with the controller model.
     * 
     * @return list of sprites
     */
    public List<Sprite> getSprites();

    /**
     * reset the controller
     */
    public void reset();

    /**
     * Update the controller model
     * 
     * @param deltaTime
     */
    public void update(float deltaTime);

}
