package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;
import com.mycompany.irr00_group_project.view.screen.SettingsScreen;

import javafx.event.ActionEvent;

public class MainMenuScreenNavigatorManager {

    public MainMenuScreenNavigatorManager() {

    }
    /**
     * This method is called when the level selection button is clicked.
     * It loads the level selection screen and displays it.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void navigateToLevelSelection() {
        try {
            LevelSelectionScreen levels = new LevelSelectionScreen();
            NavigationManager.getInstance().navigateTo(levels.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navigateToSettings() {
        try {
            SettingsScreen settings = new SettingsScreen();
            NavigationManager.getInstance().navigateTo(settings.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        // This method is currently empty, but can be implemented to navigate to the settings screen


    



}
