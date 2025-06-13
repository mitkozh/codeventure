package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.WinScreenNavigatorManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The WinScreenController class is responsible for handling the logic.
 */
public class WinScreenController {

    public Button restartButton;
    @FXML
    private Label starsLabel;

    private LevelService levelService;

    private LevelDTO currentLevelDTO;

    private WinScreenNavigatorManager navigatorManager;

    /**
     * Initializes the controller.
     */
    public void initialize() {
        levelService = LevelServiceImpl.getInstance();
        navigatorManager = new WinScreenNavigatorManager();
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
        navigatorManager.navigateToLevelSelection();
    }

    /**
     * Handles the action when the "Next Level" button is clicked.
     */
    public void handleNextLevelButtonAction() {
        LevelDTO nextLevel = levelService.selectNextLevel();
        navigatorManager.navigateToNextLevel(nextLevel);
    }

    /**
     * Gets the number of stars for the current level.
     *
     * @return the stars received.
     */
    public int getStars() {
        LevelDTO currentLevel = levelService.getCurrentLevel();
        return currentLevel.getStars();
    }

    /**
     * Sets the action to be performed when the restart button is clicked.
     *
     * @param onRestart the action to perform on restart.
     */
    public void setOnRestart(Runnable onRestart) {
        restartButton.setOnAction(event -> {
            onRestart.run();
        });
    }
}
