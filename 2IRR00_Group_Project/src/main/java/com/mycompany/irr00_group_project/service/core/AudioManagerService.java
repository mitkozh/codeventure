package com.mycompany.irr00_group_project.service.core;

/**
 * .
 */
public interface AudioManagerService {

    /**
     * Plays the background music.
     */
    void playBackgroundMusic();

    /**
     * Stops the background music.
     */
    void stopBackgroundMusic();

    /**
     * Plays the sound effect for a successful action.
     */
    void playSuccessSound();

    /**
     * Plays the sound effect for a failed action.
     */
    void playFailureSound();

    /**
     * Plays the sound effect for a button click.
     */
    void playButtonClickSound();

    void setGlobalVolume(double newVolume);
}