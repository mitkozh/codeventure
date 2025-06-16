package com.mycompany.irr00_group_project.service.navigator;

import java.io.IOException;

import com.mycompany.irr00_group_project.gui.screen.GameScreen;
import com.mycompany.irr00_group_project.gui.screen.MainMenuScreen;

/**
 * Manages navigation for the level selection.
 */
public class LevelSelectionScreenNavigatorManager {

    public LevelSelectionScreenNavigatorManager() {
        
    }

    /**
     * This method is called when the user wants to navigate to the main menu.
     */
    public void navigateToMenu() {
        try {
            MainMenuScreen menuScreen = new MainMenuScreen();
            NavigationManager.getInstance().navigateTo(menuScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the user wants to navigate to the level.
     */
    public void navigateToLevel() {
        GameScreen gameScreen = new GameScreen();
        try {
            NavigationManager.getInstance().navigateTo(gameScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
