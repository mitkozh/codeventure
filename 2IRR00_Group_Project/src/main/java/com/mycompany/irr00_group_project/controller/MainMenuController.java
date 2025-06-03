package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.HelpScreen;
import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;
import com.mycompany.irr00_group_project.view.screen.SettingsScreen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the main menu.
 * The class manages the "movement" of the user from the main 
 *      menu to the help screen, settings screen and level menu.
 * Handles exiting the program by pressing the exit button.
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

    /*
     * This method is called when the level selection button is clicked.
     * It loads the level selection screen and displays it.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void onLevelSelectClick(ActionEvent actionEvent) {
        try {
            LevelSelectionScreen levels = new LevelSelectionScreen();
            NavigationManager.getInstance().navigateTo(levels.getView());
            System.out.println("Level selection screen loaded");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading level selection screen");
        }
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

    /**
    * This method is called when the help button is clicked.
    * It loads the help screen and displays it.
    *
    * @param actionEvent The action event triggered by the button click.
    */
    public void onHelpClick(ActionEvent actionEvent) {
        try {
            HelpScreen helpScreen = new HelpScreen();
            NavigationManager.getInstance().navigateTo(helpScreen.getView());
            System.out.println("Help screen loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading help screen");
        }   
    }   

    public void onExitGameClick(ActionEvent actionEvent) {
        Platform.exit();
    }
}
