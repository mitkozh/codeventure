package com.mycompany.irr00_group_project.service.core.impl;


import java.util.List;
import java.util.Map;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.MovementResult;
import com.mycompany.irr00_group_project.model.core.Point;
import com.mycompany.irr00_group_project.model.core.SpriteCharacter;
import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.TileType;
import com.mycompany.irr00_group_project.service.core.MovementService;

/**
 * Implementation of the MovementService interface.
 * This class handles the movement logic for the sprite character in the game.
 */
public class MovementServiceImpl implements MovementService {

    private static final String WALK_SOUND_PATH = 
        "/com/mycompany/irr00_group_project/assets/sounds/WalkSound.wav";

    @Override
    public MovementResult tryMoveForward(GameState gameState) {
        SpriteCharacter sprite = gameState.getSprite();
        if (sprite == null) {
            return new MovementResult(false, "No sprite found", null,
                    false);
        }
        int[] nextPos = calculateNextPosition(sprite);
        int nextRow = nextPos[0];
        int nextCol = nextPos[1];
        if (!isWithinGridBounds(gameState, nextRow, nextCol)) {
            return new MovementResult(false, "Movement out of bounds", null,
                    false);
        }
        TileType nextTile = gameState.getTileAt(nextRow, nextCol);
        return handleTileInteraction(gameState, sprite, nextRow, nextCol, nextTile);
    }

    private void playWalkSound() {
        AudioManagerServiceImpl.getInstance().playSfx(WALK_SOUND_PATH);
    }

    /**
     * Handles the interaction with the tile at the next position.
     * This method checks the tile type and performs the appropriate action.
     *
     * @param gameState The game state.
     * @param sprite    The sprite character attempting to move.
     * @param nextRow   The row of the next tile.
     * @param nextCol   The column of the next tile.
     * @param nextTile  The type of the next tile.
     * @return A MovementResult indicating the outcome of the interaction.
     */
    private MovementResult handleTileInteraction(GameState gameState, SpriteCharacter sprite,
                                                 int nextRow, int nextCol, TileType nextTile) {
        switch (nextTile) {
            case OBSTACLE:
                return new MovementResult(
                    false, "Obstacle in the way", nextTile, false);

            case DOOR_CLOSED:
                if (!canOpenDoor(gameState, nextRow, nextCol)) {
                    return new MovementResult(
                        false, "Door is locked", nextTile, false);
                }
                gameState.setTileAt(nextRow, nextCol, TileType.DOOR_OPENED);
                sprite.moveTo(nextRow, nextCol);
                playWalkSound();
                return new MovementResult(
                    true, "Door opened and moved forward", TileType.DOOR_CLOSED, false);

            case KEY:
                if (canCollectKey(gameState, nextRow, nextCol)) {
                    gameState.setTileAt(nextRow, nextCol, TileType.NORMAL);
                }
                sprite.moveTo(nextRow, nextCol);
                playWalkSound();
                return new MovementResult(true, "Key collected", nextTile, false);

            case END:
                sprite.moveTo(nextRow, nextCol);
                playWalkSound();
                boolean levelComplete = isLevelComplete(gameState, nextRow, nextCol);
                return new MovementResult(true, "Reached the end", nextTile, levelComplete);

            default:
                sprite.moveTo(nextRow, nextCol);
                playWalkSound();
                return new MovementResult(true, "Moved forward", nextTile, false);
        }
    }

    private boolean isWithinGridBounds(GameState gameState, int nextRow, int nextCol) {
        TileType[][] grid = gameState.getGrid();
        if (grid == null) {
            return false;
        }

        int height = grid.length;
        int width = (height > 0) ? grid[0].length : 0;

        return nextRow >= 0 && nextRow < height && nextCol >= 0 && nextCol < width;
    }

    @Override
    public boolean isValidPosition(GameState gameState, int row, int col) {
        return isWithinGridBounds(gameState, row, col)
                && gameState.getTileAt(row, col) != TileType.OBSTACLE;
    }

    @Override
    public boolean canCollectKey(GameState gameState, int row, int col) {
        TileType tile = gameState.getTileAt(row, col);
        if (tile == TileType.KEY) {
            Point currentPoint = new Point(row, col);
            if (!getCollectedKeys(gameState).contains(currentPoint)) {
                getCollectedKeys(gameState).add(currentPoint);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canOpenDoor(GameState gameState, int row, int col) {
        Map<Point, Point> doorKeyPairs = gameState.getDoorKeyPair();
        TileType tile = gameState.getTileAt(row, col);
        if (tile == TileType.DOOR_CLOSED) {
            Point doorPoint = new Point(row, col);
            Point keyPoint = doorKeyPairs.get(doorPoint);
            return keyPoint != null && getCollectedKeys(gameState).contains(keyPoint);
        }
        return false;
    }

    @Override
    public boolean isLevelComplete(GameState gameState, int row, int col) {
        TileType tile = gameState.getTileAt(row, col);
        return tile == TileType.END;
    }

    @Override
    public List<Point> getCollectedKeys(GameState gameState) {
        return gameState.getCollectedKeys();
    }

    @Override
    public void turnRight(GameState gameState) {
        SpriteCharacter sprite = gameState.getSprite();
        switch (sprite.getCurrentDirection()) {
            case NORTH:
                sprite.setCurrentDirection(Direction.EAST);
                break;
            case EAST:
                sprite.setCurrentDirection(Direction.SOUTH);
                break;
            case SOUTH:
                sprite.setCurrentDirection(Direction.WEST);
                break;
            case WEST:
                sprite.setCurrentDirection(Direction.NORTH);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: "
                        + sprite.getCurrentDirection());
        }
    }

    @Override
    public void turnLeft(GameState gameState) {
        SpriteCharacter sprite = gameState.getSprite();
        switch (sprite.getCurrentDirection()) {
            case NORTH:
                sprite.setCurrentDirection(Direction.WEST);
                break;
            case WEST:
                sprite.setCurrentDirection(Direction.SOUTH);
                break;
            case SOUTH:
                sprite.setCurrentDirection(Direction.EAST);
                break;
            case EAST:
                sprite.setCurrentDirection(Direction.NORTH);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: "
                        + sprite.getCurrentDirection());
        }
    }

    /**
     * Attempts to move the sprite one step forward in its current orientation.
     * This method itself does not check for any collisions or boundaries.
     * It returns the potential new coordinates.
     *
     * @return An array [newRow, newCol] representing the potential next position.
     */
    private int[] calculateNextPosition(SpriteCharacter sprite) {
        int nextRow = sprite.getCurrentRow();
        int nextCol = sprite.getCurrentCol();

        switch (sprite.getCurrentDirection()) {
            case NORTH:
                nextRow--;
                break;
            case EAST:
                nextCol++;
                break;
            case SOUTH:
                nextRow++;
                break;
            case WEST:
                nextCol--;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: "
                        + sprite.getCurrentDirection());
        }
        return new int[]{nextRow, nextCol};
    }
}
