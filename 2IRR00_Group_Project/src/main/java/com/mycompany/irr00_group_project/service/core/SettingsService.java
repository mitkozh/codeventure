package com.mycompany.irr00_group_project.service.core;

/**
 * .
 */
public interface SettingsService {

    String getSelectedAvatar();
    void setSelectedAvatar(String avatarName);

    double getMasterVolume();
    void setMasterVolume(double volume);

    double getMusicVolume();
    void setMusicVolume(double volume);

    double getSfxVolume();
    void setSfxVolume(double volume);

    void saveCurrentSettings();    // saves the current state of the service to persistence
    void loadPersistedSettings();
}
