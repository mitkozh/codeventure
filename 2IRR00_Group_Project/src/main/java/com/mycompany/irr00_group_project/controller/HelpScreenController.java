package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.navigator.HelpScreenNavigatorManager;
import javafx.event.ActionEvent;

/**
 * Controller for the help screen.
 * This class handles navigation back to the main menu from the help screen.
 */
public class HelpScreenController {

    private HelpScreenNavigatorManager navigatorManager;

    /**
     * Initializes the controller. Can be used to set up
     * any initial data or bindings needed for the help screen.
     */
    public void initialize() {
        navigatorManager = new HelpScreenNavigatorManager();
    }

    /**
     * Handles the back button action to return to the main menu.
     *
     * @param actionEvent The action event triggered by the button click
     */
    public void backToMenu(ActionEvent actionEvent) {
        navigatorManager.navigateBackAndNotify();
    }
}