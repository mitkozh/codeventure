package com.mycompany.irr00_group_project.model.core;

import com.mycompany.irr00_group_project.model.enums.TileType;

/**
 * Represents the result of a movement action in the game.
 * It has fields to indicate whether the movement was successful,
 * a message describing the result, the current tile type after the movement,
 * and whether the level has been completed.
 */
public class MovementResult {
    private final boolean isSuccessful;
    private final String message;
    private final TileType currentTile;
    private final boolean levelCompleted;

    /**
     * Constructs a MovementResult with the specified parameters.
     *
     * @param isSuccessful   Indicates if the movement was successful.
     * @param message        A message describing the result of the movement.
     * @param currentTile    The type of tile the character is currently on.
     * @param levelCompleted Indicates if the level has been completed.
     */
    public MovementResult(boolean isSuccessful, String message,
                          TileType currentTile, boolean levelCompleted) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.currentTile = currentTile;
        this.levelCompleted = levelCompleted;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public TileType getCurrentTile() {
        return currentTile;
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

}
