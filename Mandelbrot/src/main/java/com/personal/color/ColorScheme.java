package com.personal.color;

import java.util.List;
import javafx.scene.paint.Color;

public class ColorScheme {
  private static final Color DEFAULT_COLOR = Color.BLACK;

  private final List<ColorLayer> layers;

  public ColorScheme(List<ColorLayer> layers) {
    this.layers = List.copyOf(layers);
  }
  public Color colorFor(int dwell) {
    for (ColorLayer layer : layers) {
      if (layer.contains(dwell)) {
        return layer.colorFor(dwell);
      }
    }
    return DEFAULT_COLOR;
  }
}
