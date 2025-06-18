package com.mycompany.irr00_group_project.model.core;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.GameResult;
import com.mycompany.irr00_group_project.model.enums.TileType;

/**
 * JUnit test class for the GameState Class.
 * This class tests the functionality of the GameState,
 * including grid management, position validation, and game progress tracking.
 */
public class GameStateTest {

    private GameState gameState;
    private LevelData testLevelData;

    /**
     * Initializes test environment before each test execution.
     * This method sets up a 3x3 game level with:
     * - Obstacles, start position, and end position
     * - A door-key pair for interaction testing
     * - A fresh GameState instance with default values
     * 
     * @see LevelData#setSize(int, int)
     * @see GameState#GameState(LevelData)
     */
    @BeforeEach
    void setUp() {
        // Initialize test level data with a 3x3 grid 
        //Start at center facing North, End at bottom-right corner
        testLevelData = new LevelData();
        testLevelData.setSize(3, 3);
        testLevelData.setStart(1, 1, Direction.NORTH); 
        testLevelData.setEnd(2, 2); 
        
        testLevelData.setTile(0, 0, TileType.OBSTACLE);
        testLevelData.setTile(0, 1, TileType.NORMAL);
        testLevelData.setTile(0, 2, TileType.OBSTACLE);
        testLevelData.setTile(1, 0, TileType.NORMAL);
        testLevelData.setTile(1, 1, TileType.START);
        testLevelData.setTile(1, 2, TileType.NORMAL);
        testLevelData.setTile(2, 0, TileType.OBSTACLE);
        testLevelData.setTile(2, 1, TileType.NORMAL);
        testLevelData.setTile(2, 2, TileType.END);
        
        // Add a door-key pair, 1 key at top-center, 1 door at bottom-center
        testLevelData.addKey(0, 1);
        testLevelData.addDoor(2, 1); 
        testLevelData.addDoorKeyPairs();
        
        gameState = new GameState(testLevelData);
    }

    /**
     * Tests GameState constructor with LevelData.
     * Verifies correct initialization of grid, sprite, and default values.
     */
    @Test
    void testConstructorWithLevelData() {
        assertNotNull(gameState.getGrid());
        assertEquals(3, gameState.getSize());
        assertNotNull(gameState.getSprite());
        
        // Verify sprite initial position and direction
        assertEquals(1, gameState.getSprite().getCurrentRow());
        assertEquals(1, gameState.getSprite().getCurrentCol());
        assertEquals(Direction.NORTH, gameState.getSprite().getCurrentDirection());
        
        // Verify game state
        assertEquals(GameResult.PLAYING, gameState.getGameResult());
        assertEquals(0, gameState.getPlayerSteps());
        assertTrue(gameState.getCollectedKeys().isEmpty());
    }

    /**
     * Tests position validation.
     * Verifies both valid and invalid positions are correctly identified.
     */
    @Test
    void testIsValidPosition() {
        // Test valid positions
        assertTrue(gameState.isValidPosition(0, 0));
        assertTrue(gameState.isValidPosition(1, 1)); 
        assertTrue(gameState.isValidPosition(2, 2)); 
        
        // Test invalid positions
        assertFalse(gameState.isValidPosition(-1, 0));
        assertFalse(gameState.isValidPosition(0, -1));
        assertFalse(gameState.isValidPosition(3, 0));
        assertFalse(gameState.isValidPosition(0, 3));
    }

    /**
     * Tests tile retrieval at grid positions.
     * Verifies correct tile types are returned and invalid positions throw exceptions.
     */
    @Test
    void testGetTileAt() {
        assertEquals(TileType.OBSTACLE, gameState.getTileAt(0, 0));
        assertEquals(TileType.START, gameState.getTileAt(1, 1));
        assertEquals(TileType.END, gameState.getTileAt(2, 2));
        assertEquals(TileType.NORMAL, gameState.getTileAt(0, 1));
        
        assertThrows(IllegalArgumentException.class, () -> gameState.getTileAt(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> gameState.getTileAt(3, 3));
    }

    /**
     * Tests tile modification - Verifies tiles can be changed.
     */
    @Test
    void testSetTileAt() {
        // Change a tile and verify
        gameState.setTileAt(1, 1, TileType.KEY);
        assertEquals(TileType.KEY, gameState.getTileAt(1, 1));
    }

    /**
     * Tests door-key pair management.
     * Verifies pairs are correctly stored and retrieved.
     */
    @Test
    void testDoorKeyPairManagement() {
        Map<Point, Point> pairs = gameState.getDoorKeyPair();
        assertNotNull(pairs);
        assertEquals(1, pairs.size());
        
        Point door = new Point(2, 1);
        Point key = new Point(0, 1);
        assertTrue(pairs.containsKey(door));
        assertEquals(key, pairs.get(door));
    }

    /**
     * Tests player step tracking.
     * Verifies step counting and reset functionality.
     */
    @Test
    void testPlayerStepTracking() {
        assertEquals(0, gameState.getPlayerSteps());
        
        gameState.incrementPlayerSteps();
        assertEquals(1, gameState.getPlayerSteps());
        
        gameState.incrementPlayerSteps();
        gameState.incrementPlayerSteps();
        assertEquals(3, gameState.getPlayerSteps());
        
        gameState.resetPlayerSteps();
        assertEquals(0, gameState.getPlayerSteps());
    }

    /**
     * Tests game result management.
     * Verifies game state transitions between PLAYING, WON, and LOST.
     */
    @Test
    void testGameResultManagement() {
        assertEquals(GameResult.PLAYING, gameState.getGameResult());
        assertTrue(gameState.isGamePlaying());
        
        gameState.setGameResult(GameResult.WON);
        assertEquals(GameResult.WON, gameState.getGameResult());
        assertFalse(gameState.isGamePlaying());
        
        gameState.setGameResult(GameResult.LOST);
        assertEquals(GameResult.LOST, gameState.getGameResult());
        assertFalse(gameState.isGamePlaying());
    }
}