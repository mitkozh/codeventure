package com.mycompany.irr00_group_project.model;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.core.dto.GameProgressDTO;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;

/**
 * JUnit test class for GameProgressDTO.
 * This class tests the functionality of the GameProgressDTO,
 * focusing on level progress management.
 */
public class GameProgressDTOTest {

    private GameProgressDTO gameProgressDTO;
    private LevelDTO testLevel1;
    private LevelDTO testLevel2;

    /**
     * Sets up test fixtures before each test method.
     * Initializes a fresh GameProgressDTO instance and two test LevelDTO objects:
     * testLevel1: Level 1 with 3 stars (unlocked)
     * testLevel2: Level 2 with 0 stars (locked)
     */
    @BeforeEach
    void setUp() {
        gameProgressDTO = new GameProgressDTO();
        testLevel1 = new LevelDTO(1, 3, true);
        testLevel2 = new LevelDTO(2, 0, false);
    }

    /**
     * Tests retrieving an existing level.
     * Verifies the correct level DTO is returned.
     */
    @Test
    void testGetLevelWithExistingLevel() {
        gameProgressDTO.putLevel(1, testLevel1);
        LevelDTO retrievedLevel = gameProgressDTO.getLevel(1);
        assertEquals(testLevel1, retrievedLevel);
    }

    /**
     * Tests retrieving a non-existent level.
     * Verifies null is returned when level doesn't exist.
     */
    @Test
    void testGetLevelWithNonExistingLevel() {
        assertNull(gameProgressDTO.getLevel(1));
    }

    /**
     * Tests retrieving with invalid level numbers.
     * Verifies IllegalArgumentException is thrown for invalid inputs.
     */
    @Test
    void testGetLevelWithInvalidLevelNumber() {
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.getLevel(0));
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.getLevel(-1));
    }

    /**
     * Tests adding valid level data.
     * Verifies levels can be added and retrieved correctly.
     */
    @Test
    void testPutLevelWithValidData() {
        gameProgressDTO.putLevel(1, testLevel1);
        assertEquals(testLevel1, gameProgressDTO.getLevel(1));
        
        gameProgressDTO.putLevel(2, testLevel2);
        assertEquals(testLevel2, gameProgressDTO.getLevel(2));
    }

    /**
     * Tests adding null level data.
     * Verifies IllegalArgumentException is thrown.
     */
    @Test
    void testPutLevelWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.putLevel(1, null));
    }

    /**
     * Tests adding with invalid level numbers.
     * Verifies IllegalArgumentException is thrown for invalid inputs.
     */
    @Test
    void testPutLevelWithInvalidLevelNumber() {
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.putLevel(0, testLevel1));
        assertThrows(IllegalArgumentException.class, 
            () -> gameProgressDTO.putLevel(-1, testLevel1));
    }

    /**
     * Tests getting existing level with default fallback.
     * Verifies the existing level is returned.
     */
    @Test
    void testGetLevelOrDefaultWithExistingLevel() {
        gameProgressDTO.putLevel(1, testLevel1);
        LevelDTO retrievedLevel = gameProgressDTO.getLevelOrDefault(1);
        assertEquals(testLevel1, retrievedLevel);
    }

    /**
     * Tests getting non-existent level with default fallback.
     * Verifies default level values are returned.
     */
    @Test
    void testGetLevelOrDefaultWithNonExistingLevel() {
        LevelDTO defaultLevel = gameProgressDTO.getLevelOrDefault(1);
        assertEquals(1, defaultLevel.getLevelNumber());
        assertEquals(0, defaultLevel.getStars());
        assertTrue(defaultLevel.isUnlocked());
        
        LevelDTO defaultLevel2 = gameProgressDTO.getLevelOrDefault(2);
        assertEquals(2, defaultLevel2.getLevelNumber());
        assertEquals(0, defaultLevel2.getStars());
        assertFalse(defaultLevel2.isUnlocked());
    }

    /**
     * Tests getting with invalid level numbers using default fallback.
     * Verifies IllegalArgumentException is thrown for invalid inputs.
     */
    @Test
    void testGetLevelOrDefaultWithInvalidLevelNumber() {
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.getLevelOrDefault(0));
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.getLevelOrDefault(-1));
    }

    /**
     * Tests checking for level existence.
     * Verifies correct boolean results for existing/non-existing levels.
     */
    @Test
    void testContainsLevel() {
        assertFalse(gameProgressDTO.containsLevel(1));
        
        gameProgressDTO.putLevel(1, testLevel1);
        assertTrue(gameProgressDTO.containsLevel(1));
        assertFalse(gameProgressDTO.containsLevel(2));
    }

    /**
     * Tests checking existence with invalid level numbers.
     * Verifies IllegalArgumentException is thrown for invalid inputs.
     */
    @Test
    void testContainsLevelWithInvalidLevelNumber() {
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.containsLevel(0));
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.containsLevel(-1));
    }

    /**
     * Tests retrieving all level entries.
     * Verifies correct set of entries is returned.
     */
    @Test
    void testGetEntrySet() {
        assertTrue(gameProgressDTO.getEntrySet().isEmpty());
        
        gameProgressDTO.putLevel(1, testLevel1);
        gameProgressDTO.putLevel(2, testLevel2);
        
        Set<Map.Entry<Integer, LevelDTO>> entrySet = gameProgressDTO.getEntrySet();
        assertEquals(2, entrySet.size());
        
        for (Map.Entry<Integer, LevelDTO> entry : entrySet) {
            if (entry.getKey() == 1) {
                assertEquals(testLevel1, entry.getValue());
            } else if (entry.getKey() == 2) {
                assertEquals(testLevel2, entry.getValue());
            } else {
                fail("Unexpected level number in entry set: " + entry.getKey());
            }
        }
    }

    /**
     * Tests combined operations.
     * Verifies multiple operations work together correctly.
     */
    @Test
    void testMultipleOperations() {
        assertFalse(gameProgressDTO.containsLevel(1));
        assertNull(gameProgressDTO.getLevel(1));
        
        gameProgressDTO.putLevel(1, testLevel1);
        assertTrue(gameProgressDTO.containsLevel(1));
        assertEquals(testLevel1, gameProgressDTO.getLevel(1));
        
        gameProgressDTO.putLevel(2, testLevel2);
        assertEquals(2, gameProgressDTO.getEntrySet().size());
        
        LevelDTO defaultLevel3 = gameProgressDTO.getLevelOrDefault(3);
        assertEquals(3, defaultLevel3.getLevelNumber());
        assertFalse(defaultLevel3.isUnlocked());
    }
}