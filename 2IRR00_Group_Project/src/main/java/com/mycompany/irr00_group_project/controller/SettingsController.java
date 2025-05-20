package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * .
 */
public class SettingsController {

    // todo: implement real logic
    private String currentCharacter = "Robot";

    @FXML
    private ComboBox<String> characterComboBox;

    @FXML
    private void initialize() {
        characterComboBox.setValue(currentCharacter);
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
