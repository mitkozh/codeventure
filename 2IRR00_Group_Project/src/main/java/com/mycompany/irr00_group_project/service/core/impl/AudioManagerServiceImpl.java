package com.mycompany.irr00_group_project.service.core.impl;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.mycompany.irr00_group_project.service.core.AudioManagerService;
import com.mycompany.irr00_group_project.service.core.SettingsService;

/**
 * Implementation of the AudioManagerService interface.
 * This class provides methods to manage audio settings such as master volume,
 * music volume, and sound effects volume.
 */
public class AudioManagerServiceImpl implements AudioManagerService {

    private Clip mainMenuMusicClip;
    private double masterVolume = 1.0; // Default volume level
    private double musicVolume = 1.0; // Default music volume level
    private double sfxVolume = 1.0;

    private static AudioManagerServiceImpl instance;

    /**
     * Constructor that initializes the audio manager service.
     * It attempts to load and play the background music for the main menu.
     */
    public AudioManagerServiceImpl() {
        try {
            SettingsService settings = SettingsServiceImpl.getInstance();
            this.masterVolume = settings.getMasterVolume();
            this.musicVolume = settings.getMusicVolume();
            this.sfxVolume = settings.getSfxVolume();
            System.out.println("AudioManagerServiceImpl: Initialized internal volumes from SettingsService: M=" +
                    this.masterVolume + ", Mu=" + this.musicVolume +
                    ", S=" + this.sfxVolume);
        } catch (Exception e) {
            System.err.println("AudioManagerServiceImpl: Error getting initial settings from SettingsService. Using internal defaults. Error: " + e.getMessage());
            // Internal defaults (1.0) will be used if this fails
        }

        try {
            String soundPath =
                    "/com/mycompany/irr00_group_project/assets/sounds/MainMenuMusic.wav";
            URL soundURL = getClass().getResource(soundPath);
            if (soundURL != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                mainMenuMusicClip = AudioSystem.getClip();
                mainMenuMusicClip.open(audioIn);
                updateMusicClipVolume(); // Apply initial default volumes
                mainMenuMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop forever
                mainMenuMusicClip.start();
            } else {
                System.err.println("Background sound file not found: " + soundPath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Failed to play background sound: " + e.getMessage());
        }

    }

    public static synchronized AudioManagerServiceImpl getInstance() {
        if (instance == null) {
            instance = new AudioManagerServiceImpl();
        }
        return instance;
    }
    /**
     * Starts the main menu music if it is not already playing.
     */
    private void updateMusicClipVolume() {
        if (mainMenuMusicClip != null && mainMenuMusicClip.isOpen()) {
            double effectiveVolume = masterVolume * musicVolume;
            FloatControl volumeControl =
                (FloatControl) mainMenuMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float dB;
            if (effectiveVolume == 0.0) {
                dB = min;
            } else {
                dB = (float) (Math.log10(effectiveVolume) * 20.0);
                if (dB < min) {
                    dB = min;
                }
                if (dB > max) {
                    dB = max;
                }
            }
            volumeControl.setValue(dB);
        }
    }

    @Override
    public void setMasterVolume(double newVolume) {
        // Implementation for setting master volume
        this.masterVolume = Math.max(0.0, Math.min(1.0, newVolume));
        updateMusicClipVolume();
        System.out.println("Master volume set to: " + newVolume);
    }

    @Override
    public void setMusicVolume(double newVolume) {
        this.musicVolume = Math.max(0.0, Math.min(1.0, newVolume));
        musicVolume = newVolume;
        updateMusicClipVolume();
        System.out.println("Music volume set to: " + newVolume);
    }

    @Override
    public void setSfxVolume(double newVolume) {
        this.sfxVolume = Math.max(0.0, Math.min(1.0, newVolume));
        // Implementation for setting sound effects volume
        // effectiveSfxVolume = masterVolume * sfxVolume;
        System.out.println("AudioManager: SFX volume set to: " + this.sfxVolume);
    }

}
