package com.mycompany.irr00_group_project.service.navigator;

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
        Runnable onExit = getOnRestart();
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
    
    /**
     * Navigates to the level selection screen.
     */
    public void navigateToLossScreen() {
        try {
            LossScreen lossScreen = getLossScreen();
            NavigationManager.getInstance().navigateTo(lossScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LossScreen getLossScreen() {
        Runnable onRestart = getOnRestart();
        return new LossScreen(onRestart);
    }

    /**
     * Navigates to the win screen.
     */
    public void navigateToWinScreen() {
        try {
            WinScreen winScreen = getWinScreen();
            NavigationManager.getInstance().navigateTo(winScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WinScreen getWinScreen() {
        Runnable onRestart = getOnRestart();
        return new WinScreen(onRestart);
    }

    private Runnable getOnRestart() {
        return () -> {
            NavigationManager.getInstance().navigateTo(rootPane);
        };
    }
}
