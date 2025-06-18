package com.mycompany.irr00_group_project.model.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.enums.TileType;

/**
 * Test class for MovementResult.
 * Verifies the behavior of the movement result data container.
 */
class MovementResultTest {

    /**
     * Tests successful movement with level completion (ending on END tile).
     */
    @Test
    void testSuccessfulMovementWithLevelCompletion() {
        MovementResult result = new MovementResult(
            true, "Level completed!", TileType.END, true);
        
        assertTrue(result.isSuccessful(), "Movement should be successful");
        assertEquals("Level completed!", result.getMessage(), 
            "Message should match");
        assertEquals(TileType.END, result.getCurrentTile(),
            "Tile type should be END");
        assertTrue(result.isLevelCompleted(),
            "Level should be marked as completed");
    }

    /**
     * Tests successful movement without level completion (on NORMAL tile).
     */
    @Test
    void testSuccessfulMovementWithoutLevelCompletion() {
        MovementResult result = new MovementResult(
            true, "Moved successfully", TileType.NORMAL, false);
        
        assertTrue(result.isSuccessful(), "Movement should be successful");
        assertEquals("Moved successfully", result.getMessage(),
            "Message should match");
        assertEquals(TileType.NORMAL, result.getCurrentTile(),
            "Tile type should be NORMAL");
        assertFalse(result.isLevelCompleted(),
            "Level should not be marked as completed");
    }

    /**
     * Tests failed movement (hit an OBSTACLE).
     */
    @Test
    void testFailedMovement() {
        MovementResult result = new MovementResult(
            false, "Cannot move there", TileType.OBSTACLE, false);
        
        assertFalse(result.isSuccessful(), "Movement should be failed");
        assertEquals("Cannot move there", result.getMessage(),
            "Message should match");
        assertEquals(TileType.OBSTACLE, result.getCurrentTile(),
            "Tile type should be OBSTACLE");
        assertFalse(result.isLevelCompleted(),
            "Level should not be marked as completed");
    }

    /**
     * Tests behavior with empty message.
     */
    @Test
    void testEmptyMessage() {
        MovementResult result = new MovementResult(
            true, "", TileType.NORMAL, false);
        
        assertEquals("", result.getMessage(),
            "Empty message should be preserved");
    }

    /**
     * Tests behavior with null message (should be allowed).
     */
    @Test
    void testNullMessage() {
        MovementResult result = new MovementResult(
            true, null, TileType.NORMAL, false);
        
        assertNull(result.getMessage(),
            "Null message should be preserved");
    }

    /**
     * Tests behavior with null tile type (should be allowed).
     */
    @Test
    void testNullTileType() {
        MovementResult result = new MovementResult(
            true, "Test", null, false);
        
        assertNull(result.getCurrentTile(),
            "Null tile type should be preserved");
    }

    /**
     * Tests all getters with various tile types.
     */
    @Test
    void testAllTileTypes() {
        for (TileType type : TileType.values()) {
            MovementResult result = new MovementResult(
                true, type.name(), type, false);
            
            assertEquals(type, result.getCurrentTile(),
                "Tile type should match for " + type.name());
        }
    }

    /**
     * Tests movement to START tile.
     */
    @Test
    void testStartTileMovement() {
        MovementResult result = new MovementResult(
            true, "Returned to start", TileType.START, false);
        
        assertEquals(TileType.START, result.getCurrentTile(),
            "Should handle START tile correctly");
    }

    /**
     * Tests movement to KEY tile.
     */
    @Test
    void testKeyTileMovement() {
        MovementResult result = new MovementResult(
            true, "Found a key!", TileType.KEY, false);
        
        assertEquals(TileType.KEY, result.getCurrentTile(),
            "Should handle KEY tile correctly");
    }

    /**
     * Tests movement to DOOR_CLOSED tile.
     */
    @Test
    void testClosedDoorMovement() {
        MovementResult result = new MovementResult(
            false, "Door is closed", TileType.DOOR_CLOSED, false);
        
        assertEquals(TileType.DOOR_CLOSED, result.getCurrentTile(),
            "Should handle DOOR_CLOSED tile correctly");
    }

    /**
     * Tests movement to DOOR_OPENED tile.
     */
    @Test
    void testOpenedDoorMovement() {
        MovementResult result = new MovementResult(
            true, "Door is open", TileType.DOOR_OPENED, false);
        
        assertEquals(TileType.DOOR_OPENED, result.getCurrentTile(),
            "Should handle DOOR_OPENED tile correctly");
    }
}