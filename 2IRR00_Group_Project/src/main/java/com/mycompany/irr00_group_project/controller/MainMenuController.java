package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.SettingsScreen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * .
 */
//todo
public class MainMenuController {
    @FXML private Label titleLabel;
    @FXML private Button startButton;
    @FXML private Button levelSelectButton;
    @FXML private Button settingsButton;
    @FXML private Button helpButton;
    @FXML private Button exitButton;
    Stage primaryStage;
    
    public void onStartNewGameClick(ActionEvent actionEvent) {
    }

    public void onLevelSelectClick(ActionEvent actionEvent) {
    }

    /**
     * This method is called when the settings button is clicked.
     * It loads the settings screen and displays it.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void onSettingsClick(ActionEvent actionEvent) {
        try {
            SettingsScreen settings = new SettingsScreen();
            NavigationManager.getInstance().navigateTo(settings.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onHelpClick(ActionEvent actionEvent) {
    }

    public void onExitGameClick(ActionEvent actionEvent) {
        Platform.exit();
    }
}
