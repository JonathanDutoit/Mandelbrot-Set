package com.personal;

public class MandelbrotFractal implements Fractal {

  @Override
  public int dwell(Complex c, int maxIterations) {
    Complex z = new Complex(0, 0);
    int iterations = 0;

    while (z.real() * z.real() + z.imaginary() * z.imaginary() <= 4 && iterations < maxIterations) {
      z = z.squared().add(c);
      iterations++;
    }

    return iterations;
  }
}
