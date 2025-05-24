package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.service.core.AudioManagerService;

/**
 * Implementation of the AudioManagerService interface.
 * This class provides methods to manage audio settings such as master volume, 
 * music volume, and sound effects volume.
 */
public class AudioManagerServiceImpl implements AudioManagerService {

    @Override
    public void setMasterVolume(double newVolume) {
        // Implementation for setting master volume
        System.out.println("Master volume set to: " + newVolume);
    }

    @Override
    public void setMusicVolume(double newVolume) {
        // Implementation for setting music volume
        System.out.println("Music volume set to: " + newVolume);
    }

    @Override
    public void setSfxVolume(double newVolume) {
        // Implementation for setting sound effects volume
        System.out.println("SFX volume set to: " + newVolume);
    }
    
}
