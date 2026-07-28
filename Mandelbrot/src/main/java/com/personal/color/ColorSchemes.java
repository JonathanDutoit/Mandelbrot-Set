package com.personal.color;

import java.util.List;
import javafx.scene.paint.Color;

public final class ColorSchemes {

  private ColorSchemes() {}

  public static ColorScheme electricBlueGold() {
    return new ColorScheme(List.of(
      new ColorLayer(
        0,
        30,
        new GradientRule(List.of(
          new GradientStop(0.0, Color.web("#FFF8C6")), // Pale yellow
          new GradientStop(1.0, Color.web("#FFD54A"))  // Gold
        ))
      ),

      new ColorLayer(
        30,
        100,
        new GradientRule(List.of(
          new GradientStop(0.0, Color.web("#FFD54A")), // Gold
          new GradientStop(1.0, Color.web("#A35A00"))  // Amber/Brown
        ))
      ),

      new ColorLayer(
        100,
        300,
        new GradientRule(List.of(
          new GradientStop(0.0, Color.web("#081B4B")), // Deep navy
          new GradientStop(1.0, Color.web("#00A8FF"))  // Electric blue
        ))
      ),

      new ColorLayer(
        300,
        Integer.MAX_VALUE,
        new GradientRule(List.of(
          new GradientStop(0.0, Color.web("#00A8FF")), // Electric blue
          new GradientStop(1.0, Color.WHITE)
        ))
      )
    ));
  }
}
