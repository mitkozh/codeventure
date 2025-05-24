package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.core.AudioManagerService;
import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

/**
 * .
 */
public class SettingsController {

    // todo: implement real logic
    private String currentCharacter = "Robot";

    @FXML
    private ComboBox<String> characterComboBox;

    @FXML
    private Slider masterVolumeSlider;

    @FXML
    private Slider musicSlider;

    @FXML
    private Slider sfxSlider;

    private AudioManagerService audioManagerService;
    
    // todo: predefine character selection choices

    public void setAudioManagerService(AudioManagerService audioManagerService) {
        this.audioManagerService = audioManagerService;
    }

    @FXML
    private void initialize() {
        characterComboBox.setValue(currentCharacter);

        // Set initial slider values (optional: load from persistence)
        masterVolumeSlider.setValue(100);
        musicSlider.setValue(100);
        sfxSlider.setValue(100);

        // Add listeners to sliders
        masterVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (audioManagerService != null) {
                audioManagerService.setMasterVolume(newVal.doubleValue() / 100.0);
            }
        });

        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (audioManagerService != null) {
                audioManagerService.setMusicVolume(newVal.doubleValue() / 100.0);
            }
        });

        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (audioManagerService != null) {
                audioManagerService.setSfxVolume(newVal.doubleValue() / 100.0);
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
        goToMenu();
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
