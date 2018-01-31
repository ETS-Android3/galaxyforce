package com.danosoftware.galaxyforce.controller.game;

import com.danosoftware.galaxyforce.controller.interfaces.BaseControllerModel;
import com.danosoftware.galaxyforce.controller.interfaces.ControllerBase;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.options.OptionController;
import com.danosoftware.galaxyforce.services.Configurations;

public class BaseControllerFactory
{
    /**
     * Returns a base controller from currently selected option.
     * 
     * A new instance of the wanted base controller option is returned.
     * 
     * @param controller
     * @param gameHandler
     * @return base controller
     */
    public static BaseControllerModel getBaseController(ControllerBase controller, GameHandler gameHandler)
    {

        BaseControllerModel baseController = null;

        // get controller option currently selected
        Configurations configurations = Configurations.getInstance();
        OptionController optionController = configurations.getControllerType();

        // choose wanted base controller
        switch (optionController)
        {
        case ACCELEROMETER:
            baseController = new BaseTiltModel(controller);
            break;
        case DRAG:
            baseController = new BaseDragModel(gameHandler, controller);
            break;
        case JOYSTICK:
            baseController = new BaseJoystickModel(controller);
            break;
        default:
            throw new IllegalArgumentException("Unrecognised OptionController found: '" + optionController + "'.");
        }

        return baseController;
    }
}
