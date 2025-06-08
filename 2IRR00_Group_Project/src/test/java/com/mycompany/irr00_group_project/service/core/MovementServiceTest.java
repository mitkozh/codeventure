package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.MovementResult;
import com.mycompany.irr00_group_project.model.core.Point;
import com.mycompany.irr00_group_project.model.core.SpriteCharacter;
import com.mycompany.irr00_group_project.model.enums.*;
import com.mycompany.irr00_group_project.service.core.impl.MovementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for MovementService.
 * This class tests the functionality of the MovementService,
 * including moving the sprite character, collecting keys, opening doors,
 * and checking tile types.
 */
public class MovementServiceTest {

    private MovementService movementService;
    private GameState gameState;
    private SpriteCharacter sprite;

    /**
     * Sets up the MovementService and GameState before each test.
     * Initializes a 3x3 grid with various tile types and a sprite character.
     */
    @BeforeEach //TO BE FIXED
    void setUp() {
        movementService = new MovementServiceImpl();
        // 3x3 grid, center is (1,1)
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
    void testTryMoveForward_EmptyTile() {
        sprite.setCurrentDirection(Direction.NORTH);
        MovementResult result = movementService.tryMoveForward(gameState);
        assertTrue(result.isSuccessful());
    }

    @Test
    void testTryMoveForward_Obstacle() {
        sprite.setCurrentDirection(Direction.WEST);
        MovementResult result = movementService.tryMoveForward(gameState);
        assertFalse(result.isSuccessful());
        assertEquals("Obstacle in the way", result.getMessage());
    }

    @Test
    void testTryMoveForward_OutOfBounds() {
        sprite.setCurrentDirection(Direction.NORTH);
        sprite.moveTo(0, 0);
        MovementResult result = movementService.tryMoveForward(gameState);
        assertFalse(result.isSuccessful());
        assertEquals("Movement out of bounds", result.getMessage());
    }

    @Test
    void testTryMoveForward_Key() {
        sprite.setCurrentDirection(Direction.WEST);
        MovementResult result = movementService.tryMoveForward(gameState);
        assertTrue(result.isSuccessful());
        assertEquals("Key collected", result.getMessage());
        assertTrue(gameState.getCollectedKeys().contains(new Point(1, 0)));
    }

    @Test
    void testTryMoveForward_DoorWithoutKey() {
        sprite.setCurrentDirection(Direction.EAST);
        MovementResult result = movementService.tryMoveForward(gameState);
        assertFalse(result.isSuccessful());
        assertEquals("Door is locked", result.getMessage());
    }

    @Test
    void testTryMoveForward_DoorWithKey() {
        gameState.getCollectedKeys().add(new Point(1, 0));
        sprite.setCurrentDirection(Direction.EAST);
        MovementResult result = movementService.tryMoveForward(gameState);
        assertTrue(result.isSuccessful());
        assertEquals("Door opened and moved forward", result.getMessage());
    }

    @Test
    void testTryMoveForward_EndTile() {
        sprite.moveTo(2, 0);
        sprite.setCurrentDirection(Direction.EAST);
        MovementResult result = movementService.tryMoveForward(gameState);
        assertTrue(result.isSuccessful());
        assertTrue(result.isLevelCompleted());
    }

    @Test
    void testIsValidPosition() {
        assertTrue(movementService.isValidPosition(gameState, 1, 1));
        assertFalse(movementService.isValidPosition(gameState, 0, 1)); // Obstacle
        assertFalse(movementService.isValidPosition(gameState, -1, 0)); // Out of bounds
    }

    @Test
    void testCanCollectKey() {
        assertTrue(movementService.canCollectKey(gameState, 1, 0));
        // Try again, should be false (already collected)
        assertFalse(movementService.canCollectKey(gameState, 1, 0));
    }

    @Test
    void testCanOpenDoor() {
        // Without key
        assertFalse(movementService.canOpenDoor(gameState, 1, 2));
        // With key
        gameState.getCollectedKeys().add(new Point(1, 0));
        assertTrue(movementService.canOpenDoor(gameState, 1, 2));
    }

    @Test
    void testIsLevelComplete() {
        assertTrue(movementService.isLevelComplete(gameState, 2, 1));
        assertFalse(movementService.isLevelComplete(gameState, 1, 1));
    }

    @Test
    void testTurnRightAndLeft() {
        sprite.setCurrentDirection(Direction.NORTH);
        movementService.turnRight(gameState);
        assertEquals(Direction.EAST, sprite.getCurrentDirection());
        movementService.turnLeft(gameState);
        assertEquals(Direction.NORTH, sprite.getCurrentDirection());
    }

    //----------

    @Test
    void testTryMoveForward_NoSprite() {
        MovementServiceImpl movementService = new MovementServiceImpl();
        GameState gameState = new GameState() {
            
            @Override
            public SpriteCharacter getSprite() {
                return null;
            }

            @Override
            public TileType[][] getGrid() {
                return (TileType[][]) new Object[3][3];
            }

            @Override
            public TileType getTileAt(int row, int col) {
                return null;
            }

            @Override
            public void setTileAt(int row, int col, TileType type) {
            }

            @Override
            public List<Point> getCollectedKeys() {
                return new ArrayList<>();
            }

            @Override
            public Map<Point, Point> getDoorKeyPair() {
                return new HashMap<>();
            }
        };
        MovementResult result = movementService.tryMoveForward(gameState);
        assertFalse(result.isSuccessful());
        assertEquals("No sprite found", result.getMessage());
    }

    @Test
    void testTurnRight() {
        MovementServiceImpl movementService = new MovementServiceImpl();
        SpriteCharacter sprite = new SpriteCharacter(0, 0, null) {
            private Direction direction = Direction.NORTH;

            @Override
            public int getCurrentRow() { 
                return 1; 
            }

            @Override
            public int getCurrentCol() { 
                return 1; 
            }

            @Override
            public Direction getCurrentDirection() { 
                return direction; 
            }

            @Override
            public void moveTo(int newRow, int newCol) {
            }

            @Override
            public void setCurrentDirection(Direction direction) { 
                this.direction = direction; 
            }
        };
        GameState gameState = new GameState() {

            @Override
            public SpriteCharacter getSprite() { 
                return sprite; 
            }

            @Override
            public TileType[][] getGrid() { 
                return (TileType[][]) new Object[3][3]; 
            }

            @Override
            public TileType getTileAt(int row, int col) { 
                return null; 
            }

            @Override
            public void setTileAt(int row, int col, TileType type) {
            }

            @Override
            public List<Point> getCollectedKeys() { 
                return new ArrayList<>(); 
            }

            @Override
            public Map<Point, Point> getDoorKeyPair() { 
                return new HashMap<>(); 
            }
        };
        movementService.turnRight(gameState);
        assertEquals(Direction.EAST, sprite.getCurrentDirection());
    }

    @Test
    void testTurnLeft() {
        MovementServiceImpl movementService = new MovementServiceImpl();
        SpriteCharacter sprite = new SpriteCharacter(0, 0, null) {
            private Direction direction = Direction.EAST;

            @Override
            public int getCurrentRow() { 
                return 1; 
            }

            @Override
            public int getCurrentCol() { 
                return 1; 
            }

            @Override
            public Direction getCurrentDirection() { 
                return direction; 
            }

            @Override
            public void moveTo(int newRow, int newCol) {
            }

            @Override
            public void setCurrentDirection(Direction direction) { 
                this.direction = direction; 
            }
        };
        GameState gameState = new GameState() {

            @Override
            public SpriteCharacter getSprite() { 
                return sprite; 
            }

            @Override
            public TileType[][] getGrid() { 
                return (TileType[][]) new Object[3][3]; 
            }

            @Override
            public TileType getTileAt(int row, int col) { 
                return null; 
            }

            @Override
            public void setTileAt(int row, int col, TileType type) {
            }

            @Override
            public List<Point> getCollectedKeys() { 
                return new ArrayList<>(); 
            }

            @Override
            public Map<Point, Point> getDoorKeyPair() { 
                return new HashMap<>(); 
            }
        };
        movementService.turnLeft(gameState);
        assertEquals(Direction.NORTH, sprite.getCurrentDirection());
    }
}
