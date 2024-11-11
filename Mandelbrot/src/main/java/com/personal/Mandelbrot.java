package com.personal;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;


public final class Mandelbrot {

    private static final Parameters INITIAL_PARAMETERS =
            new Parameters(500, new Point(-0.5, 0), 3.4, 300, 250);

    private final IntegerProperty maxIterationsProperty;
    private final ObjectProperty<Point> frameCenterProperty;
    private final DoubleProperty frameWidthProperty;

    private final IntegerProperty widthProperty;
    private final IntegerProperty heightProperty;

    private final ObjectProperty<Parameters> parametersProperty;
    private final ObjectProperty<Image> imageProperty;

    public Mandelbrot(int maxIterations) {
        this.maxIterationsProperty = new SimpleIntegerProperty(maxIterations);
        this.frameCenterProperty = new SimpleObjectProperty<>(INITIAL_PARAMETERS.frameCenter);
        this.frameWidthProperty = new SimpleDoubleProperty(INITIAL_PARAMETERS.frameWidth);
        this.widthProperty = new SimpleIntegerProperty(INITIAL_PARAMETERS.width);
        this.heightProperty = new SimpleIntegerProperty(INITIAL_PARAMETERS.height);
        this.parametersProperty = new SimpleObjectProperty<>(INITIAL_PARAMETERS);
        this.imageProperty = new SimpleObjectProperty<>(computeImage(INITIAL_PARAMETERS));

        ChangeListener<Object> parameterChangeListener = (p, o, n) ->
            Platform.runLater(() -> parametersProperty.set(getParameters()));

        maxIterationsProperty.addListener(parameterChangeListener);
        frameCenterProperty.addListener(parameterChangeListener);
        frameWidthProperty.addListener(parameterChangeListener);
        widthProperty.addListener(parameterChangeListener);
        heightProperty.addListener(parameterChangeListener);

        parametersProperty.addListener((p, o, n) -> imageProperty.set(computeImage(getParameters())));
    }


    public IntegerProperty maxIterationsProperty() { return maxIterationsProperty; }
    public int getMaxIterations() { return maxIterationsProperty.get(); }
    public void setMaxIterations(int newMaxIterations) { maxIterationsProperty.set(newMaxIterations); }

    public ObjectProperty<Point> frameCenterProperty() { return frameCenterProperty; }
    public Point getFrameCenter() { return frameCenterProperty.get(); }
    public void setFrameCenter(Point newFrameCenter) { frameCenterProperty.set(newFrameCenter); }

    public DoubleProperty frameWidthProperty() { return frameWidthProperty; }
    public double getFrameWidth() { return frameWidthProperty.get(); }
    public void setFrameWidth(double newFrameWidth) { frameWidthProperty.set(newFrameWidth); }

    public Rectangle getFrame() {
        return Parameters.frameFor(getWidth(), getHeight(), getFrameCenter(), getFrameWidth());
    }

    public IntegerProperty widthProperty() { return widthProperty; }
    public int getWidth() { return widthProperty.get(); }
    public void setWidth(int newWidth) { widthProperty.set(newWidth); }

    public IntegerProperty heightProperty() { return heightProperty; }
    public int getHeight() { return heightProperty.get(); }
    public void setHeight(int newHeight) { heightProperty.set(newHeight); }

    public ReadOnlyObjectProperty<Image> imageProperty() { return imageProperty; }
    public Image getImage() { return imageProperty.get(); }

    private Parameters getParameters() {
        return new Parameters(getMaxIterations(), getFrameCenter(), getFrameWidth(), getWidth(), getHeight());
    }

    private record Parameters(int maxIterations, Point frameCenter, double frameWidth, int width, int height) {

        public static Rectangle frameFor(int width, int height, Point frameCenter, double frameWidth) {
            double frameHeight = frameWidth * ((double) (height - 1) / (double) (width - 1));
            return Rectangle.ofCenterAndSize(frameCenter, frameWidth, frameHeight);
        }

        public Rectangle frame() {
            return frameFor(width, height, frameCenter, frameWidth);
        }

        @Override
        public boolean equals(Object thatO) {
            return (thatO instanceof Parameters)
                    && (maxIterations == ((Parameters) thatO).maxIterations)
                    && (frameCenter.equals(((Parameters) thatO).frameCenter))
                    && (frameWidth == ((Parameters) thatO).frameWidth)
                    && (width == ((Parameters) thatO).width)
                    && (height == ((Parameters) thatO).height);
        }

    }

    private static Image computeImage(Parameters p) {
        WritableImage image = new WritableImage(p.width, p.height);
        PixelWriter pixWriter = image.getPixelWriter();

        Color[] palette = new Color[p.maxIterations];
        for (int i = 0; i < p.maxIterations; i++) {
            //palette[i] = Color.getHSBColor(0.70f  + (i / (float) p.maxIterations) % 0.50f, 1f, i / (i + 8f));
            float hue = 0.7f + 0.5f * (float)Math.sin(2 * Math.PI * i / p.maxIterations);  // Sinusoidal hue variation
            float saturation = 1f;
            float brightness =  i / (i + 8f); // Points inside the set are black
            palette[i] = Color.getHSBColor(hue, saturation, brightness);
        }

        Rectangle frame = p.frame();
        double delta = frame.width() / (p.width - 1);
        for (int x = 0; x < p.width; ++x) {
            for (int y = 0; y < p.height; ++y) {
                double cR = frame.minX() + delta * x, cI = frame.maxY() - delta * y;
                double zr = cR, zi = cI;
                int i = 1;
                while (zr * zr + zi * zi < 4d && i < p.maxIterations) {
                    double zr1 = zr * zr - zi * zi + cR;
                    double zi1 = 2d * zr * zi + cI;
                    zr = zr1;
                    zi = zi1;
                    i += 1;
                }
                int colorIndex = i % p.maxIterations;
                int color = palette[colorIndex].getRGB();
                pixWriter.setArgb(x, y, color);

                /*
                double q = 1d - Math.pow((double) i / p.maxIterations, 0.25);
                int pI = (int) (q * 255.9999);
                int g = 0XFF000000 | (pI << 16) | (pI << 8) | pI;
                pixWriter.setArgb(x, y, color);
                 */
            }
        }
        return image;
    }

    public Image computeMandelbrotSequence(Parameters p, Complex c) {
        WritableImage image = new WritableImage(p.width, p.height);
        PixelWriter pixWriter = image.getPixelWriter();

        Rectangle frame = p.frame();
        double delta = frame.width() / (p.width - 1);

        Complex z = new Complex(0, 0);

        int iteration = 0;

        while (Math.pow(z.real(), 2) + Math.pow(z.imaginary(), 2) < 4 && iteration < getMaxIterations()) {
            // Applies the function
            z = z.squared().add(c);

            pixWriter.setArgb((int) c.real(), (int) c.imaginary(), 0x00FF0000);
            iteration++;
        }
        return image;
    }
}
