package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.core.AudioManagerService;
import com.mycompany.irr00_group_project.service.core.SettingsService;
import com.mycompany.irr00_group_project.service.core.impl.AudioManagerServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.SettingsServiceImpl;
import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for Game Settings Screen.
 * It manages the changes occurring in the sound lavels and character of the player.
 */
public class SettingsController {

    @FXML
    private Button backToMenuButton;

    @FXML
    private ComboBox<String> characterComboBox;

    @FXML
    private Slider masterVolumeSlider;

    @FXML
    private Slider musicSlider;

    @FXML
    private Slider sfxSlider;

    private AudioManagerService audioManagerService;
    private SettingsService settingsService;

    private Runnable onExit;
    private Runnable onGoBack;

    private final List<String> avatarOptions =
            Arrays.asList("Robot", "Robot kid", "Alien", "Cool alien");

    private boolean isInitializingView = true;


    /**
     * Initializes the settings screen.
     */
    @FXML
    private void initialize() {
        isInitializingView = true;
        initializeServices();
        configureSliders();
        loadSettings();
        setupListeners();
        isInitializingView = false;
    }

    private void initializeServices() {
        try {
            // attempt to get AudioManagerService instance if not already set by a setter
            this.audioManagerService = AudioManagerServiceImpl.getInstance();
            this.settingsService = SettingsServiceImpl.getInstance();
        } catch (Exception e) {
            System.err.println("SettingsController: CRITICAL - Failed "
                + "to initialize services: " + e.getMessage());
            e.printStackTrace();
            isInitializingView = false;
        }
    }

    private void configureSliders() {
        configureSlider(masterVolumeSlider);
        configureSlider(musicSlider);
        configureSlider(sfxSlider);
    }

    private void configureSlider(Slider slider) {
        if (slider != null) {
            slider.setMin(0);
            slider.setMax(100);
            slider.setBlockIncrement(1);
        }
    }

    private void loadSettings() {
        if (settingsService == null || audioManagerService == null) {
            handleNullServices();
            return;
        }
        loadAvatarSettings();
        loadVolumeSettings();
    }

    private void handleNullServices() {
        System.err.println("SettingsController: Cannot load "
            + "settings to UI, services are null.");
        if (characterComboBox != null && !avatarOptions.isEmpty()) {
            characterComboBox.setValue(avatarOptions.get(0));
        }
        if (masterVolumeSlider != null) {
            masterVolumeSlider.setValue(100);
        }
    }

    private void loadAvatarSettings() {
        if (characterComboBox != null) {
            String savedAvatar = settingsService.getSelectedAvatar();
            if (avatarOptions.contains(savedAvatar)) {
                characterComboBox.setValue(savedAvatar);
            } else if (!avatarOptions.isEmpty()) {
                characterComboBox.setValue(avatarOptions.getFirst());
            }
        }
    }

    private void loadVolumeSettings() {
        loadMasterVolume();
        loadMusicVolume();
        loadSfxVolume();
    }

    private void loadMasterVolume() {
        double masterVol = settingsService.getMasterVolume();
        audioManagerService.setMasterVolume(masterVol);
        if (masterVolumeSlider != null) {
            masterVolumeSlider.setValue(masterVol * 100.0);
        }
    }

    private void loadMusicVolume() {
        double musicVol = settingsService.getMusicVolume();
        audioManagerService.setMusicVolume(musicVol);
        if (musicSlider != null) {
            musicSlider.setValue(musicVol * 100.0);
        }
    }

    private void loadSfxVolume() {
        double sfxVol = settingsService.getSfxVolume();
        audioManagerService.setSfxVolume(sfxVol);
        if (sfxSlider != null) {
            sfxSlider.setValue(sfxVol * 100.0);
        }
    }

    private void setupListeners() {
        setupCharacterComboBoxListener();
        setupMasterVolumeListeners();
        setupMusicSliderListeners();
        setupSfxSliderListeners();
    }

    private void setupCharacterComboBoxListener() {
        if (characterComboBox != null) {
            characterComboBox.setOnAction(event -> {
                if (isInitializingView) {
                    return;
                }
                String selectedAvatar = characterComboBox.getValue();
                if (selectedAvatar != null && !selectedAvatar.equals(
                        settingsService.getSelectedAvatar())) {
                    settingsService.setSelectedAvatar(selectedAvatar);
                    settingsService.saveCurrentSettings();
                }
            });
        }
    }

    private void setupMasterVolumeListeners() {
        if (masterVolumeSlider != null) {
            masterVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (audioManagerService != null) {
                    audioManagerService.setMasterVolume(newVal.doubleValue() / 100.0);
                }
            });
            masterVolumeSlider.valueChangingProperty().addListener(
                (obs, wasChanging, isChanging) -> {
                    if (isInitializingView || settingsService == null) {
                        return;
                    }
                    if (wasChanging && !isChanging) {
                        settingsService.setMasterVolume(masterVolumeSlider.getValue() / 100.0);
                        settingsService.saveCurrentSettings();
                    }
                });
            masterVolumeSlider.setOnMouseReleased(event -> {
                if (isInitializingView || settingsService == null) {
                    return;
                }
                if (!masterVolumeSlider.isValueChanging()) {
                    settingsService.setMasterVolume(masterVolumeSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                }
            });
        }
    }

    private void setupMusicSliderListeners() {
        if (musicSlider != null) {
            musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                double value = newVal.doubleValue() / 100.0;
                settingsService.setMusicVolume(value);
                AudioManagerServiceImpl.getInstance().setMusicVolume(value);
                audioManagerService.setMusicVolume(value);
            });
            musicSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                if (isInitializingView || settingsService == null) {
                    return;
                }
                if (wasChanging && !isChanging) {
                    settingsService.setMusicVolume(musicSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                }
            });
            musicSlider.setOnMouseReleased(event -> {
                if (isInitializingView || settingsService == null) {
                    return;
                }
                if (!musicSlider.isValueChanging()) {
                    settingsService.setMusicVolume(musicSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                }
            });
        }
    }

    private void setupSfxSliderListeners() {
        if (sfxSlider != null) {
            sfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                double value = newVal.doubleValue() / 100.0;
                settingsService.setSfxVolume(value);
                AudioManagerServiceImpl.getInstance().setSfxVolume(value);
                audioManagerService.setSfxVolume(value);
            });
            sfxSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                if (isInitializingView || settingsService == null) {
                    return;
                }
                if (wasChanging && !isChanging) {
                    settingsService.setSfxVolume(sfxSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                }
            });
            sfxSlider.setOnMouseReleased(event -> {
                if (isInitializingView || settingsService == null) {
                    return;
                }
                if (!sfxSlider.isValueChanging()) {
                    settingsService.setSfxVolume(sfxSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                }
            });
        }
    }

    /**
     * This method is called when the close button is clicked.
     * It navigates back to the main menu screen.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void handleClose(ActionEvent actionEvent) {
        if (onExit != null) {
            onExit.run();
        } else {
            goToMenu();
        }
    }

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    public void setOnGoBack(Runnable onGoBack) {
        this.onGoBack = onGoBack;
    }

    /**
     * This method is called when the main menu button is clicked.
     * It navigates back to the main menu screen.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void backToMenu(ActionEvent actionEvent) {
        if (onGoBack != null) { 
            onGoBack.run();
        } else {
            goToMenu();
        }   
    }
    
    private static void goToMenu() {
        try {
            MainMenuScreen menuScreen = new MainMenuScreen();
            NavigationManager.getInstance().navigateTo(menuScreen.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBackToMenuButtonContent(String content) {
        backToMenuButton.setText(content);
    }
}