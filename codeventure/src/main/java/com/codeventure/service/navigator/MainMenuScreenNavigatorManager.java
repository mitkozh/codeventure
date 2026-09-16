package com.codeventure.service.navigator;

import com.codeventure.gui.screen.HelpScreen;
import com.codeventure.gui.screen.LevelSelectionScreen;
import com.codeventure.gui.screen.SettingsScreen;

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
            NavigationManager.getInstance().navigateTo(levels);
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
            NavigationManager.getInstance().navigateTo(settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method loads the help screen and displays it.
     */
    public void navigateToHelp() {
        try {
            HelpScreen help = new HelpScreen();
            NavigationManager.getInstance().navigateTo(help);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
