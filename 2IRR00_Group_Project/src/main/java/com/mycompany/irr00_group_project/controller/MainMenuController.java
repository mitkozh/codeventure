package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.navigator.MainMenuScreenNavigatorManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * Controller for the main menu.
 * The class manages the "movement" of the user from the main
 * menu to the help screen, settings screen and level menu.
 * Handles exiting the program by pressing the exit button.
 */
public class MainMenuController {


    private MainMenuScreenNavigatorManager navigatorManager;

    /**
     * Constructor for MainMenuController.
     */
    public MainMenuController() {
        this.navigatorManager = new MainMenuScreenNavigatorManager();
    }

    /**
     * This method navigates the user to the level selection screen.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void onLevelSelectClick(ActionEvent actionEvent) {
        navigatorManager.navigateToLevelSelection();
    }

    /**
     * This method is called when the settings button is clicked.
     * It loads the settings screen and displays it.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void onSettingsClick(ActionEvent actionEvent) {
        navigatorManager.navigateToSettings();
    }

    /**
     * This method is called when the help button is clicked.
     * It loads the help screen and displays it.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void onHelpClick(ActionEvent actionEvent) {
        navigatorManager.navigateToHelp();
    }

    /**
     * This method is called when the exit button is clicked.
     * It exits the application.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void onExitGameClick(ActionEvent actionEvent) {
        Platform.exit();
    }
}
