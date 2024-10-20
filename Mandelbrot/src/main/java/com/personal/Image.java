package com.personal;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Image extends Application {

    private static final int HEIGHT = 800;
    private static final int WIDTH = 700;

    @Override
    public void start(Stage primaryStage) {
        // Set the title of the window
        primaryStage.setTitle("2D Graph of Complex Numbers");

        // Create a Canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawAxes(gc);
        drawGrid(gc);
        plotComplexNumber(gc, 3, 4);

        // Add the canvas to a Group and set it as the Scene
        Group root = new Group();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawAxes(GraphicsContext gc) {
        // Clear the canvas
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Set line color and width
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        // Draw x-axis
        gc.strokeLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

        // Draw y-axis
        gc.strokeLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);

        // Draw vertical grid lines
        for (int i = 0; i < WIDTH; i += 20) {
            gc.strokeLine(i, 0, i, HEIGHT);
        }

        // Draw horizontal grid lines
        for (int i = 0; i < HEIGHT; i += 20) {
            gc.strokeLine(0, i, WIDTH, i);
        }
    }

    private void plotComplexNumber(GraphicsContext gc, double real, double imag) {
        gc.setFill(Color.RED);
        double x = WIDTH / 2 + real * 20; // Scale factor of 20 for example
        double y = HEIGHT / 2 - imag * 20; // Invert y-axis for correct display
        gc.fillOval(x - 2, y - 2, 4, 4); // Draw a small circle
    }


    public static void main(String[] args) {
        launch(args);
    }
}
