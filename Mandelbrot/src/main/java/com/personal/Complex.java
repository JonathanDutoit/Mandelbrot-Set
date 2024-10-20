package com.personal;

public record Complex(double real, double imaginary) {

    public static Complex multiply(Complex c1, Complex c2) {
        double real = c1.real * c2.real - c1.imaginary * c2.imaginary;
        double imaginary = c1.real * c2.imaginary + c2.real * c1.imaginary;
        return new Complex(real, imaginary);
    }

    public Complex squared() {
        return multiply(this, this);
    }

    public Complex add(Complex c) {
        return new Complex(this.real + c.real, this.imaginary + c.imaginary);
    }

}
