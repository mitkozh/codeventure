package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * SettingsObservables provides observable properties for game settings.
 * It allows other components to observe and react to changes in settings.
 */
public class SettingsObservables {
    private final StringProperty selectedAvatar = new SimpleStringProperty("Robot");
    private final DoubleProperty masterVolume = new SimpleDoubleProperty(1.0);
    private final DoubleProperty musicVolume = new SimpleDoubleProperty(1.0);
    private final DoubleProperty sfxVolume = new SimpleDoubleProperty(1.0);

    public StringProperty selectedAvatarProperty() {
        return selectedAvatar;
    }

    public void setSelectedAvatar(String avatar) {
        selectedAvatar.set(avatar);
    }

    public String getSelectedAvatar() {
        return selectedAvatar.get();
    }

    // --- Master Volume ---
    public DoubleProperty masterVolumeProperty() {
        return masterVolume;
    }

    public void setMasterVolume(double volume) {
        masterVolume.set(volume);
    }

    public double getMasterVolume() {
        return masterVolume.get();
    }

    public DoubleProperty musicVolumeProperty() {
        return musicVolume;
    }

    public void setMusicVolume(double volume) {
        musicVolume.set(volume);
    }

    public double getMusicVolume() {
        return musicVolume.get();
    }

    public DoubleProperty sfxVolumeProperty() {
        return sfxVolume;
    }

    public void setSfxVolume(double volume) {
        sfxVolume.set(volume);
    }

    public double getSfxVolume() {
        return sfxVolume.get();
    }
}
