package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.service.core.SettingsService;
import com.mycompany.irr00_group_project.service.resources.PersistenceService;
import com.mycompany.irr00_group_project.service.resources.impl.PersistenceServiceImpl;

import java.util.Properties;

/**
 * Class which implements the respective interface and handles 
 *      the changes on the volumes of the game.
 * Loads the settings of the game in the properties file and saves them.
 * Is able to reload the latest saved game settings from the properties file.
 */
public class SettingsServiceImpl implements SettingsService {

    /*
     * 
     */
    public interface VolumeChangeListener {
        void onMasterVolumeChanged(double newVolume);

        void onMusicVolumeChanged(double newVolume);

        void onSfxVolumeChanged(double newVolume);
    }

    private VolumeChangeListener volumeChangeListener;

    // default settings
    private String selectedAvatar = "Robot";
    private double masterVolume = 1.0;
    private double musicVolume = 1.0;
    private double sfxVolume = 1.0;

    private static final String SETTINGS_FILENAME = "game_settings.properties";
    private final PersistenceService persistenceManager;

    private static SettingsServiceImpl instance;

    private SettingsServiceImpl() {
        this.persistenceManager = new PersistenceServiceImpl(SETTINGS_FILENAME);
        loadPersistedSettings();
    }

    /*
     * 
     */
    public static synchronized SettingsServiceImpl getInstance() {
        if (instance == null) {
            instance = new SettingsServiceImpl();
        }
        return instance;
    }

    public void setVolumeChangeListener(VolumeChangeListener listener) {
        this.volumeChangeListener = listener;
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
        if (volumeChangeListener != null) {
            volumeChangeListener.onMasterVolumeChanged(this.masterVolume);
        }
    }

    @Override
    public void setMusicVolume(double volume) {
        this.musicVolume = Math.max(0.0, Math.min(1.0, volume));
        if (volumeChangeListener != null) {
            volumeChangeListener.onMusicVolumeChanged(this.musicVolume);
        }
    }

    @Override
    public void setSfxVolume(double volume) {
        this.sfxVolume = Math.max(0.0, Math.min(1.0, volume));
        if (volumeChangeListener != null) {
            volumeChangeListener.onSfxVolumeChanged(this.sfxVolume);
        }
    }

    @Override
    public synchronized void saveCurrentSettings() {
        System.out.println("SettingsServiceImpl: Saving settings to " + SETTINGS_FILENAME + "...");
        Properties props = new Properties();
        props.setProperty("avatar", selectedAvatar);
        props.setProperty("masterVolume", String.valueOf(masterVolume));
        props.setProperty("musicVolume", String.valueOf(musicVolume));
        props.setProperty("sfxVolume", String.valueOf(sfxVolume));

        persistenceManager.saveProperties(props, "Game Application Settings");
    }

    @Override
    public synchronized void loadPersistedSettings() {
        System.out.println("SettingsServiceImpl: Loading settings from "
            + SETTINGS_FILENAME + "...");
        Properties props = persistenceManager.loadProperties();

        try {
            this.selectedAvatar = props.getProperty("avatar", "Robot"); // default if key not found
            this.masterVolume = Double.parseDouble(props.getProperty("masterVolume", "1.0"));
            this.musicVolume = Double.parseDouble(props.getProperty("musicVolume", "1.0"));
            this.sfxVolume = Double.parseDouble(props.getProperty("sfxVolume", "1.0"));

            setMasterVolume(this.masterVolume);
            setMusicVolume(this.musicVolume);
            setSfxVolume(this.sfxVolume);

            System.out.println("SettingsServiceImpl: Settings loaded successfully.");
        } catch (NumberFormatException e) {
            System.err.println("SettingsServiceImpl: Error loading settings"
                + " or file corrupted. Using default values. Error: " 
                + e.getMessage());
            e.printStackTrace();
        }
    }
}
