package com.mycompany.irr00_group_project.model.core;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.TileType;

import javafx.scene.effect.Light.Point;

/**
 * The LevelData is responsible for storing the model of a game level.
 */
public class LevelData {

    public static class Point {
    public final int row;
    public final int col;
    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }
}



    private int width;
    private int height;
    private TileType[][] grid;
    private int startRow;
    private int startCol;
    private Direction startDirection;
    private int endRow;
    private int endCol;
    private final List<Point> keys = new ArrayList<>();
    private final List<Point> doors = new ArrayList<>();

    /**
     * Sets the size of the level grid and initializes it with NORMAL tiles.
     * 
     * @param width The width of the level grid.
     * @param height The height of the level grid.
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new TileType[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = TileType.NORMAL;
            }
        }
    }

    /**
     * Sets the starting position and direction of the level.
     *
     * @param row       The row index for the start position.
     * @param col       The column index for the start position.
     * @param direction The direction the character is facing at the start.
     */
    public void setStart(int row, int col, Direction direction) {
        this.startRow = row;
        this.startCol = col;
        this.startDirection = direction;
        if (grid != null && row < height && col < width) {
            grid[row][col] = TileType.START;
        }
    }

    /**
     * Sets the end position of the level.
     *
     * @param row The row index for the end position.
     * @param col The column index for the end position.
     */
    public void setEnd(int row, int col) {
        this.endRow = row;
        this.endCol = col;
        if (grid != null && row < height && col < width) {
            grid[row][col] = TileType.END;
        }
    }

    /**
     * Sets the tile type at a specific position in the grid.
     *
     * @param row  The row index of the tile.
     * @param col  The column index of the tile.
     * @param type The type of tile to set.
     */
    public void setTile(int row, int col, TileType type) {
        if (grid != null && row < height && col < width) {
            grid[row][col] = type;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TileType[][] getGrid() {
        return grid;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public Direction getStartDirection() {
        return startDirection;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public void addKey(int row, int col) {
        keys.add(new Point(row, col));
    }

    public void addDoor(int row, int col) {
        doors.add(new Point(row, col));
    }

    public List<Point> getKeys() {
        return keys;
    }

    public List<Point> getDoors() {
        return doors;
    }
}
