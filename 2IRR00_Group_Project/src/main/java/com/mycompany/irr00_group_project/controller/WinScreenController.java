package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.WinScreenNavigatorManager;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The WinScreenController class is responsible for handling the logic.
 */

public class WinScreenController {

    private Button restartButton;
    private HBox starsContainer;

    private LevelService levelService;

    private WinScreenNavigatorManager navigatorManager;

    /**
     * Initializes the controller.
     */
    public void initialize() {
        levelService = LevelServiceImpl.getInstance();
        navigatorManager = new WinScreenNavigatorManager();
        
    }
    
    /**
     *  Updates the stars display using images of stars
     *  based on the number of stars the player achieved in the current level.
     */
    public void updateStarsDisplay() {
        starsContainer.getChildren().clear();
        int stars = getStars();

        Image starImage = new Image(
            getClass().getResourceAsStream(
                "/com/mycompany/irr00_group_project/assets/images/star_image.png"
            ),
            60, 60, true, true
        );

        for (int i = 0; i < stars; i++) {
            starsContainer.getChildren().add(new ImageView(starImage));
        }      
    }

    /**
     * Handles the action when the "Level Selection" button is clicked.
     * @param event the action event triggered by the button click.
     */
    public void handleLevelSelectionButtonAction(ActionEvent event) {
        navigatorManager.navigateToLevelSelection();
    }

    /**
     * Handles the action when the "Next Level" button is clicked.
     * @param event the action event triggered by the button click.
     */
    public void handleNextLevelButtonAction(ActionEvent event) {
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
     * Sets the action to be performed when the user restarts the level.
     * @param event the action event triggered by the button click.
     */
    public void handleRestartButtonAction(ActionEvent event) {
        navigatorManager.navigateBack();
    }

    /**
    * Sets the HBox container that will hold the star rating display.
    * This container holds the star images for the win screen
    * to visually represent the player's performance or score.
    * @param starsContainer the HBox to be used for displaying stars
     */
    public void setStarsContainer(HBox starsContainer) {
        this.starsContainer = starsContainer;
    }

    /**
    * Sets the restart button for the win screen. 
    * The button is used to reset the current level 
    * to defaults when clicked by the player.
    * 
    * @param restartButton the Button to be used for restart functionality
    */
    public void setRestartButton(Button restartButton) {
        this.restartButton = restartButton;
    }

    /**
     * Returns the current level number.
     */
    public int getCurrentLevelNumber() {
        return levelService.getCurrentLevel().getLevelNumber();
    }

    /**
     * Returns true if there is a next level (unlocked and within max).
     */
    public boolean hasNextLevel(int maxLevel) {
        int currentLevel = getCurrentLevelNumber();
        
        // Only allow next level if within bounds and unlocked
        return currentLevel < maxLevel;
    }

}
