package com.mycompany.irr00_group_project.utils;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.enums.Direction;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ParseUtils class.
 * This class tests the parsing methods for LevelData.
 */
public class ParseUtilsTest {

    @Test
    void testSizeParsing() throws Exception {
        LevelData levelData = new LevelData();
        String[] parts = {"SIZE", "4", "6"};
        Method method = ParseUtils.class.getDeclaredMethod(
            "handleIndividualPart", String[].class, LevelData.class, String.class);
        method.setAccessible(true);
        method.invoke(null, parts, levelData, "");

        assertEquals(4, levelData.getWidth());
        assertEquals(6, levelData.getHeight());
    }

    @Test
    void testStartParsing() throws Exception {
        LevelData levelData = new LevelData();
        String[] parts = {"START", "2", "3", "SOUTH"};
        Method method = ParseUtils.class.getDeclaredMethod(
            "handleIndividualPart", String[].class, LevelData.class, String.class);
        method.setAccessible(true);
        method.invoke(null, parts, levelData, "");

        assertEquals(2, levelData.getStartRow());
        assertEquals(3, levelData.getStartCol());
        assertEquals(Direction.SOUTH, levelData.getStartDirection());
    }

    @Test
    void testEndParsing() throws Exception {
        LevelData levelData = new LevelData();
        String[] parts = {"END", "5", "5"};
        Method method = ParseUtils.class.getDeclaredMethod(
            "handleIndividualPart", String[].class, LevelData.class, String.class);
        method.setAccessible(true);
        method.invoke(null, parts, levelData, "");

        assertEquals(5, levelData.getEndRow());
        assertEquals(5, levelData.getEndCol());
    }

    @Test
    void testInvalidSize() throws Exception {
        LevelData levelData = new LevelData();
        String[] parts = {"SIZE", "bad", "5"};
        Method method = ParseUtils.class.getDeclaredMethod(
            "handleIndividualPart", String[].class, LevelData.class, String.class);
        method.setAccessible(true);

        Throwable thrown = assertThrows(Exception.class, () 
            -> method.invoke(null, parts, levelData, ""));
        Throwable cause = thrown.getCause();
        assertNotNull(cause);
        assertTrue(cause instanceof NumberFormatException);
    }

    @Test
    void testInvalidDirection() throws Exception {
        LevelData levelData = new LevelData();
        String[] parts = {"START", "1", "1", "BAD"};
        Method method = ParseUtils.class.getDeclaredMethod(
            "handleIndividualPart", String[].class, LevelData.class, String.class);
        method.setAccessible(true);

        Throwable thrown = assertThrows(Exception.class, () 
            -> method.invoke(null, parts, levelData, ""));
        Throwable cause = thrown.getCause();
        assertNotNull(cause);
        assertTrue(cause instanceof IllegalArgumentException);
    }
}