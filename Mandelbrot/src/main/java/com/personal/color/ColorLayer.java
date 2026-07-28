package com.personal.color;

import javafx.scene.paint.Color;

// Defines an interval of iterations and the color rule to apply to that interval.
public record ColorLayer(int min, int max, ColorRule rule) {

  // Returns the color for a given dwell value, normalized to the range [min, max].
  public Color colorFor(int dwell) {
    double t = (double)(dwell - min) / (max - min);
    return rule.colorFor(t);
  }

  public boolean contains(int dwell) {
    return dwell >= min && dwell <= max;
  }
}
