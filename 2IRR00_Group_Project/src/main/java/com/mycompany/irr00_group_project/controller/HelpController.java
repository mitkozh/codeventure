package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.navigator.HelpScreenNavigatorManager;

import com.mycompany.irr00_group_project.service.navigator.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for the help screen.
 * This class handles navigation back to the main menu from the help screen.
 */
public class HelpController {

    private HelpScreenNavigatorManager navigatorManager;
    private Runnable onExit;

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
     * 
     * @param actionEvent The action event triggered by the button click
     */
    @FXML
    public void backToMenu(ActionEvent actionEvent) {
        if (onExit != null) {
            onExit.run();
            NavigationService.getInstance().notifyReturnedToGame();
        } else {
            navigatorManager.navigateToMenu();
        }
    }

    /**
     * Sets the action to be performed when the user exits the help screen.
     * 
     * @param onExit The runnable to execute on exit
     */
    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }
}