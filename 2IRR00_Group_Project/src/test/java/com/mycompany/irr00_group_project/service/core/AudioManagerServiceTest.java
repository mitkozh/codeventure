package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.service.core.impl.AudioManagerServiceImpl;
import com.mycompany.irr00_group_project.service.observable.SettingsObservables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

/**
 * JUnit test class for AudioManagerService.
 * This class tests the functionality of the AudioManagerService,
 * focusing on playing sound effects as defined in the interface.
 */
public class AudioManagerServiceTest {

    private AudioManagerServiceImpl audioManagerService;
    private SettingsObservables settingsObservables;

    /**
     * Sets up the AudioManagerService instance and accesses SettingsObservables before each test.
     */
    @BeforeEach
    void setUp() {
        audioManagerService = AudioManagerServiceImpl.getInstance();
        try {
            Field settingsField = AudioManagerServiceImpl
                .class.getDeclaredField("settingsObservables");
            settingsField.setAccessible(true);
            settingsObservables = (SettingsObservables) settingsField.get(audioManagerService);
            settingsObservables.setMasterVolume(1.0);
            settingsObservables.setSfxVolume(1.0);
        } catch (Exception e) {
            fail("Failed to access or set SettingsObservables: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithValidPath() {
        try {
            audioManagerService.playSfx("sounds/effect1.wav");
        } catch (Exception e) {
            fail("Failed to play sound effect: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithNullPath() {
        try {
            audioManagerService.playSfx(null);
            fail("Expected an exception when playing with null path");
        } catch (IllegalArgumentException e) {
            assertEquals("Sound path cannot be null or empty", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception when playing with null path: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithEmptyPath() {
        try {
            audioManagerService.playSfx("");
            fail("Expected an exception when playing with empty path");
        } catch (IllegalArgumentException e) {
            assertEquals("Sound path cannot be null or empty", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception when playing with empty path: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithInvalidExtension() {
        try {
            audioManagerService.playSfx("sounds/effect1.txt");
        } catch (Exception e) {
            fail("Unexpected exception when playing with invalid extension: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithRelativePath() {
        try {
            audioManagerService.playSfx("../sounds/effect1.wav");
        } catch (Exception e) {
            fail("Unexpected exception when playing with relative path: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithSpecialCharacters() {
        try {
            audioManagerService.playSfx("sounds/effect@#$.wav");
        } catch (Exception e) {
            fail("Unexpected exception when playing with special characters: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxMultipleTimes() {
        try {
            audioManagerService.playSfx("sounds/effect1.wav");
            audioManagerService.playSfx("sounds/effect1.wav");
            audioManagerService.playSfx("sounds/effect1.wav");
        } catch (Exception e) {
            fail("Unexpected exception when playing multiple times: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithLongPath() {
        try {
            String longPath = "sounds/very/long/path/to/effect1.wav".repeat(10);
            audioManagerService.playSfx(longPath);
        } catch (Exception e) {
            fail("Unexpected exception when playing with long path: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithNonExistentPath() {
        try {
            audioManagerService.playSfx("sounds/nonexistent.wav");
        } catch (Exception e) {
            fail("Unexpected exception when playing with non-existent path: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithZeroVolume() {
        try {
            settingsObservables.setMasterVolume(0.0);
            settingsObservables.setSfxVolume(0.0);
            audioManagerService.playSfx("sounds/effect1.wav");
        } catch (Exception e) {
            fail("Unexpected exception when playing with zero volume: " + e.getMessage());
        }
    }

    @Test
    void testPlaySfxWithMultipleConcurrentClips() {
        try {
            audioManagerService.playSfx("sounds/effect1.wav");
            Thread.sleep(100);
            audioManagerService.playSfx("sounds/effect2.wav");
        } catch (Exception e) {
            fail("Unexpected exception when playing multiple concurrent clips: " + e.getMessage());
        }
    }
}