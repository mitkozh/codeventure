package com.codeventure.service.navigator;

import java.io.IOException;

import com.codeventure.gui.screen.GameScreen;
import com.codeventure.gui.screen.LevelSelectionScreen;
import com.codeventure.model.core.dto.LevelDTO;

/**
 * Manages navigation for the win screen in the game.
 */
public class WinScreenNavigatorManager {

    /**
     * Navigate to the level selection screen.
     */
    public void navigateToLevelSelection() {
        LevelSelectionScreen levelSelectionScreen = new LevelSelectionScreen();
        try {
            NavigationManager.getInstance().navigateTo(levelSelectionScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigate to the same game screen with the current level.
     */
    public void navigateToSameGameScreen(LevelDTO currentLevelDTO) {
        
        GameScreen gameScreen = new GameScreen();
        try {
            NavigationManager.getInstance().navigateTo(gameScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigate to the next level screen.
     */
    public void navigateToNextLevel(LevelDTO nextLevel) {
        
        if (nextLevel != null) {
            GameScreen gameScreen = new GameScreen();
            try {
                NavigationManager.getInstance().navigateTo(gameScreen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No next level available.");
        }
    }

    public void navigateBack() {
        NavigationManager.getInstance().navigateBack();
    }
}