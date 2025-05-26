package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.service.core.SettingsService;

import java.io.*;
import java.util.Properties;

/**
 * .
 */
public class SettingsServiceImpl implements SettingsService {
    // default settings
    private String selectedAvatar = "Robot";
    private double masterVolume = 1.0;
    private double musicVolume = 1.0;
    private double sfxVolume = 1.0;

    private static final String SETTINGS_FILENAME = "game_settings.properties";

    private static SettingsServiceImpl instance;

    private SettingsServiceImpl() {
        loadPersistedSettings();
    }

    public static synchronized SettingsServiceImpl getInstance() {
        if (instance == null) {
            instance = new SettingsServiceImpl();
        }
        return instance;
    }

    @Override
    public String getSelectedAvatar() {
        return this.selectedAvatar;
    }

    @Override
    public double getMasterVolume() {
        return this.masterVolume;
    }

    @Override
    public double getMusicVolume() {
        return this.musicVolume;
    }

    @Override
    public double getSfxVolume() {
        return this.sfxVolume;
    }

    @Override
    public void setSelectedAvatar(String avatarName) {
        this.selectedAvatar = avatarName;
    }

    @Override
    public void setMasterVolume(double volume) {
        this.masterVolume = Math.max(0.0, Math.min(1.0, volume));
    }

    @Override
    public void setMusicVolume(double volume) {
        this.musicVolume = Math.max(0.0, Math.min(1.0, volume));
    }

    @Override
    public void setSfxVolume(double volume) {
        this.sfxVolume = Math.max(0.0, Math.min(1.0, volume));
    }

    @Override
    public synchronized void saveCurrentSettings() {
        System.out.println("SettingsServiceImpl: Saving settings to " + SETTINGS_FILENAME + "...");
        Properties props = new Properties();
        props.setProperty("avatar", selectedAvatar);
        props.setProperty("masterVolume", String.valueOf(masterVolume));
        props.setProperty("musicVolume", String.valueOf(musicVolume));
        props.setProperty("sfxVolume", String.valueOf(sfxVolume));

        try (OutputStream output = new FileOutputStream(SETTINGS_FILENAME)) {
            props.store(output, "Game Application Settings");
            System.out.println("Settings saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void loadPersistedSettings() {
        System.out.println("SettingsServiceImpl: Loading settings from " + SETTINGS_FILENAME + "...");
        Properties props = new Properties();
        File settingsFile = new File(SETTINGS_FILENAME);

        if (settingsFile.exists()) {
            try (InputStream input = new FileInputStream(settingsFile)) {
                props.load(input);

                this.selectedAvatar = props.getProperty("avatar", "Robot"); // default if key not found
                this.masterVolume = Double.parseDouble(props.getProperty("masterVolume", "1.0"));
                this.musicVolume = Double.parseDouble(props.getProperty("musicVolume", "1.0"));
                this.sfxVolume = Double.parseDouble(props.getProperty("sfxVolume", "1.0"));

                setMasterVolume(this.masterVolume);
                setMusicVolume(this.musicVolume);
                setSfxVolume(this.sfxVolume);

                System.out.println("Settings loaded successfully.");
                return;

            } catch (IOException | NumberFormatException e) {
                System.err.println("Error loading settings or file corrupted. Using default values. Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Settings file not found. Using default settings values.");
        }
    }
}
