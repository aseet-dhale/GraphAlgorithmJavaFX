package org.graphalgorithm;

import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.graphalgorithm.algorithm.ShortestDistanceWithQueue;
import org.graphalgorithm.algorithm.Tuple;
import org.graphalgorithm.utils.BlockType;
import org.graphalgorithm.utils.GenerateGrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private final String BACKGROUND_COLOR = "#f4f4f9";   // Light soft background
    private final String PATH_COLOR = "#a0c4ff";         // Pastel blue
    private final String BRICK_COLOR = "#ffadad";        // Pastel red
    private final String DESTINATION_COLOR = "#caffbf";  // Pastel green
    private Group regenerateGridButton;
    private Group solveButton;
    private Group blocks;
    private GridPane gridPane;
    private BlockType[][] grid;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        regenerateGridButton = regenerateGridButton();
        blocks = getBlocks();
        solveButton = solveButton();
        gridPane = new GridPane();
        gridPane.add(regenerateGridButton, 0, 0);
        gridPane.add(solveButton, 2, 0);
        gridPane.add(blocks, 0, 1, 3, 1);
        gridPane.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(5);
        Scene scene = new Scene(gridPane, 1280, 720);
        scene.setFill(Color.web(BACKGROUND_COLOR));
        stage.setScene(scene);
        stage.show();
    }

    Group regenerateGridButton() {
        Button button = new Button("Regenerate Grid");
        button.setBackground(new Background(new BackgroundFill(Color.WHEAT, new CornerRadii(1), new Insets(1))));
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Group newBlocks = getBlocks();
                gridPane.getChildren().remove(blocks);
                gridPane.add(newBlocks, 0, 1, 3, 1);
                blocks = newBlocks;
            }
        });
        return new Group(button);
    }

    Group solveButton() {
        Button button = new Button("Solve Grid");
        button.setBackground(new Background(new BackgroundFill(Color.WHEAT, new CornerRadii(1), new Insets(1))));
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                List<Tuple<Integer, Integer>> path = ShortestDistanceWithQueue.solveGrid(blocks, grid);
                System.out.println("Found path " + (path.size() == 0));
                for (int i = 0; i < path.size(); i++) {
                    if (i != 0) {
                        Rectangle rectangle = (Rectangle) (((Group) (blocks.getChildren().get(
                                path.get(i).x))).getChildren().get(path.get(i).y));
                        transition(rectangle);
                    }
                }
            }
        });
        return new Group(button);
    }

    private void transition(Rectangle rectangle) {
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), rectangle);
        fillTransition.setToValue(Color.web(DESTINATION_COLOR));
        fillTransition.setInterpolator(Interpolator.LINEAR);
        fillTransition.play();
    }

    Group getBlocks() {
        BlockType[][] grid = new GenerateGrid(10, 10).generateGrid();

        List<List<Rectangle>> rectangles = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            List<Rectangle> temp = new ArrayList<>();
            for (int j = 0; j < grid[0].length; j++) {
                Rectangle rectangle = new Rectangle();
                // Set position
                rectangle.setX(i * 50);
                rectangle.setY(j * 50);
                // Set dimensions
                rectangle.setHeight(44.0);
                rectangle.setWidth(44.0);
                if ((i != 0 || j != 0) && grid[i][j] == BlockType.PATH) {
                    rectangle.setFill(Color.web(PATH_COLOR));
                } else if (grid[i][j] == BlockType.BRICK) {
                    rectangle.setFill(Color.web(BRICK_COLOR));
                } else {
                    rectangle.setFill(Color.web(DESTINATION_COLOR));
                }
                temp.add(rectangle);
            }
            rectangles.add(temp);
        }
        this.grid = grid;
        Group group = new Group();
        for (List<Rectangle> rect : rectangles) {
            Group tempGroup = new Group();
            tempGroup.getChildren().addAll(rect);
            group.getChildren().add(tempGroup);
        }
        return group;
    }

}