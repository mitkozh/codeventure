package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;
import com.mycompany.irr00_group_project.view.screen.SettingsScreen;

import javafx.event.ActionEvent;

/**
 * Manages navigation for the main menu screen.
 */
public class MainMenuScreenNavigatorManager {

    public MainMenuScreenNavigatorManager() {

    }

    /**
     * This method is called when the level selection button is clicked.
     * It loads the level selection screen and displays it.
     */
    public void navigateToLevelSelection() {
        try {
            LevelSelectionScreen levels = new LevelSelectionScreen();
            NavigationManager.getInstance().navigateTo(levels.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the settings button is clicked.
     */
    public void navigateToSettings() {
        try {
            SettingsScreen settings = new SettingsScreen();
            NavigationManager.getInstance().navigateTo(settings.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        // This method is currently empty, but can be implemented to navigate to the settings screen
}
