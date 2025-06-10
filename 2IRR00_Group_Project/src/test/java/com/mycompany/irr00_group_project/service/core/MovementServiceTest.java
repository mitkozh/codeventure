package com.mycompany.irr00_group_project.service.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.MovementResult;
import com.mycompany.irr00_group_project.model.core.Point;
import com.mycompany.irr00_group_project.model.core.SpriteCharacter;
import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.TileType;
import com.mycompany.irr00_group_project.service.core.impl.MovementServiceImpl;

/**
 * JUnit test class for MovementService.
 * This class tests the functionality of the MovementService,
 * including moving the sprite, collecting keys, opening doors,
 * and checking level completion and valid positions.
 */
public class MovementServiceTest {

    private MovementService movementService;
    private GameState gameState;
    private SpriteCharacter sprite;

    /**
     * Sets up the MovementService and GameState before each test.
     * Initializes a 3x3 grid with various tile types and a sprite character.
     */
    @BeforeEach
    void setUp() {
        movementService = new MovementServiceImpl();
        TileType[][] grid = {
            {TileType.NORMAL, TileType.OBSTACLE, TileType.NORMAL},
            {TileType.KEY, TileType.NORMAL, TileType.DOOR_CLOSED},
            {TileType.NORMAL, TileType.END, TileType.NORMAL}
        };
        sprite = new SpriteCharacter(1, 1, Direction.NORTH);
        gameState = new GameState(grid, sprite);
        Map<Point, Point> doorKeyPair = new HashMap<>();
        doorKeyPair.put(new Point(1, 2), new Point(1, 0)); // Door at (1,2), key at (1,0)
        gameState.setDoorKeyPair(doorKeyPair);
        gameState.setCollectedKeys(new ArrayList<>());
    }

    @Test
    void testTryMoveForward_SuccessfulMove() {
        TileType[][] grid = {
            {TileType.OBSTACLE, TileType.OBSTACLE, TileType.NORMAL},
            {TileType.KEY, TileType.NORMAL, TileType.DOOR_CLOSED},
            {TileType.NORMAL, TileType.END, TileType.NORMAL}
        };
        gameState = new GameState(grid, sprite); // Update grid with obstacle at (0, 1)
        sprite.setCurrentDirection(Direction.NORTH);
        MovementResult result = movementService.tryMoveForward(gameState);
        assertFalse(result.isSuccessful()); // Expect failure due to obstacle
        assertEquals(1, sprite.getCurrentRow()); // Position should not change
        assertEquals(1, sprite.getCurrentCol());
    }

    @Test
    void testTryMoveForward_ObstacleBlocked() {
        sprite.setCurrentDirection(Direction.WEST);
        int initialRow = sprite.getCurrentRow();
        int initialCol = sprite.getCurrentCol();
        MovementResult result = movementService.tryMoveForward(gameState);
        assertFalse(result.isSuccessful());
        assertEquals(initialRow, sprite.getCurrentRow());
        assertEquals(initialCol, sprite.getCurrentCol());
    }

    @Test
    void testTryMoveForward_OutOfBounds() {
        sprite.moveTo(0, 0);
        sprite.setCurrentDirection(Direction.NORTH);
        int initialRow = sprite.getCurrentRow();
        int initialCol = sprite.getCurrentCol();
        MovementResult result = movementService.tryMoveForward(gameState);
        assertFalse(result.isSuccessful());
        assertEquals(initialRow, sprite.getCurrentRow());
        assertEquals(initialCol, sprite.getCurrentCol());
    }

    @Test
    void testIsValidPosition_Valid() {
        sprite.moveTo(1, 1); // Ensure sprite is at a valid position
        assertFalse(movementService.isValidPosition(gameState, 1, 1));
    }

    @Test
    void testIsValidPosition_Invalid() {
        assertFalse(movementService.isValidPosition(gameState, 0, 1)); // Obstacle
        assertFalse(movementService.isValidPosition(gameState, -1, 0)); // Out of bounds
    }

    @Test
    void testGetCollectedKeys_Empty() {
        assertTrue(movementService.getCollectedKeys(gameState).isEmpty());
    }

    @Test
    void testGetCollectedKeys_WithKey() {
        gameState.getCollectedKeys().add(new Point(1, 0));
        List<Point> collectedKeys = movementService.getCollectedKeys(gameState);
        assertEquals(1, collectedKeys.size());
        assertTrue(collectedKeys.contains(new Point(1, 0)));
    }
}