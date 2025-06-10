package com.mycompany.irr00_group_project.service.core.impl;

import java.util.Properties;

import com.mycompany.irr00_group_project.model.core.dto.GameSettingsDTO;
import com.mycompany.irr00_group_project.service.core.SettingsService;
import com.mycompany.irr00_group_project.service.observable.ObservableProvider;
import com.mycompany.irr00_group_project.service.observable.ObservableRegistry;
import com.mycompany.irr00_group_project.service.observable.SettingsObservables;
import com.mycompany.irr00_group_project.service.resources.PersistenceService;
import com.mycompany.irr00_group_project.service.resources.impl.PersistenceServiceImpl;
import com.mycompany.irr00_group_project.utils.Constants;

/**
 * Class which implements the respective interface and handles 
 * the changes on the volumes of the game.
 * Loads the settings of the game in the properties file and saves them.
 * Is able to reload the latest saved game settings from the properties file.
 */
public class SettingsServiceImpl implements SettingsService, ObservableProvider {
    GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();

    private final PersistenceService persistenceManager;
    private final ObservableRegistry observableRegistry = new ObservableRegistry();

    private static SettingsServiceImpl instance;

    private SettingsServiceImpl() {
        this.persistenceManager = new PersistenceServiceImpl(Constants.GAME_SETTINGS_FILE);
        initializeObservables();
        loadPersistedSettings();
    }

    private void initializeObservables() {
        observableRegistry.register(SettingsObservables.class, new SettingsObservables());
    }

    @Override
    public ObservableRegistry getObservableRegistry() {
        return observableRegistry;
    }

    /**
     * Singleton to get the instance of SettingsServiceImpl.
     */
    public static synchronized SettingsServiceImpl getInstance() {
        if (instance == null) {
            instance = new SettingsServiceImpl();
        }
        return instance;
    }

    @Override
    public GameSettingsDTO getSettings() {
        return gameSettingsDTO;
    }

    @Override
    public String getSelectedAvatar() {
        return getObservableOrThrow(SettingsObservables.class).getSelectedAvatar();
    }

    @Override
    public double getMasterVolume() {
        return getObservableOrThrow(SettingsObservables.class).getMasterVolume();
    }

    @Override
    public double getMusicVolume() {
        return getObservableOrThrow(SettingsObservables.class).getMusicVolume();
    }

    @Override
    public double getSfxVolume() {
        return getObservableOrThrow(SettingsObservables.class).getSfxVolume();
    }

    @Override
    public void setSelectedAvatar(String avatarName) {
        getObservableOrThrow(SettingsObservables.class).setSelectedAvatar(avatarName);
    }

    @Override
    public void setMasterVolume(double volume) {
        double clampedVolume = Math.max(0.0, Math.min(1.0, volume));
        getObservableOrThrow(SettingsObservables.class).setMasterVolume(clampedVolume);
    }

    @Override
    public void setMusicVolume(double volume) {
        double clampedVolume = Math.max(0.0, Math.min(1.0, volume));
        getObservableOrThrow(SettingsObservables.class).setMusicVolume(clampedVolume);
    }

    @Override
    public void setSfxVolume(double volume) {
        double clampedVolume = Math.max(0.0, Math.min(1.0, volume));
        getObservableOrThrow(SettingsObservables.class).setSfxVolume(clampedVolume);
    }

    @Override
    public synchronized void saveCurrentSettings() {
        SettingsObservables settingsObs = getObservableOrThrow(SettingsObservables.class);
        Properties props = new Properties();
        props.setProperty("avatar", settingsObs.getSelectedAvatar());
        props.setProperty("masterVolume", String.valueOf(settingsObs.getMasterVolume()));
        props.setProperty("musicVolume", String.valueOf(settingsObs.getMusicVolume()));
        props.setProperty("sfxVolume", String.valueOf(settingsObs.getSfxVolume()));

        persistenceManager.saveProperties(props, "Game Application Settings");
    }

    @Override
    public synchronized void loadPersistedSettings() {
        Properties props = persistenceManager.loadProperties();
        try {
            setSelectedAvatar(props.getProperty("avatar", "Robot"));
            setMasterVolume(Double.parseDouble(props.getProperty("masterVolume", "1.0")));
            setMusicVolume(Double.parseDouble(props.getProperty("musicVolume", "1.0")));
            setSfxVolume(Double.parseDouble(props.getProperty("sfxVolume", "1.0")));
        } catch (NumberFormatException e) {
            System.err.println("SettingsServiceImpl: Settings load failed, using defaults. Error: "
                    + e.getMessage());
            e.printStackTrace();
            setSelectedAvatar("Robot");
            setMasterVolume(1.0);
            setMusicVolume(1.0);
            setSfxVolume(1.0);
        }
    }
}
