package com.mycompany.irr00_group_project.model.core;

import com.mycompany.irr00_group_project.model.enums.Direction;

/**
 * Sprite declaration and movement.
 * This class represents a sprite character in the game,
 * including its position and direction.
 */
public class SpriteCharacter {
    private int currentRow;
    private int currentCol;
    private Direction currentDirection; // enum: NORTH, EAST, SOUTH, WEST

    /**
     * Constructor to initialize the sprite character with a starting position and direction.
     *
     * @param startRow          The initial row position of the sprite.
     * @param startCol          The initial column position of the sprite.
     * @param initialDirection  The initial direction the sprite is facing.
     */
    public SpriteCharacter(int startRow, int startCol, Direction initialDirection) {
        this.currentRow = startRow;
        this.currentCol = startCol;
        this.currentDirection = initialDirection;
    }

    // getters
    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    // setters
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    /**
     * Updates the sprite's position to the new coordinates.
     * This should only be called after validation (boundaries, collisions)
     * has been performed by the game logic.
     *
     * @param newRow The validated new row.
     * @param newCol The validated new column.
     */
    public void moveTo(int newRow, int newCol) {
        this.currentRow = newRow;
        this.currentCol = newCol;
    }
}
