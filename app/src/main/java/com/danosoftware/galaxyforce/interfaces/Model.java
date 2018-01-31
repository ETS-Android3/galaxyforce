package com.danosoftware.galaxyforce.interfaces;

import java.util.List;

import com.danosoftware.galaxyforce.sprites.game.interfaces.Sprite;
import com.danosoftware.galaxyforce.text.Text;

/**
 * Generic interface for any models containing the logic behind any screens.
 * 
 * @author Danny
 * 
 */
public interface Model
{
    // public void setState(ModelState modelState);

    // public ModelState getState();

    /**
     * Used to initialise model after model has been instantiated. The model
     * should only be initialised once.
     */
    public void initialise();

    public List<Sprite> getSprites();

    public List<Text> getText();

    public void update(float deltaTime);

    /**
     * Called when model associated with screen can be disposed with. Since
     * references to the models can be held, any objects that do not need
     * references to be kept should be disposed. This allows these objects to be
     * garbage collected. Setting references of unneeded objects to null will
     * allow garbage collection. If the model is needed again, objects should be
     * re-initialised using the initialise() method.
     */
    public void dispose();

    /**
     * Handle "back button" behaviour
     * */
    public void goBack();

    /**
     * Pause the current game model. Has no effect if model is already paused.
     */
    public void pause();

    /**
     * Resume the current game model.
     */
    public void resume();
}