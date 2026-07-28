package com.personal.color;

import javafx.scene.paint.Color;

public interface ColorRule {

  // Assumes the normalizedPosition is in the range [0, 1]
  public Color colorFor(double normalizedPosition);
}
