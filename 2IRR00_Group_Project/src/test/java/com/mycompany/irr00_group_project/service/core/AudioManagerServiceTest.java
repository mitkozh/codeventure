//package com.mycompany.irr00_group_project.service.core;
//
//import com.mycompany.irr00_group_project.service.core.impl.AudioManagerServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * JUnit test class for AudioManagerService.
// * This class tests the functionality of the AudioManagerService,
// * including setting and getting volume levels for master, music, and sound effects.
// */
//public class AudioManagerServiceTest {
//
//    private AudioManagerServiceImpl audioManagerService;
//
//    /**
//     * Sets up the AudioManagerService instance before each test.
//     * This method initializes the service and resets volume levels to default.
//     */
//    @BeforeEach
//    void setUp() {
//        audioManagerService = AudioManagerServiceImpl.getInstance();
//        // Reset volumes to default for each test
//        audioManagerService.(1.0);
//        audioManagerService.setMusicVolume(1.0);
//        audioManagerService.setSfxVolume(1.0);
//    }
//
//    @Test
//    void testSetMasterVolumeWithinBounds() {
//        audioManagerService.setMasterVolume(0.5);
//        assertEquals(0.5, audioManagerService.getMasterVolume(), 0.0001);
//    }
//
//    @Test
//    void testSetMasterVolumeBelowZero() {
//        audioManagerService.setMasterVolume(-1.0);
//        assertEquals(0.0, audioManagerService.getMasterVolume(), 0.0001);
//    }
//
//    @Test
//    void testSetMasterVolumeAboveOne() {
//        audioManagerService.setMasterVolume(2.0);
//        assertEquals(1.0, audioManagerService.getMasterVolume(), 0.0001);
//    }
//
//    @Test
//    void testSetMusicVolumeWithinBounds() {
//        audioManagerService.setMusicVolume(0.3);
//        assertEquals(0.3, audioManagerService.getMusicVolume(), 0.0001);
//    }
//
//    @Test
//    void testSetMusicVolumeBelowZero() {
//        audioManagerService.setMusicVolume(-0.5);
//        assertEquals(0.0, audioManagerService.getMusicVolume(), 0.0001);
//    }
//
//    @Test
//    void testSetMusicVolumeAboveOne() {
//        audioManagerService.setMusicVolume(1.5);
//        assertEquals(1.0, audioManagerService.getMusicVolume(), 0.0001);
//    }
//
//    @Test
//    void testSetSfxVolumeWithinBounds() {
//        audioManagerService.setSfxVolume(0.7);
//        assertEquals(0.7, audioManagerService.getSfxVolume(), 0.0001);
//    }
//
//    @Test
//    void testSetSfxVolumeBelowZero() {
//        audioManagerService.setSfxVolume(-0.2);
//        assertEquals(0.0, audioManagerService.getSfxVolume(), 0.0001);
//    }
//
//    @Test
//    void testSetSfxVolumeAboveOne() {
//        audioManagerService.setSfxVolume(2.0);
//        assertEquals(1.0, audioManagerService.getSfxVolume(), 0.0001);
//    }
//}
