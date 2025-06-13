package com.mycompany.irr00_group_project.gui.components;

import com.mycompany.irr00_group_project.controller.components.GameGridController;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Component for displaying the game grid.
 */
public class GameGridDisplay {

    private GameGridController controller;
    private VBox rootContainer;
    private VBox gridContainer;
    private StackPane gameGridContainer;
    private GridPane gameGrid;

    public GameGridDisplay() {
        controller = new GameGridController(this);
        createUI();
    }

    private void createUI() {
        rootContainer = new VBox();
        rootContainer.getStyleClass().add("grid-panel");

        gridContainer = new VBox();
        gridContainer.getStyleClass().add("grid-container");
        VBox.setVgrow(gridContainer, javafx.scene.layout.Priority.ALWAYS);
        gridContainer.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));

        gameGridContainer = new StackPane();
        gameGridContainer.getStyleClass().add("game-grid-background");
        VBox.setVgrow(gameGridContainer, javafx.scene.layout.Priority.ALWAYS);

        gameGrid = new GridPane();
        gameGrid.getStyleClass().add("game-grid");
        gameGrid.setAlignment(javafx.geometry.Pos.CENTER);

        gameGridContainer.getChildren().add(gameGrid);
        gridContainer.getChildren().add(gameGridContainer);
        rootContainer.getChildren().add(gridContainer);
        controller.initialize();
    }

    public Parent getView() {
        return rootContainer;
    }

    public VBox getRootContainer() {
        return rootContainer;
    }

    public VBox getGridContainer() {
        return gridContainer;
    }

    public StackPane getGameGridContainer() {
        return gameGridContainer;
    }

    public GridPane getGameGrid() {
        return gameGrid;
    }

    public GameGridController getController() {
        return controller;
    }
}