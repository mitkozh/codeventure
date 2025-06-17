package com.mycompany.irr00_group_project.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ConstantsResources class.
 * This class tests the resource paths defined in ConstantsResources.
 */
public class ConstantsResourcesTest {

    @Test
    void testGeometricBackground() {
        String expected = 
            "/com/mycompany/irr00_group_project/assets/images/geometric_background.png";
        assertEquals(expected, ConstantsResources.GEOMETRIC_BACKGROUND,
            "GEOMETRIC_BACKGROUND should match expected path");
    }

    @Test
    void testGameBezel() {
        String expected = "/com/mycompany/irr00_group_project/assets/images/game_bezel.png";
        assertEquals(expected, ConstantsResources.GAME_BEZEL,
            "GAME_BEZEL should match expected path");
    }

    @Test
    void testBackgroundImage() {
        String expected = "/com/mycompany/irr00_group_project/assets/images/background.jpg";
        assertEquals(expected, ConstantsResources.BACKGROUND_IMAGE,
            "BACKGROUND_IMAGE should match expected path");
    }

    @Test
    void testGeometricBackgroundNotNull() {
        assertNotNull(ConstantsResources.GEOMETRIC_BACKGROUND,
            "GEOMETRIC_BACKGROUND should not be null");
    }

    @Test
    void testGameBezelNotEmpty() {
        assertFalse(ConstantsResources.GAME_BEZEL.isEmpty(), "GAME_BEZEL should not be empty");
    }
}
