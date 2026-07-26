package com.personal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Mandelbrot mandelbrot = new Mandelbrot(500);
        ImageView imageView = new ImageView();
        imageView.imageProperty().bind(mandelbrot.imageProperty());

        BorderPane mainPane = new BorderPane(imageView);

        mandelbrot.widthProperty().bind(mainPane.widthProperty());
        mandelbrot.heightProperty().bind(mainPane.heightProperty());

        imageView.setOnMouseClicked(c -> {
            if (!(c.getClickCount() == 2 && c.getButton() == MouseButton.PRIMARY)) return;
            zoom(mandelbrot, c);
        });

        imageView.setOnZoom(z -> {
            if (z.getZoomFactor() == 0) return;
            zoom(mandelbrot, z);
        });

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mandelbrot");
        primaryStage.show();
    }

    // Zooms in or out of the Mandelbrot set based on input event
    private void zoom(Mandelbrot mandelbrot, InputEvent event) {
        Rectangle frame = mandelbrot.getFrame();
        double iToP = frame.width() / mandelbrot.getWidth();
        double x = 0, y = 0, scale = 0;

        if (event instanceof MouseEvent mouse) {
            x = mouse.getX() * iToP;
            y = frame.height() - mouse.getY() * iToP;
            scale = (mouse.isControlDown() ? 2 : 0.5);
        } else if (event instanceof ZoomEvent zoom) {
            x = zoom.getX() * iToP;
            y = frame.height() - zoom.getY() * iToP;
            scale = 1 / zoom.getZoomFactor();
        }

        updateNewFrame(frame, mandelbrot, x, y, scale);
    }

    // Update the mandelbrot with the new frame
    private void updateNewFrame(Rectangle frame, Mandelbrot mandelbrot, double x, double y, double scale) {
        Rectangle newFrame = frame
          .translatedBy(x, y)
          .scaledBy(scale)
          .translatedBy(-x * scale, -y * scale);
        mandelbrot.setFrameWidth(newFrame.width());
        mandelbrot.setFrameCenter(newFrame.center());
    }
}
