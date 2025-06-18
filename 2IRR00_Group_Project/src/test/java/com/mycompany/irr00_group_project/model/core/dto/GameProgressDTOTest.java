package com.mycompany.irr00_group_project.model.core.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
     * Tests adding valid level data and retrieving it.
     * Verifies levels can be added and retrieved correctly.
     */
    @Test
    void testPutAndGetLevelWithValidData() {
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
     * Tests checking existence with invalid level numbers.
     * Verifies IllegalArgumentException is thrown for invalid inputs.
     */
    @Test
    void testContainsLevelWithInvalidLevelNumber() {
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.containsLevel(0));
        assertThrows(IllegalArgumentException.class, () -> gameProgressDTO.containsLevel(-1));
    }
}