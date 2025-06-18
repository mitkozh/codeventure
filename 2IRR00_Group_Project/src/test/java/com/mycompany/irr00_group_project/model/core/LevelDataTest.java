package com.mycompany.irr00_group_project.model.core;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.TileType;

/**
 * Tests for LevelData.
 * Verifies level grid initialization, tile management, and door-key pairing.
 */
public class LevelDataTest {

    private LevelData levelData;

    @BeforeEach
    void setUp() {
        levelData = new LevelData();
    }

    /**
     * Tests grid initialization.
     * Verifies correct dimensions and default tile values.
     */
    @Test
    void testSetSize() {
        levelData.setSize(5, 5);
        assertEquals(5, levelData.getWidth());
        assertEquals(5, levelData.getHeight());
        assertEquals(TileType.NORMAL, levelData.getGrid()[0][0]);
    }

    /**
     * Tests start position setting.
     * Verifies position, direction, and tile type.
     */
    @Test
    void testSetStart() {
        levelData.setSize(5, 5);
        levelData.setStart(1, 1, Direction.NORTH);
        assertEquals(1, levelData.getStartRow());
        assertEquals(1, levelData.getStartCol());
        assertEquals(Direction.NORTH, levelData.getStartDirection());
        assertEquals(TileType.START, levelData.getGrid()[1][1]);
    }

    /**
     * Tests end position setting.
     * Verifies position and tile type.
     */
    @Test
    void testSetEnd() {
        levelData.setSize(5, 5);
        levelData.setEnd(4, 4);
        assertEquals(4, levelData.getEndRow());
        assertEquals(4, levelData.getEndCol());
        assertEquals(TileType.END, levelData.getGrid()[4][4]);
    }

    /**
     * Tests key and door management.
     * Verifies correct storage and retrieval.
     */
    @Test
    void testKeyDoorManagement() {
        levelData.addKey(1, 1);
        levelData.addDoor(2, 2);
        
        List<Point> keys = levelData.getKeys();
        List<Point> doors = levelData.getDoors();
        
        assertEquals(1, keys.size());
        assertEquals(1, doors.size());
        assertEquals(new Point(1, 1), keys.get(0));
    }

    /**
     * Tests door-key pairing.
     * Verifies correct pair creation and mapping.
     */
    @Test
    void testDoorKeyPairs() {
        levelData.addKey(1, 1);
        levelData.addKey(2, 2);
        levelData.addDoor(3, 3);
        levelData.addDoorKeyPairs();
        
        Map<Point, Point> pairs = levelData.getDoorKeyPair();
        assertEquals(1, pairs.size());
        assertTrue(pairs.containsKey(new Point(3, 3)));
    }

    /**
     * Tests optimal steps management.
     * Verifies value storage and retrieval.
     */
    @Test
    void testOptimalSteps() {
        levelData.setOptimalSteps(10);
        assertEquals(10, levelData.getOptimalSteps());
    }
}