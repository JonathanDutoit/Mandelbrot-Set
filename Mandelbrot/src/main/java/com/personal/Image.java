package com.personal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Image extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private double gridSize = 20;
    private double offsetX = 0;
    private double offsetY = 0;

    private double lastMouseX;
    private double lastMouseY;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGrid(gc, gridSize, offsetX, offsetY);

        Pane pane = new Pane(canvas);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);

        primaryStage.setTitle("Zoomable and Draggable Grid");
        primaryStage.setScene(scene);
        primaryStage.show();

        setupZooming(pane, canvas);
        setupDragging(pane, canvas);
    }

    private void drawGrid(GraphicsContext gc, double gridSize, double offsetX, double offsetY) {
        gc.clearRect(0, 0, WIDTH, HEIGHT); // Clear the canvas

        drawGridAxes(gc, offsetX, offsetY);
        drawGridLines(gc, gridSize, offsetX, offsetY);
    }

    private void drawGridAxes(GraphicsContext gc, double offsetX, double offsetY) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // x-axis
        gc.strokeLine(0, HEIGHT / 2 + offsetY, WIDTH, HEIGHT / 2 + offsetY);

        // y-axis
        gc.strokeLine(WIDTH / 2 + offsetX, 0, WIDTH / 2 + offsetX, HEIGHT);
    }

    private void drawGridLines(GraphicsContext gc, double gridSize, double offsetX, double offsetY) {
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);

        // Vertical lines
        for (double x = offsetX % gridSize; x < WIDTH; x += gridSize) {
            gc.strokeLine(x, 0, x, HEIGHT);
        }

        // Horizontal lines
        for (double y = offsetY % gridSize; y < HEIGHT; y += gridSize) {
            gc.strokeLine(0, y, WIDTH, y);
        }
    }

    private void setupZooming(Pane pane, Canvas canvas) {
        pane.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            if (event.getDeltaY() < 0) {
                zoomFactor = 0.95;
            }

            // Ensure zoom limits
            double newGridSize = gridSize * zoomFactor;
            if (newGridSize < 5 || newGridSize > 100) {
                return;
            }

            // Calculate the mouse position relative to the canvas
            double mouseX = event.getX();
            double mouseY = event.getY();

            // Calculate new offsets to keep the zoom centered at the mouse position
            offsetX = (offsetX - mouseX) * zoomFactor + mouseX;
            offsetY = (offsetY - mouseY) * zoomFactor + mouseY;

            gridSize = newGridSize;
            drawGrid(canvas.getGraphicsContext2D(), gridSize, offsetX, offsetY);

            event.consume();
        });
    }

    private void setupDragging(Pane pane, Canvas canvas) {
        pane.setOnMousePressed((MouseEvent event) -> {
            lastMouseX = event.getX();
            lastMouseY = event.getY();
        });

        pane.setOnMouseDragged((MouseEvent event) -> {
            double deltaX = event.getX() - lastMouseX;
            double deltaY = event.getY() - lastMouseY;

            offsetX += deltaX;
            offsetY += deltaY;

            lastMouseX = event.getX();
            lastMouseY = event.getY();

            drawGrid(canvas.getGraphicsContext2D(), gridSize, offsetX, offsetY);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
