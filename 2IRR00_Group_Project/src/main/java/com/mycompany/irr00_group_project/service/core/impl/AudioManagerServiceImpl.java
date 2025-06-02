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
 * 
 * 
 * 
 */
public class AudioManagerServiceImpl implements AudioManagerService {

    private Clip mainMenuMusicClip;
    private double masterVolume = 1.0; 
    private double musicVolume = 1.0; 
    private double sfxVolume = 1.0;

    private static AudioManagerServiceImpl instance;

    /**
     * 
     * 
     */
    private AudioManagerServiceImpl() {

        try {
            SettingsServiceImpl settings = SettingsServiceImpl.getInstance();
            this.masterVolume = settings.getMasterVolume();
            this.musicVolume = settings.getMusicVolume();
            this.sfxVolume = settings.getSfxVolume();

            settings.setVolumeChangeListener(new SettingsServiceImpl.VolumeChangeListener() {
                @Override
                public void onMasterVolumeChanged(double newVolume) {
                    setMasterVolume(newVolume);
                }

                @Override
                public void onMusicVolumeChanged(double newVolume) {
                    setMusicVolume(newVolume);
                }

                @Override
                public void onSfxVolumeChanged(double newVolume) {
                    setSfxVolume(newVolume);
                }
            });

            System.out.println("AudioManagerServiceImpl: Initialized internal volumes from SettingsService: M=" +
                    this.masterVolume + ", Mu=" + this.musicVolume +
                    ", S=" + this.sfxVolume);
        } catch (Exception e) {
            System.err.println("AudioManagerServiceImpl: Error getting initial settings from SettingsService. Using internal defaults. Error: " + e.getMessage());
        }

        try {
            String soundPath = "/com/mycompany/irr00_group_project/assets/sounds/MainMenuMusic.wav";
            URL soundURL = getClass().getResource(soundPath);
            if (soundURL != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                mainMenuMusicClip = AudioSystem.getClip();
                mainMenuMusicClip.open(audioIn);
                updateMusicClipVolume(); 
                mainMenuMusicClip.loop(Clip.LOOP_CONTINUOUSLY); 
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

    private void updateMusicClipVolume() {
        if (mainMenuMusicClip != null && mainMenuMusicClip.isOpen()) {
            double effectiveVolume = masterVolume * musicVolume;
            FloatControl volumeControl = (FloatControl) mainMenuMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
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
            System.out.println("Updating music clip volume. Master: " + masterVolume + ", Music: " + musicVolume);
            System.out.println("Calculated dB: " + dB);
        }
    }

    @Override
    public void setMasterVolume(double newVolume) {
        this.masterVolume = Math.max(0.0, Math.min(1.0, newVolume));
        updateMusicClipVolume();
        System.out.println("Master volume set to: " + this.masterVolume);
    }

    @Override
    public void setMusicVolume(double newVolume) {
        this.musicVolume = Math.max(0.0, Math.min(1.0, newVolume));
        updateMusicClipVolume();
        System.out.println("Music volume set to: " + this.musicVolume);
    }

    @Override
    public void setSfxVolume(double newVolume) {
        this.sfxVolume = Math.max(0.0, Math.min(1.0, newVolume));
        System.out.println("SFX volume set to: " + this.sfxVolume);
    }
}