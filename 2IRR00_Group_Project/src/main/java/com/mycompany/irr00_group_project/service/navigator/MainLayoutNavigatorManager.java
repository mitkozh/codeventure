package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.gui.screen.MainMenuScreen;
import javafx.scene.layout.AnchorPane;

/**
 * MainLayoutNavigatorManager is responsible for managing navigation
 * within the main layout of the application.
 */
public class MainLayoutNavigatorManager {

    /**
     * Sets the content area for navigation.
     * @param contentArea the AnchorPane where screens will be displayed.
     */
    public void setContentArea(AnchorPane contentArea) {
        NavigationManager.getInstance().setContentArea(contentArea);
    }

    /**
     * Navigates to the main menu screen.
     */
    public void navigateToMainMenu() {
        try {
            MainMenuScreen mainMenu = new MainMenuScreen();
            NavigationManager.getInstance().navigateToRoot(mainMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
