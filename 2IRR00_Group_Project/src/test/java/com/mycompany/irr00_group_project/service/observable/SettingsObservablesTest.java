package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsObservablesTest {

    private SettingsObservables settingsObservables;

    @BeforeEach
    void setUp() {
        settingsObservables = new SettingsObservables();
    }

    @Test
    void testSetSelectedAvatarUpdatesProperty() {
        String avatar = "Player";
        settingsObservables.setSelectedAvatar(avatar);

        StringProperty selectedAvatar = settingsObservables.selectedAvatarProperty();
        assertEquals(avatar, selectedAvatar.get(),
            "SelectedAvatar property should match set value");
        assertEquals(avatar, settingsObservables.getSelectedAvatar(),
            "getSelectedAvatar should return set value");
    }

    @Test
    void testSetMasterVolumeUpdatesProperty() {
        double volume = 0.5;
        settingsObservables.setMasterVolume(volume);

        DoubleProperty masterVolume = settingsObservables.masterVolumeProperty();
        assertEquals(volume, masterVolume.get(), 0.001,
            "MasterVolume property should match set value");
        assertEquals(volume, settingsObservables.getMasterVolume(),
            0.001, "getMasterVolume should return set value");
    }

    @Test
    void testSetMusicVolumeUpdatesProperty() {
        double volume = 0.7;
        settingsObservables.setMusicVolume(volume);

        DoubleProperty musicVolume = settingsObservables.musicVolumeProperty();
        assertEquals(volume, musicVolume.get(), 0.001,
            "MusicVolume property should match set value");
        assertEquals(volume, settingsObservables.getMusicVolume(),
            0.001, "getMusicVolume should return set value");
    }

    @Test
    void testSetSfxVolumeUpdatesProperty() {
        double volume = 0.3;
        settingsObservables.setSfxVolume(volume);

        DoubleProperty sfxVolume = settingsObservables.sfxVolumeProperty();
        assertEquals(volume, sfxVolume.get(), 0.001,
            "SfxVolume property should match set value");
        assertEquals(volume, settingsObservables.getSfxVolume(),
            0.001, "getSfxVolume should return set value");
    }

    @Test
    void testInitialState() {
        assertEquals("Robot", settingsObservables.getSelectedAvatar(),
            "Initial SelectedAvatar should be 'Robot'");
        assertEquals(1.0, settingsObservables.getMasterVolume(),
            0.001, "Initial MasterVolume should be 1.0");
        assertEquals(1.0, settingsObservables.getMusicVolume(),
            0.001, "Initial MusicVolume should be 1.0");
        assertEquals(1.0, settingsObservables.getSfxVolume(),
            0.001, "Initial SfxVolume should be 1.0");
    }
}