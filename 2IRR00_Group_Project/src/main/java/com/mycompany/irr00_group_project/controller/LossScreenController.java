package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.GameScreen;
import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * This class represents the controller for the Loss Screen of the game.
 */
public class LossScreenController {
    @FXML
    private Button restartButton;
    @FXML
    private Button levelSelectionButton;

    private LevelServiceImpl levelService;
    private LevelDTO currentLevelDTO;

    @FXML
    public void initialize() {
        levelService = LevelServiceImpl.getInstance();
    }

    public void setCurrentLevelDTO(LevelDTO levelDTO) {
        this.currentLevelDTO = levelDTO;
    }

    @FXML
    public void handleLevelSelectionButton(ActionEvent event) {

        // Navigate to level selection screen
        try {
            LevelSelectionScreen levels = new LevelSelectionScreen();
            NavigationManager.getInstance().navigateTo(levels.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRestartButton(ActionEvent event) {

        currentLevelDTO = levelService.getCurrentLevel();

        try {
            GameScreen game = new GameScreen();
            NavigationManager.getInstance().navigateTo(game.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
