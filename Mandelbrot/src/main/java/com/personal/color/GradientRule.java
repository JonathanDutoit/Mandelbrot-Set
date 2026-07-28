package com.personal.color;

import java.util.List;
import javafx.scene.paint.Color;

public final class GradientRule implements ColorRule {

  private final List<GradientStop> stops;

  public GradientRule(List<GradientStop> stops) {
    validate(stops);
    this.stops = List.copyOf(stops);
  }

  @Override
  public Color colorFor(double t) {
    if (t < 0 || t > 1) {
      throw new IllegalArgumentException(
        "Normalized position must be in range [0,1]"
      );
    }

    if (t <= stops.getFirst().position()) {
      return stops.getFirst().color();
    }

    if (t >= stops.getLast().position()) {
      return stops.getLast().color();
    }

    for (int i = 0; i < stops.size() - 1; i++) {
      GradientStop lower = stops.get(i);
      GradientStop upper = stops.get(i + 1);

      if (t <= upper.position()) {
        double localT =
          (t - lower.position()) /
            (upper.position() - lower.position());

        return lower.color().interpolate(
          upper.color(),
          localT
        );
      }
    }

    throw new IllegalStateException("Unreachable");
  }

  private static void validate(List<GradientStop> stops) {
    if (stops.size() < 2) {
      throw new IllegalArgumentException(
        "Gradient requires at least two stops"
      );
    }

    for (int i = 0; i < stops.size() - 1; i++) {
      if (stops.get(i).position() >= stops.get(i + 1).position()) {
        throw new IllegalArgumentException(
          "Stops must be sorted by position"
        );
      }
    }
  }
}
