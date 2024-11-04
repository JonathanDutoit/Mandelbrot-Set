package com.personal;

public record Point(double x, double y) {

    public Point translatedBy(double dX, double dY) {
        return new Point(x + dX, y + dY);
    }

    @Override
    public boolean equals(Object thatO) {
        return (thatO instanceof Point)
                && (this.x == ((Point) thatO).x)
                && (this.y == ((Point) thatO).y);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
}