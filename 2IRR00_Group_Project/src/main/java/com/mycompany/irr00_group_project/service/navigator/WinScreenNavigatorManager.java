package com.mycompany.irr00_group_project.service.navigator;

import java.io.IOException;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.view.screen.GameScreen;
import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;

import javafx.event.ActionEvent;

public class WinScreenNavigatorManager {

    public void navigateToLevelSelection() {
        LevelSelectionScreen levelSelectionScreen = new LevelSelectionScreen();
        try {
            NavigationManager.getInstance().navigateTo(levelSelectionScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToSameGameScreen(LevelDTO currentLevelDTO){
        
        GameScreen gameScreen = new GameScreen();
        try {
            NavigationManager.getInstance().navigateTo(gameScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToNextLevel(LevelDTO nextLevel) {
        
        if (nextLevel != null) {
            GameScreen gameScreen = new GameScreen();
            try {
                NavigationManager.getInstance().navigateTo(gameScreen.getView());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No next level available.");
        }
    }
    
}