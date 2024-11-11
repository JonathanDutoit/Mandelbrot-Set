package com.personal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.beans.EventHandler;

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

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mandelbrot");
        primaryStage.show();
    }

    public void zoom(Mandelbrot mandelbrot, MouseEvent mouse) {
        Rectangle frame = mandelbrot.getFrame();
        double iToP = frame.width() / mandelbrot.getWidth();
        double x = mouse.getX() * iToP;
        double y = frame.height() - mouse.getY() * iToP;
        double scale = (mouse.isControlDown() ? 2 : 0.5);
        Rectangle newFrame = frame
                .translatedBy(x, y)
                .scaledBy(scale)
                .translatedBy(-x * scale, -y * scale);

        // Update the mandelbrot with the new frame
        mandelbrot.setFrameWidth(newFrame.width());
        mandelbrot.setFrameCenter(newFrame.center());
    }

}
