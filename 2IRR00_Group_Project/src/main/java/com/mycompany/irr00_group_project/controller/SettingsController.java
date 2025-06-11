package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.core.SettingsService;
import com.mycompany.irr00_group_project.service.core.impl.SettingsServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.NavigationManager;
import com.mycompany.irr00_group_project.service.navigator.SettingsScreenNavigatorManager;
import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;
import com.mycompany.irr00_group_project.view.screen.SettingsScreen;
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

    private SettingsService settingsService;

    private Runnable onExit;
    private Runnable onGoBack;

    private SettingsScreenNavigatorManager navigatorManager;
            

    private final List<String> avatarOptions =
            Arrays.asList("Robot", "Robot kid", "Alien", "Cool alien");

    private boolean isInitializingView = true;

    /**
     * Initializes the settings screen.
     */
    @FXML
    private void initialize() {
        navigatorManager = new SettingsScreenNavigatorManager();
        isInitializingView = true;
        initializeServices();
        configureSliders();
        loadSettings();
        setupObservableBindings();
        isInitializingView = false;
    }

    private void initializeServices() {
        try {
            this.settingsService = SettingsServiceImpl.getInstance();
        } catch (Exception e) {
            System.err.println("SettingsController: CRITICAL - Failed "
                    + "to initialize services: " + e.getMessage());
            e.printStackTrace();
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
        if (settingsService == null) {
            System.err.println("SettingsService is not initialized.");
            return;
        }
        loadAvatarSettings();
        loadVolumeSettings();
    }

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

    private void loadAvatarSettings() {
        String savedAvatar = settingsService.getSelectedAvatar();
        if (isSavedAvatarValid(savedAvatar)) {
            characterComboBox.setValue(savedAvatar);
        } else if (characterComboBox != null && !avatarOptions.isEmpty()) {
            characterComboBox.setValue(avatarOptions.getFirst());
        }
    }

    private boolean isSavedAvatarValid(String savedAvatar) {
        return characterComboBox != null && avatarOptions.contains(savedAvatar);
    }

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

    public void goToMenu() {
        navigatorManager.navigateToMenu();
    }

    public void setBackToMenuButtonContent(String content) {
        backToMenuButton.setText(content);
    }
}