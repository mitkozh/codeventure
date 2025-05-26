package com.mycompany.irr00_group_project.controller.components;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.SpriteCharacter;
import com.mycompany.irr00_group_project.model.enums.TileType;
import com.mycompany.irr00_group_project.view.components.SpriteCharacterView;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Controller for the game grid component.
 * This class handles the rendering of the game grid, including tiles and sprite
 * character.
 */
public class GameGridController {
    @FXML
    private GridPane gameGrid;
    @FXML
    private StackPane gameGridContainer;

    private GameState gameState;
    private SpriteCharacterView spriteCharacterView;

    /**
     * Initializes the game grid controller.
     * Sets up the sprite character view and configures the grid layout.
     */
    @FXML
    public void initialize() {
        spriteCharacterView = new SpriteCharacterView();
        gameGrid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gameGrid.setAlignment(javafx.geometry.Pos.CENTER);
    }

    /**
     * Loads the game state into the grid.
     * This method sets up the grid constraints and renders the tiles based on the
     * game state.
     *
     * @param gameState The game state to load into the grid.
     */
    public void loadLevelFromGameState(GameState gameState) {
        this.gameState = gameState;
        setupGridConstraints();
        renderGrid();
        updateSpritePosition();
    }

    private void setupGridConstraints() {
        int size = gameState.getSize();
        double percentageSize = 100.0 / size;
        gameGrid.getColumnConstraints().clear();
        gameGrid.getRowConstraints().clear();

        for (int i = 0; i < size; i++) {
            ColumnConstraints colConstraint = new ColumnConstraints();
            colConstraint.setPercentWidth(percentageSize);
            colConstraint.setHgrow(Priority.ALWAYS);
            colConstraint.setFillWidth(true);
            gameGrid.getColumnConstraints().add(colConstraint);
        }

        for (int i = 0; i < size; i++) {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setPercentHeight(percentageSize);
            rowConstraint.setVgrow(Priority.ALWAYS);
            rowConstraint.setFillHeight(true);
            gameGrid.getRowConstraints().add(rowConstraint);
        }
    }

    private void renderGrid() {
        gameGrid.getChildren().clear();

        TileType[][] grid = gameState.getGrid();
        int size = gameState.getSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane tile = createTile(grid[row][col]);
                gameGrid.add(tile, col, row);
                GridPane.setFillWidth(tile, true);
                GridPane.setFillHeight(tile, true);
                GridPane.setHgrow(tile, Priority.ALWAYS);
                GridPane.setVgrow(tile, Priority.ALWAYS);
            }
        }
    }

    private StackPane createTile(TileType tileType) {
        StackPane tile = new StackPane();
        tile.getStyleClass().add("game-tile");
        tile.getStyleClass().add(getTileStyleClass(tileType));
        return tile;
    }

    private String getTileStyleClass(TileType tileType) {
        return switch (tileType) {
            case OBSTACLE -> "obstacle-tile";
            case START -> "start-tile";
            case END -> "end-tile";
            case KEY -> "key-tile";
            case DOOR_CLOSED -> "door-closed-tile";
            case DOOR_OPENED -> "door-opened-tile";
            default -> "normal-tile";
        };
    }

    /**
     * Updates the position of the sprite character in the grid.
     * It removes the old sprite image and adds it to the new position
     * based on the sprite's current row and column.
     */
    public void updateSpritePosition() {
        SpriteCharacter sprite = gameState.getSprite();
        if (sprite != null) {
            spriteCharacterView.updateDirection(sprite.getCurrentDirection());
            gameGrid.getChildren().removeIf(node -> node == spriteCharacterView.getImageView());
            gameGrid.add(spriteCharacterView.getImageView(),
                    sprite.getCurrentCol(), sprite.getCurrentRow());
            GridPane.setHalignment(spriteCharacterView.getImageView(), javafx.geometry.HPos.CENTER);
            GridPane.setValignment(spriteCharacterView.getImageView(), javafx.geometry.VPos.CENTER);

        }

    }

    public boolean isValidMove(int row, int col) {
        return gameState.isValidPosition(row, col);
    }

    /**
     * Moves the sprite character to a new position if the move is valid.
     * It checks if the new position is valid and updates the sprite's position
     * accordingly.
     *
     * @param newRow The new row index for the sprite.
     * @param newCol The new column index for the sprite.
     */
    public void moveSprite(int newRow, int newCol) {
        if (isValidMove(newRow, newCol)) {
            gameState.getSprite().moveTo(newRow, newCol);
            updateSpritePosition();
        }
    }

    public GameState getGameState() {
        return gameState;
    }

}
