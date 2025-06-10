package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.dto.GameSettingsDTO;

/**
 * Interface for managing settings changes.
 */
public interface SettingsService {

    /**
     * Gets the current game settings.
     * @return GameSettingsDTO containing all current settings
     */
    GameSettingsDTO getSettings();

    /**
     * Gets the selected avatar name.
     * @return the currently selected avatar
     */
    String getSelectedAvatar();

    /**
     * Sets the selected avatar.
     * @param avatarName the avatar name to set
     */
    void setSelectedAvatar(String avatarName);

    /**
     * Gets the master volume level.
     * @return master volume (0.0 to 1.0)
     */
    double getMasterVolume();

    /**
     * Sets the master volume level.
     * @param volume the volume level (0.0 to 1.0)
     */
    void setMasterVolume(double volume);

    /**
     * Gets the music volume level.
     * @return music volume (0.0 to 1.0)
     */
    double getMusicVolume();

    /**
     * Sets the music volume level.
     * @param volume the volume level (0.0 to 1.0)
     */
    void setMusicVolume(double volume);

    /**
     * Gets the SFX volume level.
     * @return SFX volume (0.0 to 1.0)
     */
    double getSfxVolume();

    /**
     * Sets the SFX volume level.
     * @param volume the volume level (0.0 to 1.0)
     */
    void setSfxVolume(double volume);

    /**
     * Saves the current settings to persistent storage.
     */
    void saveCurrentSettings();

    /**
     * Loads settings from persistent storage.
     */
    void loadPersistedSettings();
}