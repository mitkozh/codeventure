package com.mycompany.irr00_group_project.controller;

import java.util.Arrays;
import java.util.List;

import com.mycompany.irr00_group_project.service.core.SettingsService;
import com.mycompany.irr00_group_project.service.core.impl.SettingsServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.SettingsScreenNavigatorManager;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

/**
 * Controller for Game Settings Screen.
 * It manages the changes occurring in the sound lavels and character of the
 * player.
 */
public class SettingsController {

    private Button backToMenuButton;
    private ComboBox<String> characterComboBox;
    private Slider masterVolumeSlider;
    private Slider musicSlider;
    private Slider sfxSlider;

    private SettingsService settingsService;

    private SettingsScreenNavigatorManager navigatorManager;

    private final List<String> avatarOptions = Arrays.asList("Robot", "Robot kid",
            "Alien", "Cool alien");

    private boolean isInitializingView = true;

    /**
     * Initializes the settings screen.
     */
    public void initialize() {
        navigatorManager = new SettingsScreenNavigatorManager();
        isInitializingView = true;
        initializeServices();
        configureSliders();
        loadSettings();
        setupObservableBindings();
        isInitializingView = false;
    }

    /**
     * Initializes the settings service.
     */
    private void initializeServices() {
        try {
            this.settingsService = SettingsServiceImpl.getInstance();
        } catch (Exception e) {
            System.err.println("SettingsController: CRITICAL - Failed "
                    + "to initialize services: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Configures all volume sliders with default settings.
     */
    private void configureSliders() {
        configureSlider(masterVolumeSlider);
        configureSlider(musicSlider);
        configureSlider(sfxSlider);
    }

    /**
     * Configures an individual slider with default settings.
     * @param slider The slider to configure
     */
    private void configureSlider(Slider slider) {
        if (slider != null) {
            slider.setMin(0);
            slider.setMax(100);
            slider.setBlockIncrement(1);
        }
    }

    /**
     * Loads all settings from the settings service.
     */
    private void loadSettings() {
        if (settingsService == null) {
            System.err.println("SettingsService is not initialized.");
            return;
        }
        loadAvatarSettings();
        loadVolumeSettings();
    }

    /**
     * Loads volume settings from the settings service.
     */
    private void loadVolumeSettings() {
        if (masterVolumeSlider != null) {
            masterVolumeSlider.setValue(settingsService.getMasterVolume() * 100.0);
        }
        if (musicSlider != null) {
            musicSlider.setValue(settingsService.getMusicVolume() * 100.0);
        }
        if (sfxSlider != null) {
            sfxSlider.setValue(settingsService.getSfxVolume() * 100.0);
        }
    }

    /**
     * Loads avatar settings from the settings service.
     */
    private void loadAvatarSettings() {
        String savedAvatar = settingsService.getSelectedAvatar();
        if (isSavedAvatarValid(savedAvatar)) {
            characterComboBox.setValue(savedAvatar);
        } else if (characterComboBox != null && !avatarOptions.isEmpty()) {
            characterComboBox.setValue(avatarOptions.getFirst());
        }
    }

    /**
     * Checks if the saved avatar is valid.
     * @param savedAvatar The avatar to validate
     * @return true if the avatar is valid, false otherwise
     */
    private boolean isSavedAvatarValid(String savedAvatar) {
        return characterComboBox != null && avatarOptions.contains(savedAvatar);
    }

    /**
     * Sets up observable bindings for UI controls.
     */
    private void setupObservableBindings() {
        if (settingsService == null) {
            return;
        }
        if (characterComboBox != null) {
            characterComboBox.setOnAction(event -> handleAvatarChange());
        }
        bindSlider(masterVolumeSlider, (volume) -> settingsService.setMasterVolume(volume));
        bindSlider(musicSlider, (volume) -> settingsService.setMusicVolume(volume));
        bindSlider(sfxSlider, (volume) -> settingsService.setSfxVolume(volume));
    }

    /**
     * Handles avatar selection changes.
     */
    private void handleAvatarChange() {
        if (isInitializingView) {
            return;
        }

        String selectedAvatar = characterComboBox.getValue();
        if (selectedAvatar != null && !selectedAvatar.equals(settingsService.getSelectedAvatar())) {
            settingsService.setSelectedAvatar(selectedAvatar);
            settingsService.saveCurrentSettings();
        }
    }

    /**
     * Binds a slider to a volume setting with change listeners.
     * @param slider The slider to bind
     * @param volumeSetter The function to call when volume changes
     */
    private void bindSlider(Slider slider, java.util.function.Consumer<Double> volumeSetter) {
        if (slider == null) {
            return;
        }
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isInitializingView) {
                volumeSetter.accept(newVal.doubleValue() / 100.0);
            }
        });
        slider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isInitializingView && wasChanging && !isChanging) {
                settingsService.saveCurrentSettings();
            }
        });
        slider.setOnMouseReleased(event -> {
            if (!isInitializingView && !slider.isValueChanging()) {
                settingsService.saveCurrentSettings();
            }
        });
    }

    /**
     * This method is called when the close button is clicked.
     * It navigates back to the previous screen and notifies the navigator manager.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void handleClose(ActionEvent actionEvent) {
        navigatorManager.navigateBackAndNotify();
    }

    /**
     * This method is called when the back to screen button is clicked.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void onBackToScreenAction(ActionEvent actionEvent) {
        navigatorManager.navigateToLevelSelectionOrMainMenu();
    }
    /**
     * Sets the back to menu button.
     * @param backToMenuButton The button to set
     */

    public void setBackToMenuButton(Button backToMenuButton) {
        this.backToMenuButton = backToMenuButton;
    }

    /**
     * Sets the master volume slider.
     * @param masterVolumeSlider The slider to set
     */
    public void setMasterVolumeSlider(Slider masterVolumeSlider) {
        this.masterVolumeSlider = masterVolumeSlider;
    }

    /**
     * Sets the music volume slider.
     * @param musicSlider The slider to set
     */
    public void setMusicSlider(Slider musicSlider) {
        this.musicSlider = musicSlider;
    }

    /**
     * Sets the SFX volume slider.
     * @param sfxSlider The slider to set
     */
    public void setSfxSlider(Slider sfxSlider) {
        this.sfxSlider = sfxSlider;
    }

    /**
     * Sets the character selection combo box.
     * @param characterComboBox The combo box to set
     */
    public void setCharacterComboBox(ComboBox<String> characterComboBox) {
        this.characterComboBox = characterComboBox;
    }
}