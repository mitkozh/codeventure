package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.MovementResult;
import com.mycompany.irr00_group_project.model.core.Point;
import java.util.List;

/**
 * Service interface for handling sprite movement and interactions in the game.
 */
public interface MovementService {
    /**
     * Attempts to move the sprite forward in the game state.
     * 
     * @param gameState the current game state
     * @return MovementResult containing success status and details
     */
    MovementResult tryMoveForward(GameState gameState);

    /**
     * Checks if a position is valid for movement.
     * 
     * @param gameState the current game state
     * @param row       target row
     * @param col       target column
     * @return true if valid, false otherwise
     */
    boolean isValidPosition(GameState gameState, int row, int col);

    /**
     * Checks if the sprite can collect a key at the specified position.
     * 
     * @param gameState the current game state
     * @param row       target row
     * @param col       target column
     * @return true if a key can be collected, false otherwise
     */
    boolean canCollectKey(GameState gameState, int row, int col);

    /**
     * Checks if the sprite can open a door at the specified position.
     * 
     * @param gameState the current game state
     * @param row       target row
     * @param col       target column
     * @return true if a door can be opened, false otherwise
     */
    boolean canOpenDoor(GameState gameState, int row, int col);

    /**
     * Checks if the level is complete based on the sprite's position.
     * 
     * @param gameState the current game state
     * @param row       target row
     * @param col       target column
     * @return true if the level is complete, false otherwise
     */
    boolean isLevelComplete(GameState gameState, int row, int col);

    /**
     * Gets the keys collected in the current game state.
     * 
     * @param gameState the current game state
     * @return a list of collected keys represented as points
     */
    List<Point> getCollectedKeys(GameState gameState);

    /**
     * Turns the sprite character to the right through the game state.
     * 
     * @param gameState the current game state
     */
    void turnRight(GameState gameState);

    /**
     * Turns the sprite character to the left through the game state.
     * 
     * @param gameState the current game state
     */
    void turnLeft(GameState gameState);
}
