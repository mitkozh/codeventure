package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.GameScreen;
import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    
    /**
     * Handles the action when the "Level Selection" button is clicked.
     */
    public void handleLevelSelectionButtonAction() {
        LevelSelectionScreen levelSelectionScreen = new LevelSelectionScreen();
        try {
            NavigationManager.getInstance().navigateTo(levelSelectionScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "Restart" button is clicked.
     */
    public void handleRestartButtonAction() {
        LevelDTO currentLevel = levelService.getCurrentLevel();
        GameScreen gameScreen = new GameScreen();
        try {
            NavigationManager.getInstance().navigateTo(gameScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "Next Level" button is clicked.
     */
    public void handleNextLevelButtonAction() {
        LevelDTO nextLevel = levelService.selectNextLevel();
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

    /**
     * Gets the number of stars for the current level.
     * @return the stars received.
     */
    public int getStars() {
        LevelDTO currentLevel = levelService.getCurrentLevel();
        return currentLevel.getStars();
    }
}
