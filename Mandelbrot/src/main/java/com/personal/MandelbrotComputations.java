package com.personal;

import java.util.ArrayList;
import java.util.List;

public final class MandelbrotComputations {

    private MandelbrotComputations() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public final static int MAX_ITERATIONS = 100;

    // the threshold must be at least 2, as −2 is the complex number with the largest magnitude within the set
    public final static double THRESHOLD = 4; // 2 ^ 2

    public static List<Complex> computeMandelbrotSequence(Complex c) {
        Complex z = new Complex(0, 0);
        List<Complex> complexSequence = new ArrayList<>();

        int iteration = 0;

        while (Math.pow(z.real(), 2) + Math.pow(z.imaginary(), 2) < THRESHOLD && iteration < MAX_ITERATIONS) {
            // Applies the function
            z = z.squared().add(c);

            complexSequence.add(z);
            iteration++;
        }
        return complexSequence;
    }
}
