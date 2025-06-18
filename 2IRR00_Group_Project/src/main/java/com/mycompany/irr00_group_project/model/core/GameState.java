package com.mycompany.irr00_group_project.model.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mycompany.irr00_group_project.model.enums.GameResult;
import com.mycompany.irr00_group_project.model.enums.TileType;

/**
 * The GameState class is responsible for managing the current state of the
 * game/level.
 */
public class GameState {
    private GameResult gameResult = GameResult.PLAYING;
    private int playerSteps = 0;
    List<Point> collectedKeys = new ArrayList<>();
    private LevelData levelData;
    private TileType[][] grid;
    private Map<Point, Point> doorKeyPair;
    private int size;
    private SpriteCharacter sprite;

    /**
     * Constructor that takes appropriate filename and 
     * initializes the game state with the appropriate level.
     */
    public GameState(LevelData levelData) {
        this.size = 3; // default size
        loadFromLevelData(levelData);
    }
    
    /**
     * Constructor that initializes the game state with a grid and a sprite.
     */
    public GameState(TileType[][] grid2, SpriteCharacter sprite2) {
    }

    /**
     * Loads the game state from a level file.
     * @param levelData the LevelData object containing the level information
     */
    private void loadFromLevelData(LevelData levelData) {
        this.levelData = levelData;
        this.size = Math.max(levelData.getWidth(), levelData.getHeight());
        this.grid = levelData.getGrid();
        this.doorKeyPair = levelData.getDoorKeyPair();
        this.sprite = new SpriteCharacter(
                levelData.getStartRow(),
                levelData.getStartCol(),
                levelData.getStartDirection());

    }

    /**
     * Checks if the given position is valid within the grid and not an obstacle.
     *
     * @param row the row index to check
     * @param col the column index to check
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * Gets the tile type at the specified position in the grid.
     *
     * @param row the row index of the tile
     * @param col the column index of the tile
     * @pre {@code row} and {@code col} must be within the bounds of the grid.
     * @return the TileType at the specified position, or null if out of bounds
     */
    public TileType getTileAt(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid[row][col];
        } else {
            throw new IllegalArgumentException("Invalid position: ("
                    + row + ", " + col + ")");
        }
    }

    /**
     * Sets the tile type at the specified position in the grid.
     *
     * @param row      the row index of the tile
     * @param col      the column index of the tile
     * @param tileType the TileType to set at the specified position
     */
    public void setTileAt(int row, int col, TileType tileType) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            grid[row][col] = tileType;
        }
    }

    /**
     * Gets the grid representing the game.
     * @return a 2D array of TileType representing the grid of the game
     */
    public TileType[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    public SpriteCharacter getSprite() {
        return sprite;
    }

    public void setDoorKeyPair(Map<Point, Point> doorKeyPair) {
        this.doorKeyPair = doorKeyPair;
    }

    public Map<Point, Point> getDoorKeyPair() {
        return doorKeyPair;
    }

    public List<Point> getCollectedKeys() {
        return collectedKeys;
    }

    public void setCollectedKeys(List<Point> collectedKeys) {
        this.collectedKeys = collectedKeys;
    }

    public LevelData getLevelData() {
        return levelData;
    }

    public void incrementPlayerSteps() {
        playerSteps++;
    }

    public void resetPlayerSteps() {
        playerSteps = 0;
    }

    public int getPlayerSteps() {
        return playerSteps;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public boolean isGamePlaying() {
        return gameResult == GameResult.PLAYING;
    }
}
