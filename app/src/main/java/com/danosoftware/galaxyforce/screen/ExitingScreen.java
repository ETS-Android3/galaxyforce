package com.danosoftware.galaxyforce.screen;

import com.danosoftware.galaxyforce.controllers.common.Controller;
import com.danosoftware.galaxyforce.models.screens.Model;
import com.danosoftware.galaxyforce.sprites.game.starfield.NewStarField;
import com.danosoftware.galaxyforce.textures.TextureMap;
import com.danosoftware.galaxyforce.textures.TextureService;
import com.danosoftware.galaxyforce.view.Camera2D;
import com.danosoftware.galaxyforce.view.SpriteBatcher;
import com.danosoftware.galaxyforce.view.StarBatcher;

public class ExitingScreen extends AbstractScreen {

  public ExitingScreen(
      Model model,
      Controller controller,
      TextureService textureService,
      TextureMap textureMap,
      Camera2D camera,
      SpriteBatcher batcher,
      StarBatcher starBatcher,
      NewStarField starField) {

    super(model, controller, textureService, textureMap, camera, batcher, starBatcher, starField);
  }

  @Override
  public boolean handleBackButton() {
    // Don't handle back button and so allow application to exit
    return false;
  }
}
