package com.personal;

import com.personal.color.ColorScheme;
import com.personal.color.ColorSchemes;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import javafx.scene.paint.Color;

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

        ColorScheme scheme = ColorSchemes.electricBlueGold();
        Rectangle frame = p.frame();
        double delta = frame.width() / (p.width - 1);

        MandelbrotFractal fractal = new MandelbrotFractal();

        for (int x = 0; x < p.width; ++x) {
            for (int y = 0; y < p.height; ++y) {
                Complex c = new Complex(frame.minX() + delta * x, frame.maxY() - delta * y);
                int dwell = fractal.dwell(c, p.maxIterations);
                Color color = scheme.colorFor(dwell);
                pixWriter.setColor(x, y, color);
            }
        }
        return image;
    }
}
