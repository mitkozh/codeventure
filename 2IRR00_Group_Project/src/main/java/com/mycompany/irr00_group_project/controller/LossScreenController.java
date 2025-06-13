package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.LossScreenNavigatorManager;


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

    private LevelService levelService;
    private LevelDTO currentLevelDTO;
    private LossScreenNavigatorManager navigatorManager;

    /**
     * Initialization.
     */
    @FXML
    public void initialize() {
        levelService = LevelServiceImpl.getInstance();
        navigatorManager = new LossScreenNavigatorManager();
    }

    public void setCurrentLevelDTO(LevelDTO levelDTO) {
        this.currentLevelDTO = levelDTO;
    }
    
    /**
     * Handles the action when the "Level Selection" button is clicked.
     */
    @FXML
    public void handleLevelSelectionButton(ActionEvent event) {
        navigatorManager.navigateToLevelSelection();
    }

    /**
     * Handles the action when the "Restart" button is clicked.
     */
    public void setOnRestart(Runnable onRestart) {
        restartButton.setOnAction(event -> {
            onRestart.run();
        });
    }
}
