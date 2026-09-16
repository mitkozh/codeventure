package com.codeventure.service.navigator;

import com.codeventure.gui.screen.GameScreen;
import com.codeventure.gui.screen.LevelSelectionScreen;
import com.codeventure.model.core.dto.LevelDTO;

/**
 * Manages navigation for the loss screen in the game.
 */
public class LossScreenNavigatorManager {
    
    /**
     * This method is called when the user wants to navigate to the level selection screen.
     */
    public void navigateToLevelSelection() {
        LevelSelectionScreen levels = new LevelSelectionScreen();
        try {
            NavigationManager.getInstance().navigateTo(levels);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the user wants to navigate to the same game screen.
     */
    public void navigateToSameGameScreen(LevelDTO currentLevelDTO) {
        try {
            GameScreen game = new GameScreen();
            NavigationManager.getInstance().navigateTo(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navigateBack() {
        NavigationManager.getInstance().navigateBack();
    }
}
