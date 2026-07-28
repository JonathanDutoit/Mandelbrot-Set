package com.personal.color;

import javafx.scene.paint.Color;

public final class SingleColorRule implements ColorRule {

  private final Color color;

  public SingleColorRule(Color color) {
    this.color = color;
  }

  @Override
  public Color colorFor(double normalizedPosition) {
    return color;
  }
}
