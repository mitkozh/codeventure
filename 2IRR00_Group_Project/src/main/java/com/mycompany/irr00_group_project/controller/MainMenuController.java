package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.service.navigator.MainMenuScreenNavigatorManager;
import com.mycompany.irr00_group_project.service.navigator.NavigationManager;
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

    private MainMenuScreenNavigatorManager navigatorManager;

    @FXML
    private void initialize() {
        this.navigatorManager = new MainMenuScreenNavigatorManager();
    }

    public void onLevelSelectClick(ActionEvent actionEvent) {
        navigatorManager.navigateToLevelSelection();
    }


    /**
     * This method is called when the settings button is clicked.
     * It loads the settings screen and displays it.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void onSettingsClick(ActionEvent actionEvent) {
        navigatorManager.navigateToSettings();
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
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }   

    public void onExitGameClick(ActionEvent actionEvent) {
        Platform.exit();
    }
}
