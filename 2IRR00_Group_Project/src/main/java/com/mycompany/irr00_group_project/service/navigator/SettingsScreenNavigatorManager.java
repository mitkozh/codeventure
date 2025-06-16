package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.gui.screen.GameScreen;
import com.mycompany.irr00_group_project.gui.screen.LevelSelectionScreen;
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
            NavigationManager.getInstance().navigateToRoot(menuScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the previous screen and notifies the game service.
     */
    public void navigateBackAndNotify() {
        NavigationManager navigationManager = NavigationManager.getInstance();
        NavigationService.getInstance().notifyReturnedToGame();
        navigationManager.navigateBack();
    }

    /**
     * Navigates to the level selection screen or
     * main menu.
     */
    public void navigateToLevelSelectionOrMainMenu() {
        NavigationManager navManager = NavigationManager.getInstance();
        String previousScreenType = navManager.getPreviousScreenType();

        if (getScreenType(GameScreen.class).equals(previousScreenType)) {
            navigateToLevelSelection();
        } else {
            navigateToMenu();
        }
    }

    private String getScreenType(Class<?> screenClass) {
        return screenClass.getSimpleName();
    }

    private void navigateToLevelSelection() {
        try {
            LevelSelectionScreen levelSelectionScreen = new LevelSelectionScreen();
            NavigationManager.getInstance().navigateTo(levelSelectionScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}