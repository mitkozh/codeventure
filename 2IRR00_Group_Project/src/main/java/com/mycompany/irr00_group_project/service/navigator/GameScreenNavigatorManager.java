package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.gui.screen.HelpScreen;
import com.mycompany.irr00_group_project.gui.screen.LevelSelectionScreen;
import com.mycompany.irr00_group_project.gui.screen.LossScreen;
import com.mycompany.irr00_group_project.gui.screen.SettingsScreen;
import com.mycompany.irr00_group_project.gui.screen.WinScreen;

import java.io.IOException;

/**
 * GameScreenNavigatorManager is used for managing the
 * navigation to the settings screen in the game.
 */
public class GameScreenNavigatorManager {

    /**
     * Navigates to the in-game settings screen.
     */
    public void navigateToSettings() {
        try {
            SettingsScreen settingsScreen = new SettingsScreen();
            NavigationManager.getInstance().navigateTo(settingsScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the loss screen.
     */
    public void navigateToLossScreen() {
        try {
            LossScreen lossScreen = new LossScreen();
            NavigationManager.getInstance().navigateTo(lossScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the win screen.
     */
    public void navigateToWinScreen() {
        try {
            WinScreen winScreen = new WinScreen();
            NavigationManager.getInstance().navigateTo(winScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the help screen.
     */
    public void navigateToHelp() {
        try {
            HelpScreen helpScreen = new HelpScreen();
            NavigationManager.getInstance().navigateTo(helpScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the previous screen.
     */
    private void navigateBack() {
        NavigationManager.getInstance().navigateBack();
    }

    /**
     * Navigates to level selection screen.
     */
    private void navigateToLevelSelection() {
        try {
            LevelSelectionScreen levelSelectionScreen = new LevelSelectionScreen();
            NavigationManager.getInstance().navigateTo(levelSelectionScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}