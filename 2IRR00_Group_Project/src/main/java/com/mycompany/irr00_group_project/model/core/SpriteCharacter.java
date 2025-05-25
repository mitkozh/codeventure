package com.mycompany.irr00_group_project.model.core;

import com.mycompany.irr00_group_project.model.enums.Direction;

/**
 * .
 */
public class SpriteCharacter implements CharacterControls{
    private int currentRow;
    private int currentCol;
    private Direction currentDirection; // enum: NORTH, EAST, SOUTH, WEST

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
    private void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    private void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    private void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    @Override
    public void moveForward() {

    }

    @Override
    public void turnLeft() {
        switch (currentDirection) {
            case NORTH: currentDirection = Direction.WEST; break;
            case WEST: currentDirection = Direction.SOUTH; break;
            case SOUTH: currentDirection = Direction.EAST; break;
            case EAST: currentDirection = Direction.NORTH; break;
        }
        //for debug
        System.out.println("Sprite turned left. New orientation is: " + currentDirection);

    }

    @Override
    public void turnRight() {
        switch (currentDirection) {
            case NORTH: currentDirection = Direction.EAST; break;
            case EAST: currentDirection = Direction.SOUTH; break;
            case SOUTH: currentDirection = Direction.WEST; break;
            case WEST: currentDirection = Direction.NORTH; break;
        }
        //for debug
        System.out.println("Sprite turned right. New orientation is: " + currentDirection);
    }

    /**
     * Attempts to move the sprite one step forward in its current orientation.
     * This method itself does not check for any collisions or boundaries.
     * It returns the potential new coordinates.
     *
     * @return An array [newRow, newCol] representing the potential next position.
     */
    private int[] calculateNextPosition() {
        int nextRow = currentRow;
        int nextCol = currentCol;

        switch (currentDirection) {
            case NORTH: nextRow--; break;
            case EAST: nextCol++; break;
            case SOUTH: nextRow++; break;
            case WEST: nextCol--; break;
        }
        return new int[]{nextRow, nextCol};
    }

    /**
     * Updates the sprite's position to the new coordinates.
     * This should only be called after validation (boundaries, collisions)
     * has been performed by the game logic.
     *
     * @param newRow The validated new row.
     * @param newCol The validated new column.
     */
    private void moveTo(int newRow, int newCol) {
        this.currentRow = newRow;
        this.currentCol = newCol;
        //for future debugging
        System.out.println("Sprite moved to: (" + currentRow + ", " + currentCol + ")");
    }
}
