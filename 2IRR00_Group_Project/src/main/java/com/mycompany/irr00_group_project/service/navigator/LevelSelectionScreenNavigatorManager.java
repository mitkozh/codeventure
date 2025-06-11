package com.mycompany.irr00_group_project.service.navigator;

import java.io.IOException;

import com.mycompany.irr00_group_project.view.screen.GameScreen;
import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;

import javafx.fxml.FXML;

public class LevelSelectionScreenNavigatorManager {

    public LevelSelectionScreenNavigatorManager() {
        
    }

    @FXML
    public void navigateToMenu() {
        try {
            MainMenuScreen menuScreen = new MainMenuScreen();
            NavigationManager.getInstance().navigateTo(menuScreen.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void navigateToLevel() {
        GameScreen gameScreen = new GameScreen();
        try {
            NavigationManager.getInstance().navigateTo(gameScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
