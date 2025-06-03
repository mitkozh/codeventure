package com.mycompany.irr00_group_project.service.core;

/**
 * Interface for audio managing process.
 */
public interface AudioManagerService {

    void setMasterVolume(double newVolume);

    void setMusicVolume(double newVolume);

    void setSfxVolume(double newVolume);
}