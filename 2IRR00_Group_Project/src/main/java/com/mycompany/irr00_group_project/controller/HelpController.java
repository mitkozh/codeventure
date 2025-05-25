package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for the help screen.
 * This class handles navigation back to the main menu from the help screen.
 */
public class HelpController {

    /**
     * Initializes the controller. This method can be used to set up
     * any initial data or bindings needed for the help screen.
     */
    @FXML
    public void initialize() {
        // Currently no initialization needed for help screen
        // Could be used to load dynamic help content in the future
    }

    /**
     * Handles the back button action to return to the main menu.
     * @param actionEvent The action event triggered by the button click
     */
    @FXML
    public void backToMenu(ActionEvent actionEvent) {
        goToMenu();
    }

    /**
     * Static method to navigate back to the main menu.
     * Can be called from other parts of the application if needed.
     */
    @FXML
    private static void goToMenu() {
        try {
            MainMenuScreen menuScreen = new MainMenuScreen();
            NavigationManager.getInstance().navigateTo(menuScreen.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}