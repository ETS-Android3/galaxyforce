package com.danosoftware.galaxyforce.games;

import android.content.Context;
import android.opengl.GLSurfaceView;
import com.danosoftware.galaxyforce.billing.BillingService;
import com.danosoftware.galaxyforce.services.configurations.ConfigurationService;
import com.danosoftware.galaxyforce.services.googleplay.GooglePlayServices;
import com.danosoftware.galaxyforce.view.GLGraphics;

/**
 * Runs a game loop test that cycles through different sections of the game automatically. Used for
 * benchmarking and performance reports.
 */
public class GameLoopTest extends GameImpl {

  boolean hasSwitched = false;

  public GameLoopTest(
      Context context,
      GLGraphics glGraphics,
      GLSurfaceView glView,
      BillingService billingService,
      GooglePlayServices playService,
      ConfigurationService configurationService) {

    super(context, glGraphics, glView, billingService, playService, configurationService);
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);
    if (!hasSwitched) {
      this.changeToGameScreen(1);
      hasSwitched = true;
    }
  }
}
