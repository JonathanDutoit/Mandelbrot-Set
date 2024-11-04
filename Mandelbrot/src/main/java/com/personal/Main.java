package com.personal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Mandelbrot mandelbrot = new Mandelbrot(500);
        ImageView imageView = new ImageView();
        imageView.imageProperty().bind(mandelbrot.imageProperty());

        BorderPane mainPane = new BorderPane(imageView);

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mandelbrot");
        primaryStage.show();
    }
}
