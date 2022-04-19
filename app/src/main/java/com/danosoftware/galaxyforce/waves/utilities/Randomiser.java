package com.danosoftware.galaxyforce.waves.utilities;

import java.util.Random;

public final class Randomiser {

  private static final Random random = new Random();

  private Randomiser() {
  }

  public static float randomFloat() {
    return random.nextFloat();
  }

  public static double random() {
    return random.nextDouble();
  }
}
