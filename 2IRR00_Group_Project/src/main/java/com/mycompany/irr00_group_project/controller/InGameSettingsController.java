package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.core.AudioManagerService;
import com.mycompany.irr00_group_project.service.core.SettingsService;
import com.mycompany.irr00_group_project.service.core.impl.SettingsServiceImpl;
import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Parent;

/**
 * .
 */
public class InGameSettingsController {

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

    private final List<String> AVATAR_OPTIONS =
            Arrays.asList("Robot", "Robot kid", "Alien", "Cool alien");

    private boolean isInitializingView = true;

    private Parent previousScreen;

    public void setAudioManagerService(AudioManagerService audioManagerService) {
        this.audioManagerService = audioManagerService;
    }

    public void setPreviousScreen(Parent previousScreen) {
        this.previousScreen = previousScreen;
    }
    @FXML
    private void initialize() {
        try {
            // attempt to get AudioManagerService instance if not already set by a setter
            if (this.audioManagerService == null && com.mycompany.irr00_group_project.service.core.impl.AudioManagerServiceImpl.class != null) {
                this.audioManagerService = com.mycompany.irr00_group_project.service.core.impl.AudioManagerServiceImpl.getInstance();
            }

            this.settingsService = SettingsServiceImpl.getInstance();
        } catch (Exception e) {
            System.err.println("SettingsController: CRITICAL - Failed to initialize services: " + e.getMessage());
            e.printStackTrace();
            isInitializingView = false;
            return;
        }

        if (masterVolumeSlider != null) { masterVolumeSlider.setMin(0); masterVolumeSlider.setMax(100); masterVolumeSlider.setBlockIncrement(1); }
        if (musicSlider != null) { musicSlider.setMin(0); musicSlider.setMax(100); musicSlider.setBlockIncrement(1); }
        if (sfxSlider != null) { sfxSlider.setMin(0); sfxSlider.setMax(100); sfxSlider.setBlockIncrement(1); }

        if (settingsService != null && audioManagerService != null) {
            // avatar
            if (characterComboBox != null) {
                String savedAvatar = settingsService.getSelectedAvatar();
                if (AVATAR_OPTIONS.contains(savedAvatar)) {
                    characterComboBox.setValue(savedAvatar);
                } else if (!AVATAR_OPTIONS.isEmpty()) {
                    characterComboBox.setValue(AVATAR_OPTIONS.get(0));
                }
            }

            // master volume
            double masterVol = settingsService.getMasterVolume();
            audioManagerService.setMasterVolume(masterVol);
            if (masterVolumeSlider != null) masterVolumeSlider.setValue(masterVol * 100.0);
            // music volume
            double musicVol = settingsService.getMusicVolume();
            audioManagerService.setMusicVolume(musicVol);
            if (musicSlider != null) musicSlider.setValue(musicVol * 100.0);
            // SFX volume
            double sfxVol = settingsService.getSfxVolume();
            audioManagerService.setSfxVolume(sfxVol);
            if (sfxSlider != null) sfxSlider.setValue(sfxVol * 100.0);
            System.out.println("SettingsController: Initial values loaded from SettingsService and applied.");
        } else {
            System.err.println("SettingsController: Cannot load settings to UI, services are null.");
            if(characterComboBox != null && !AVATAR_OPTIONS.isEmpty()) characterComboBox.setValue(AVATAR_OPTIONS.get(0));
            if(masterVolumeSlider != null) masterVolumeSlider.setValue(100);
        }

        if (characterComboBox != null) {
            characterComboBox.setOnAction(event -> {
                if (isInitializingView || settingsService == null) return;
                String selectedAvatar = characterComboBox.getValue();
                if (selectedAvatar != null && !selectedAvatar.equals(settingsService.getSelectedAvatar())) {
                    settingsService.setSelectedAvatar(selectedAvatar);
                    settingsService.saveCurrentSettings();
                    System.out.println("Avatar changed to: " + selectedAvatar + ". Settings saved.");
                }
            });
        }
        // master volume slider
        if (masterVolumeSlider != null) {
            masterVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (audioManagerService != null) audioManagerService.setMasterVolume(newVal.doubleValue() / 100.0);
            });
            masterVolumeSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                if (isInitializingView || settingsService == null) return;
                if (wasChanging && !isChanging) {
                    settingsService.setMasterVolume(masterVolumeSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                    System.out.println("Master Vol changed. Saved.");
                }
            });
            masterVolumeSlider.setOnMouseReleased(event -> {
                if (isInitializingView || settingsService == null) return;
                if(!masterVolumeSlider.isValueChanging()){
                    settingsService.setMasterVolume(masterVolumeSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                    System.out.println("Master Vol (click). Saved.");
                }
            });
        }

        if (musicSlider != null) {
            musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (isInitializingView || audioManagerService == null) return;
                audioManagerService.setMusicVolume(newVal.doubleValue() / 100.0);
            });
            // save when user finishes interaction
            musicSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                if (isInitializingView || settingsService == null) return;
                if (wasChanging && !isChanging) {
                    settingsService.setMusicVolume(musicSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                    System.out.println("Music Volume changed to " + (musicSlider.getValue()/100.0) + ". Settings saved.");
                }
            });
            musicSlider.setOnMouseReleased(event -> {
                if (isInitializingView || settingsService == null) return;
                if (!musicSlider.isValueChanging()) {
                    settingsService.setMusicVolume(musicSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                    System.out.println("Music Volume (click) " + (musicSlider.getValue()/100.0) + ". Settings saved.");
                }
            });
        }

        if (sfxSlider != null) {
            sfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (isInitializingView || audioManagerService == null) return;
                audioManagerService.setSfxVolume(newVal.doubleValue() / 100.0);
            });
            // save when user finishes interaction
            sfxSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                if (isInitializingView || settingsService == null) return;
                if (wasChanging && !isChanging) {
                    settingsService.setSfxVolume(sfxSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                    System.out.println("SFX Volume changed to " + (sfxSlider.getValue()/100.0) + ". Settings saved.");
                }
            });
            sfxSlider.setOnMouseReleased(event -> {
                if (isInitializingView || settingsService == null) return;
                if (!sfxSlider.isValueChanging()) {
                    settingsService.setSfxVolume(sfxSlider.getValue() / 100.0);
                    settingsService.saveCurrentSettings();
                    System.out.println("SFX Volume (click) " + (sfxSlider.getValue()/100.0) + ". Settings saved.");
                }
            });
        }

        isInitializingView = false;
    }

    /**
     * This method is called when the close button is clicked.
     * It navigates back to the main menu screen.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void handleClose(ActionEvent actionEvent) {
        try {
            if(previousScreen != null) {
                NavigationManager.getInstance().navigateTo(previousScreen);
            } else {
                MainMenuScreen menuScreen = new MainMenuScreen();
                NavigationManager.getInstance().navigateTo(menuScreen.getView());
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the main menu button is clicked.
     * It navigates back to the main menu screen.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void backToMenu(ActionEvent actionEvent) {
        goToMenu();
    }

    private static void goToMenu() {
        try {
            MainMenuScreen menuScreen = new MainMenuScreen();
            NavigationManager.getInstance().navigateTo(menuScreen.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
