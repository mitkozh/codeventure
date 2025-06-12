package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.navigator.HelpScreenNavigatorManager;

import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for the help screen.
 * This class handles navigation back to the main menu from the help screen.
 */
public class HelpController {

    private HelpScreenNavigatorManager navigatorManager;

    private boolean isInitializingView = true;
    
    /**
     * Initializes the controller. Can be used to set up
     * any initial data or bindings needed for the help screen.
     */
    @FXML
    public void initialize() {
        navigatorManager = new HelpScreenNavigatorManager();
    }

    /**
     * Handles the back button action to return to the main menu.
     * @param actionEvent The action event triggered by the button click
     */
    @FXML
    public void backToMenu(ActionEvent actionEvent) {
        navigatorManager.navigateToMenu();
    }
    
}