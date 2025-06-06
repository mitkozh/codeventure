package com.mycompany.irr00_group_project.model.core;

import com.mycompany.irr00_group_project.model.enums.GameResult;
import com.mycompany.irr00_group_project.model.enums.TileType;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The GameState class is responsible for managing the current state of the
 * game/level.
 */
public class GameState {
    private GameResult gameResult = GameResult.PLAYING;
    private int playerSteps = 0;
    List<Point> collectedKeys = new ArrayList<>();
    private final LevelService levelService;
    private LevelData levelData;
    private TileType[][] grid;
    private Map<Point, Point> doorKeyPair;
    private int size;
    private SpriteCharacter sprite;

    /**
     * Default constructor initializes the game state with a default level.
     */
    public GameState() {
        levelService = new LevelServiceImpl();
        this.size = 3; // just a default size
        loadFromFile("level1.txt");
    }

    /**
     * Constructor that takes appropriate filename and 
     * initializes the game state with the appropriate level.
     */
    public GameState(String filename) {
        levelService = new LevelServiceImpl();
        this.size = 3; // default size
        loadFromFile(filename);
    }
    
    /**
     * Loads the game state from a level file.
     * @param filename the name of the level file to load
     */
    public void loadFromFile(String filename) {
        assert levelService != null;
        levelData = levelService.getLevelDataByFileName(filename);
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
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }

        return grid[row][col] != TileType.OBSTACLE;
    }

    /**
     * Gets the tile type at the specified position in the grid.
     *
     * @param row the row index of the tile
     * @param col the column index of the tile
     * @return the TileType at the specified position, or null if out of bounds
     */
    public TileType getTileAt(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return grid[row][col];
        }
        return null;
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
