package com.mycompany.irr00_group_project.controller.components;

import com.mycompany.irr00_group_project.gui.components.GameGridDisplay;
import com.mycompany.irr00_group_project.gui.components.SpriteCharacterView;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.SpriteCharacter;
import com.mycompany.irr00_group_project.model.enums.TileType;
import com.mycompany.irr00_group_project.service.core.SettingsService;
import com.mycompany.irr00_group_project.service.core.impl.SettingsServiceImpl;
import com.mycompany.irr00_group_project.service.observable.ObservableProvider;
import com.mycompany.irr00_group_project.service.observable.SettingsObservables;
import com.mycompany.irr00_group_project.utils.StringUtils;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

/**
 * Controller for the game grid component.
 * This class handles the rendering of the game grid, including tiles and sprite
 * character.
 */
public class GameGridController {
    private GridPane gameGrid;
    private GameState gameState;
    private SpriteCharacterView spriteCharacterView;
    private SettingsService settingsService;
    private GameGridDisplay view;

    /**
     * Constructs a controller for the game grid display.
     * @param view The GameGridDisplay view component
     */
    public GameGridController(GameGridDisplay view) {
        this.view = view;
    }

    /**
     * Initializes the game grid controller.
     * Sets up the sprite character view and configures the grid layout.
     */
    public void initialize() {
        this.gameGrid = view.getGameGrid();
        spriteCharacterView = new SpriteCharacterView();
        gameGrid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gameGrid.setAlignment(javafx.geometry.Pos.CENTER);
        setupSettingsObservables();
    }

    /**
     * Configures settings observables for avatar changes.
     */
    private void setupSettingsObservables() {
        settingsService = SettingsServiceImpl.getInstance();
        if (settingsService instanceof ObservableProvider provider) {
            provider.getObservable(SettingsObservables.class).ifPresent(settings -> {
                settings.selectedAvatarProperty().addListener((obs, oldAvatar, newAvatar) -> {
                    if (!StringUtils.isNullOrEmpty(newAvatar)) {
                        spriteCharacterView.updateSpriteImage();
                    }
                });
            });
        }
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
        renderGridAndSprite();
    }

    /**
     * Renders the game grid and updates the sprite character position.
     * This method is called to refresh the grid display and ensure the sprite is
     * correctly positioned.
     */
    public void renderGridAndSprite() {
        renderGrid();
        updateSpritePosition();
    }

    /**
     * Sets up grid constraints based on game state size.
     */
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

    /**
     * Renders the game grid tiles.
     */
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

    /**
     * Creates a styled tile for the grid.
     * @param tileType The type of tile to create
     * @return The created StackPane tile
     */
    private StackPane createTile(TileType tileType) {
        StackPane tile = new StackPane();
        tile.getStyleClass().add("game-tile");
        tile.getStyleClass().add(getTileStyleClass(tileType));
        return tile;
    }

    /**
     * Gets the CSS style class for a tile type.
     * @param tileType The tile type to get style for
     * @return The corresponding style class name
     */
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
    private void updateSpritePosition() {
        SpriteCharacter sprite = gameState.getSprite();
        if (sprite != null) {
            spriteCharacterView.updateSpriteSize(gameState.getSize());
            spriteCharacterView.updateDirection(sprite.getCurrentDirection());
            gameGrid.getChildren().removeIf(node -> node == spriteCharacterView.getNode());
            gameGrid.add(spriteCharacterView.getNode(),
                    sprite.getCurrentCol(), sprite.getCurrentRow());
            GridPane.setHalignment(spriteCharacterView.getNode(), javafx.geometry.HPos.CENTER);
            GridPane.setValignment(spriteCharacterView.getNode(), javafx.geometry.VPos.CENTER);

        }

    }

}
