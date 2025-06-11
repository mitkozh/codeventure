package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.GameScreen;
import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import java.io.IOException;

/**
 * The WinScreenController class is responsible for handling the logic.
 */
public class WinScreenController {

    @FXML
    private Label starsLabel;

    private LevelService levelService;

    /**
     * Initializes the controller.
     */
    public void initialize() {
        levelService = LevelServiceImpl.getInstance();
        updateStarsDisplay();
    }

    private void updateStarsDisplay() {
        int stars = getStars();
        starsLabel.setText("★".repeat(stars));
        starsLabel.getStyleClass().add("star-label");
    }

    public void handleLevelSelectionButtonAction() {
        LevelSelectionScreen levelSelectionScreen = new LevelSelectionScreen();
        try {
            NavigationManager.getInstance().navigateTo(levelSelectionScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRestartButtonAction() {
        LevelDTO currentLevel = levelService.getCurrentLevel();
        GameScreen gameScreen = new GameScreen();
        try {
            NavigationManager.getInstance().navigateTo(gameScreen.getView());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleNextLevelButtonAction() {
        LevelDTO nextLevel = levelService.selectNextLevel();
        if (nextLevel != null) {
            GameScreen gameScreen = new GameScreen();
            try {
                NavigationManager.getInstance().navigateTo(gameScreen.getView());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No next level available.");
        }
    }

    public int getStars() {
        LevelDTO currentLevel = levelService.getCurrentLevel();
        return currentLevel.getStars();
    }

    
}
