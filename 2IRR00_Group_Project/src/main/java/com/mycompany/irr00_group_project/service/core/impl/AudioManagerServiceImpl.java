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
import com.mycompany.irr00_group_project.service.observable.SettingsObservables;
import com.mycompany.irr00_group_project.utils.Constants;
import com.mycompany.irr00_group_project.utils.StringUtils;

/**
 * Class which implements the respective interface and manages the sound levels of the game.
 */
public class AudioManagerServiceImpl implements AudioManagerService {

    private Clip mainMenuMusicClip;
    private final SettingsObservables settingsObservables;
    private static AudioManagerServiceImpl instance;

    /**
     * Private constructor to initialize the AudioManagerServiceImpl.
     * It fetches the observable settings and sets up listeners to react to changes.
     */
    private AudioManagerServiceImpl() {
        SettingsServiceImpl settingsService = SettingsServiceImpl.getInstance();
        this.settingsObservables = settingsService.getObservableRegistry()
                .getOrThrow(SettingsObservables.class);
        setupObservables();
        initializeBackgroundMusic();
    }

    /**
     * Sets up listeners on the observable properties to automatically update volume.
     */
    private void setupObservables() {
        settingsObservables.masterVolumeProperty().addListener(
                (obs, oldVal, newVal) -> updateMusicClipVolume());
        settingsObservables.musicVolumeProperty().addListener(
                (obs, oldVal, newVal) -> updateMusicClipVolume());
    }

    /**
     * Loads the main menu music, sets its initial volume, and plays it in a loop.
     */
    private void initializeBackgroundMusic() {
        try {
            String soundPath = Constants.BACKGROUND_MUSIC_LOCATION;
            URL soundURL = getClass().getResource(soundPath);
            if (soundURL != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                mainMenuMusicClip = AudioSystem.getClip();
                mainMenuMusicClip.open(audioIn);
                updateMusicClipVolume();
                mainMenuMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                mainMenuMusicClip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Failed to play background sound: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * method to publicly generate AudioManagerServiceImpl.
     */
    public static synchronized AudioManagerServiceImpl getInstance() {
        if (instance == null) {
            instance = new AudioManagerServiceImpl();
        }
        return instance;
    }

    /**
     * Updates the volume of the music clip based on the current master and music volume settings.
     */
    private void updateMusicClipVolume() {
        if (mainMenuMusicClip != null && mainMenuMusicClip.isOpen()) {
            double masterVolume = settingsObservables.getMasterVolume();
            double musicVolume = settingsObservables.getMusicVolume();
            double effectiveVolume = masterVolume * musicVolume;
            FloatControl volumeControl = (FloatControl) 
                mainMenuMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            if (effectiveVolume > 0.0) {
                float dB = (float) (Math.log10(effectiveVolume) * 20.0);
                volumeControl.setValue(Math.max(min, Math.min(max, dB)));
            } else {
                volumeControl.setValue(min);
            }
        }
    }

    @Override
    public void playSfx(String soundPath) {
        try {
            if (StringUtils.isNullOrEmpty(soundPath)) {
                throw new IllegalArgumentException("Sound path cannot be null or empty");
            }
            URL soundURL = getClass().getResource(soundPath);
            if (soundURL != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);

                // Set volume based on master and sfx volume
                double masterVolume = settingsObservables.getMasterVolume();
                double sfxVolume = settingsObservables.getSfxVolume();
                double effectiveVolume = masterVolume * sfxVolume;
                FloatControl volumeControl = (FloatControl) clip.getControl(
                    FloatControl.Type.MASTER_GAIN);
                float min = volumeControl.getMinimum();
                float max = volumeControl.getMaximum();
                if (effectiveVolume > 0.0) {
                    float dB = (float) (Math.log10(effectiveVolume) * 20.0);
                    volumeControl.setValue(Math.max(min, Math.min(max, dB)));
                } else {
                    volumeControl.setValue(min);
                }

                clip.start();
            }
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("Unsupported audio file format: " + soundPath, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading audio file: " + soundPath, e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Audio line  for sound: " + soundPath, e);
        }
    }
}
