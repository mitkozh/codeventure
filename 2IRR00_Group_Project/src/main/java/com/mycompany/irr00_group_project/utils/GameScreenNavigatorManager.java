package com.mycompany.irr00_group_project.utils;

import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;
import com.mycompany.irr00_group_project.view.screen.LossScreen;
import com.mycompany.irr00_group_project.view.screen.SettingsScreen;
import com.mycompany.irr00_group_project.view.screen.WinScreen;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * GameScreenNavigatorManager is used for managing the
 * navigation to the settings screen in the game.
 */
public class GameScreenNavigatorManager {
    private Parent rootPane;

    public GameScreenNavigatorManager(Parent root) {
        this.rootPane = root;
    }

    private SettingsScreen getSettingsScreen() {
        Runnable onExit = () -> {
            NavigationManager.getInstance().navigateTo(rootPane);
        };
        Runnable onGoBack = () -> {
            LevelSelectionScreen levelSelectionScreen = new LevelSelectionScreen();
            try {
                NavigationManager.getInstance().navigateTo(levelSelectionScreen.getView());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        return new SettingsScreen(onExit, onGoBack, "SELECT LEVEL");
    }

    /**
     * Navigates to the in-game settings screen.
     */
    public void navigateToSettings() {
        SettingsScreen settingsScreen = getSettingsScreen();
        try {
            NavigationManager.getInstance().navigateTo(settingsScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToLossScreen() {
        try {
            LossScreen lossScreen = new LossScreen();
            NavigationManager.getInstance().navigateTo(lossScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToWinScreen() {
        try {
            WinScreen winScreen = new WinScreen();
            NavigationManager.getInstance().navigateTo(winScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
