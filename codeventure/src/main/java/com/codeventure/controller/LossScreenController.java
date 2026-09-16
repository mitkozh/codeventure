package com.codeventure.controller;

import com.codeventure.service.navigator.LossScreenNavigatorManager;


import javafx.event.ActionEvent;

/**
 * This class represents the controller for the Loss Screen of the game.
 */
public class LossScreenController {
    private LossScreenNavigatorManager navigatorManager;

    /**
     * Initialization method for the Loss Screen Controller.
     */
    public void initialize() {
        navigatorManager = new LossScreenNavigatorManager();
    }

    /**
     * Handles the action when the "Level Selection" button is clicked.
     */
    public void handleLevelSelectionButton(ActionEvent event) {
        navigatorManager.navigateToLevelSelection();
    }

    /**
     * Handles the action when the "Restart" button is clicked.
     */
    public void handleGoBack(ActionEvent event) {
        navigatorManager.navigateBack();
    }
}
