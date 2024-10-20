package com.personal;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MandelbrotComputationsTest {

    @Test
    public void testComputeMandelbrotSequence() {
        Complex c = new Complex(0.0, 1);

        List<Complex> result = MandelbrotComputations.computeMandelbrotSequence(c);

        assertEquals(100, result.size());
    }
}