package com.mycompany.irr00_group_project.service.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.service.core.impl.SettingsServiceImpl;

/**
 * Junit test class for SettingsService.
 * This class tests the functionality of the SettingsService,
 * including setting and getting avatar, volume levels,
 * and ensuring persistence of settings.
 */
public class SettingsServiceTest {

    private SettingsService settingsService;

    /**
     * Sets up the SettingsService instance before each test.
     */
    @BeforeEach
    void setUp() {
        settingsService = SettingsServiceImpl.getInstance();
        settingsService.setSelectedAvatar("Robot");
        settingsService.setMasterVolume(1.0);
        settingsService.setMusicVolume(1.0);
        settingsService.setSfxVolume(1.0);
        settingsService.saveCurrentSettings();
    }

    @Test
    void testSetAndGetSelectedAvatar() {
        settingsService.setSelectedAvatar("Alien");
        assertEquals("Alien", settingsService.getSelectedAvatar());
    }

    @Test
    void testSetAndGetMasterVolume() {
        settingsService.setMasterVolume(0.5);
        assertEquals(0.5, settingsService.getMasterVolume(), 0.0001);
    }

    @Test
    void testSetAndGetMusicVolume() {
        settingsService.setMusicVolume(0.3);
        assertEquals(0.3, settingsService.getMusicVolume(), 0.0001);
    }

    @Test
    void testSetAndGetSfxVolume() {
        settingsService.setSfxVolume(0.7);
        assertEquals(0.7, settingsService.getSfxVolume(), 0.0001);
    }

    @Test
    void testVolumeBounds() {
        settingsService.setMasterVolume(-1.0);
        assertEquals(0.0, settingsService.getMasterVolume(), 0.0001);

        settingsService.setMusicVolume(2.0);
        assertEquals(1.0, settingsService.getMusicVolume(), 0.0001);

        settingsService.setSfxVolume(-0.5);
        assertEquals(0.0, settingsService.getSfxVolume(), 0.0001);
    }

    @Test
    void testPersistence() {
        settingsService.setSelectedAvatar("Cool alien");
        settingsService.setMasterVolume(0.8);
        settingsService.setMusicVolume(0.6);
        settingsService.setSfxVolume(0.4);
        settingsService.saveCurrentSettings();

        // Simulate reload
        settingsService.setSelectedAvatar("Robot");
        settingsService.setMasterVolume(1.0);
        settingsService.setMusicVolume(1.0);
        settingsService.setSfxVolume(1.0);

        settingsService.loadPersistedSettings();

        assertEquals("Cool alien", settingsService.getSelectedAvatar());
        assertEquals(0.8, settingsService.getMasterVolume(), 0.0001);
        assertEquals(0.6, settingsService.getMusicVolume(), 0.0001);
        assertEquals(0.4, settingsService.getSfxVolume(), 0.0001);
    }
}
