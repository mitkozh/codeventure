package com.mycompany.irr00_group_project.utils;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.TileType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ParseUtils class.
 */
public class ParseUtilsTest {

    private static final String MOCK_LEVEL_CONTENT = """
            SIZE 5 5
            START 0 0 NORTH
            END 4 4
            OPTIMAL_STEPS
            10
            OBSTACLES
            1 1
            DOORS
            2 2
            KEYS
            3 3
            """;

    @BeforeEach
    void setUp() throws Exception {
        // Mock getResourceAsStream to return a mock level file
        Field field = Class.class.getDeclaredField("getResourceAsStream");
        field.setAccessible(true);
        field.set(ParseUtils.class, (name) -> new ByteArrayInputStream(MOCK_LEVEL_CONTENT.getBytes()));
    }

    @AfterEach
    void tearDown() throws Exception {
        // Reset the mocked field
        Field field = Class.class.getDeclaredField("getResourceAsStream");
        field.setAccessible(true);
        field.set(ParseUtils.class, null);
    }

    @Test
    void testParseLevelValidInput() throws IOException {
        LevelDTO levelDTO = new LevelDTO(1);
        LevelData levelData = ParseUtils.parseLevel(levelDTO);

        assertEquals(5, levelData.getWidth(), "Width should be 5");
        assertEquals(5, levelData.getHeight(), "Height should be 5");
        assertEquals(0, levelData.getStartRow(), "Start row should be 0");
        assertEquals(0, levelData.getStartCol(), "Start column should be 0");
        assertEquals(Direction.NORTH, levelData.getStartDirection(), 
            "Start direction should be NORTH");
        assertEquals(4, levelData.getEndRow(), "End row should be 4");
        assertEquals(4, levelData.getEndCol(), "End column should be 4");
        assertEquals(10, levelData.getOptimalSteps(), "Optimal steps should be 10");
        assertEquals(TileType.OBSTACLE, levelData.getTile(1, 1),
            "Tile at (1,1) should be OBSTACLE");
        assertEquals(TileType.DOOR_CLOSED, levelData.getTile(2, 2),
             "Tile at (2,2) should be DOOR_CLOSED");
        assertEquals(TileType.KEY, levelData.getTile(3, 3), "Tile at (3,3) should be KEY");
    }

    @Test
    void testParseLevelMissingFile() throws Exception {
        // Set getResourceAsStream to return null
        Field field = Class.class.getDeclaredField("getResourceAsStream");
        field.setAccessible(true);
        field.set(ParseUtils.class, (name) -> null);

        LevelDTO levelDTO = new LevelDTO(1);
        IOException exception = assertThrows(IOException.class, () 
            -> ParseUtils.parseLevel(levelDTO));
        assertEquals("Could not find level file: level1.txt", exception.getMessage());
    }

    @Test
    void testParseLevelEmptySection() throws Exception {
        String emptySectionContent = """
                SIZE 3 3
                START 0 0 EAST
                END 2 2
                OBSTACLES
                DOORS
                KEYS
                """;
        Field field = Class.class.getDeclaredField("getResourceAsStream");
        field.setAccessible(true);
        field.set(ParseUtils.class, (name) 
            -> new ByteArrayInputStream(emptySectionContent.getBytes()));

        LevelDTO levelDTO = new LevelDTO(1);
        LevelData levelData = ParseUtils.parseLevel(levelDTO);
        assertEquals(3, levelData.getWidth(), "Width should be 3");
        assertEquals(3, levelData.getHeight(), "Height should be 3");
    }

    @Test
    void testParseLevelInvalidSizeFormat() throws Exception {
        String invalidContent = """
                SIZE invalid 5
                START 0 0 NORTH
                END 4 4
                """;
        Field field = Class.class.getDeclaredField("getResourceAsStream");
        field.setAccessible(true);
        field.set(ParseUtils.class, (name) -> new ByteArrayInputStream(invalidContent.getBytes()));

        LevelDTO levelDTO = new LevelDTO(1);
        assertDoesNotThrow(() 
            -> ParseUtils.parseLevel(levelDTO), "Should handle invalid SIZE gracefully");
    }

    @Test
    void testParseLevelCommentsAndEmptyLines() throws Exception {
        String contentWithComments = """
                # Comment
                SIZE 4 4
                
                # Another comment
                START 0 0 SOUTH
                END 3 3
                OPTIMAL_STEPS
                8
                """;
        Field field = Class.class.getDeclaredField("getResourceAsStream");
        field.setAccessible(true);
        field.set(ParseUtils.class, (name) 
            -> new ByteArrayInputStream(contentWithComments.getBytes()));

        LevelDTO levelDTO = new LevelDTO(1);
        LevelData levelData = ParseUtils.parseLevel(levelDTO);
        assertEquals(4, levelData.getWidth(), "Width should be 4");
        assertEquals(8, levelData.getOptimalSteps(), "Optimal steps should be 8");
    }
}