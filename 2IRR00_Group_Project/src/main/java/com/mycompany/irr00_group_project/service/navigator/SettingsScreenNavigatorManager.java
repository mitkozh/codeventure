package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.gui.screen.MainMenuScreen;

/**
 * Manages navigation for the settings screen.
 */
public class SettingsScreenNavigatorManager {

    /**
     * Navigates to the main menu screen.
     */
    public void navigateToMenu() {
        try {
            MainMenuScreen menuScreen = new MainMenuScreen();
            NavigationManager.getInstance().navigateTo(menuScreen.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}