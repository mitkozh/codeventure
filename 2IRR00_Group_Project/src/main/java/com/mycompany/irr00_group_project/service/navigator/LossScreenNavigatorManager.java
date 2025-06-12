package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.view.screen.GameScreen;
import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;

/**
 * Manages navigation for the loss screen in the game.
 */
public class LossScreenNavigatorManager {
    
    /**
     * This method is called when the user wants to navigate to the level selection screen.
     */
    public void navigateToLevelSelection() {
        // Navigate to level selection screen
        try {
            LevelSelectionScreen levels = new LevelSelectionScreen();
            NavigationManager.getInstance().navigateTo(levels.getView());
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
            NavigationManager.getInstance().navigateTo(game.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
