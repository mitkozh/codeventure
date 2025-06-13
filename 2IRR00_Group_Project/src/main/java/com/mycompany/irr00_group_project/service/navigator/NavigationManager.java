package com.mycompany.irr00_group_project.service.navigator;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * NavigationManager is a singleton class, responsible for managing the
 * navigation between different screens in the application.
 * It provides methods to set the content area of the main layout
 * and navigate to different screens.
 */
class NavigationManager {
    private static NavigationManager instance;
    private AnchorPane contentArea;

    /**
     * Singleton instance of NavigationManager.
     *
     * @return the singleton instance
     */
    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void setContentArea(AnchorPane contentArea) {
        this.contentArea = contentArea;
    }

    /**
     * Navigates to the specified screen by clearing the content area
     * and adding the new screen to it.
     *
     * @param node the screen to navigate to
     */
    public void navigateTo(Parent node) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(node);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }
}
