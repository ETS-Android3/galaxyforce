package com.danosoftware.galaxyforce.sprites.properties;

public enum MenuSpriteIdentifier {

  // main logo
  GALAXY_FORCE(SpriteDetails.GALAXY_FORCE, "logo"),

  // google play icon
  GOOGLE_PLAY(SpriteDetails.GOOGLE_PLAY, "googlePlay"),

  // menu buttons
  MAIN_MENU(SpriteDetails.MAIN_MENU, "blueButtonUp"),
  MAIN_MENU_PRESSED(SpriteDetails.MAIN_MENU_PRESSED, "blueButtonDown"),

  // menu buttons
  OPTIONS_MENU(SpriteDetails.OPTIONS_MENU, "greyMenuButtonUp"),
  OPTIONS_MENU_PRESSED(SpriteDetails.OPTIONS_MENU_PRESSED, "goldMenuButtonDown"),

  // wave select buttons
  LEVEL_FRAME(SpriteDetails.LEVEL_FRAME, "greenButtonUp"),
  LEVEL_FRAME_PRESSED(SpriteDetails.LEVEL_FRAME_PRESSED, "greenButtonDown"),

  // locked wave buttons
  LEVEL_FRAME_LOCKED(SpriteDetails.LEVEL_FRAME_LOCKED, "lockedButtonUp"),
  LEVEL_FRAME_LOCKED_PRESSED(SpriteDetails.LEVEL_FRAME_LOCKED_PRESSED, "lockedButtonDown"),

  // next zone buttons
  NEXT_LEVEL(SpriteDetails.NEXT_LEVEL, "greenRightArrowUp"),
  NEXT_LEVEL_PRESSED(SpriteDetails.NEXT_LEVEL_PRESSED, "greenRightArrowDown"),

  // previous zone buttons
  PREVIOUS_LEVEL(SpriteDetails.PREVIOUS_LEVEL, "greenLeftArrowUp"),
  PREVIOUS_LEVEL_PRESSED(SpriteDetails.PREVIOUS_LEVEL_PRESSED, "greenLeftArrowDown"),

  // option buttons
  OPTION_UNSELECTED(SpriteDetails.OPTION_UNSELECTED, "greyButtonUp"),
  OPTION_SELECTED(SpriteDetails.OPTION_SELECTED, "goldButtonDown"),

  // stars
  STAR(SpriteDetails.STAR, "Star"),
  STAR_BLACK(SpriteDetails.STAR_BLACK, "StarBLACK"),
  STAR_RED(SpriteDetails.STAR_RED, "StarRED"),
  STAR_BLUE(SpriteDetails.STAR_BLUE, "StarBLUE"),
  STAR_SPARKLE(SpriteDetails.STAR_SPARKLE, "StarSPARKLE"),

  // pluto
  PLUTO(SpriteDetails.PLUTO, "Pluto"),

  // bases
  BASE_LARGE(SpriteDetails.BASE_LARGE, "BaseLarge"),
  BASE_LARGE_TILT(SpriteDetails.BASE_LARGE_TILT, "BaseTiltLarge"),

  // fonts
  FONT_MAP(SpriteDetails.FONT_MAP, "GalaxyForceFont_30x38-crop");

  private final SpriteDetails sprite;
  private final String imageName;

  MenuSpriteIdentifier(SpriteDetails sprite, String imageName) {
    this.sprite = sprite;
    this.imageName = imageName;
  }

  public SpriteDetails getSprite() {
    return sprite;
  }

  public String getImageName() {
    return imageName;
  }
}
