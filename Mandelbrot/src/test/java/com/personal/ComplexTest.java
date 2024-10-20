package com.personal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComplexTest {

    @Test
    public void testSquaredRealNumber() {
        Complex r = new Complex(5.0, 0.0);

        Complex result = r.squared();

        assertEquals(25.0, result.real(), 0);
        assertEquals(0.0, result.imaginary(), 0);
    }

    @Test
    public void testSquaredComplexNumber() {
        Complex c = new Complex(0, 1);

        Complex result = c.squared();

        assertEquals(-1.0, result.real(), 0);
        assertEquals(0.0, result.imaginary(), 0);
    }

    @Test
    public void testMultiplyRealNumbers() {
        Complex r1 = new Complex(3, 0);
        Complex r2 = new Complex(4, 0);

        Complex result = Complex.multiply(r1, r2);

        assertEquals(12.0, result.real(), 0);
        assertEquals(0.0, result.imaginary(), 0);
    }

    @Test
    public void testMultiplyComplexNumbers() {
        Complex c1 = new Complex(1, 1);
        Complex c2 = new Complex(0, -1);

        Complex result = Complex.multiply(c1, c2);

        assertEquals(1.0, result.real(), 0);
        assertEquals(-1.0, result.imaginary(), 0);
    }

    @Test
    public void testAddingRealNumbers() {
        Complex r1 = new Complex(-1.0, 0);
        Complex r2 = new Complex(1.0, 0);

        Complex result = r1.add(r2);

        assertEquals(0.0, result.real(), 0);
        assertEquals(0.0, result.imaginary(), 0);
    }

    @Test
    public void testAddingComplexNumbers() {
        Complex c1 = new Complex(-1.0, 1);
        Complex c2 = new Complex(1.0, 1);

        Complex result = c1.add(c2);

        assertEquals(0.0, result.real(), 0);
        assertEquals(2.0, result.imaginary(), 0);
    }
}