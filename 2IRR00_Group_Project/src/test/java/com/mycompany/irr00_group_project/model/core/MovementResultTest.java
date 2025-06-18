package com.mycompany.irr00_group_project.model.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

}