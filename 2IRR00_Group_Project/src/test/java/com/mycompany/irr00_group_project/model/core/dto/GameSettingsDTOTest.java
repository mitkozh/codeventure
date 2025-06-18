package com.mycompany.irr00_group_project.model.core.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GameSettingsDTO.
 * Verifies the functionality of game settings data transfer object.
 */
class GameSettingsDTOTest {

    /**
     * Tests the default constructor initializes fields with expected default values.
     */
    @Test
    void testDefaultConstructor() {
        GameSettingsDTO settings = new GameSettingsDTO();
        
        assertEquals("Robot", settings.getSelectedAvatar(), 
            "Default avatar should be 'Robot'");
        assertEquals(1.0, settings.getMasterVolume(), 
            "Default master volume should be 1.0");
        assertEquals(1.0, settings.getMusicVolume(), 
            "Default music volume should be 1.0");
        assertEquals(1.0, settings.getSfxVolume(), 
            "Default SFX volume should be 1.0");
    }

    /**
     * Tests the parameterized constructor sets fields correctly.
     */
    @Test
    void testParameterizedConstructor() {
        GameSettingsDTO settings = new GameSettingsDTO("Alien", 0.8, 0.6, 0.7);
        
        assertEquals("Alien", settings.getSelectedAvatar(), 
            "Avatar should be set to 'Alien'");
        assertEquals(0.8, settings.getMasterVolume(), 
            "Master volume should be set to 0.8");
        assertEquals(0.6, settings.getMusicVolume(), 
            "Music volume should be set to 0.6");
        assertEquals(0.7, settings.getSfxVolume(), 
            "SFX volume should be set to 0.7");
    }

    /**
     * Tests all getter and setter methods work correctly.
     */
    @Test
    void testGettersAndSetters() {
        GameSettingsDTO settings = new GameSettingsDTO();
        
        // Test avatar getter/setter
        settings.setSelectedAvatar("Monster");
        assertEquals("Monster", settings.getSelectedAvatar(), 
            "Avatar should be updated to 'Monster'");
        
        // Test volume getters/setters
        settings.setMasterVolume(0.5);
        assertEquals(0.5, settings.getMasterVolume(), 
            "Master volume should be updated to 0.5");
        
        settings.setMusicVolume(0.3);
        assertEquals(0.3, settings.getMusicVolume(), 
            "Music volume should be updated to 0.3");
        
        settings.setSfxVolume(0.4);
        assertEquals(0.4, settings.getSfxVolume(), 
            "SFX volume should be updated to 0.4");
    }

}