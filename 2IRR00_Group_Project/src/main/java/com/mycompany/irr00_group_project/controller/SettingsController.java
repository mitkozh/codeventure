package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.service.core.AudioManagerService;
import com.mycompany.irr00_group_project.service.persistence.PersistenceService;
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
    private Slider volumeSlider;

    private GameState gameState;
    private PersistenceService persistenceService;
    private AudioManagerService audioManagerService;
    
    // todo: predefine character selection choices

    @FXML
    private void initialize() {
        characterComboBox.setValue(currentCharacter);

        // volume setup (FR4)
        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);
        volumeSlider.setBlockIncrement(0.05);

        // load saved volume
        volumeSlider.setValue(gameState.getSoundVolume());
        
        // apply original volume
        audioManagerService.setGlobalVolume(gameState.getSoundVolume());
    }

    private void handleVolumeChange(double newVolume) {
        newVolume = Math.max(volumeSlider.getMin(), Math.min(newVolume, volumeSlider.getMax()));

        if (Math.abs(newVolume - gameState.getSoundVolume()) > 0.001) {
            audioManagerService.setGlobalVolume(newVolume);
            gameState.setSoundVolume(newVolume);
            persistenceService.saveSettings(gameState);
        }
    }
    
    /**
     * This method is called when the close button is clicked.
     * It closes the current screen and returns to the main menu.
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
