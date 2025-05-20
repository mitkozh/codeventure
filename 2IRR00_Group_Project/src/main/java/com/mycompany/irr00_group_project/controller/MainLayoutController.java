package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.utils.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Controller for the main layout of the application.
 * This class is responsible for initializing the main layout and setting up the content area.
 */
public class MainLayoutController {

    @FXML
    public HBox rootLayout;
    @FXML
    public AnchorPane contentArea;

    @FXML
    public void initialize() {
        NavigationManager.getInstance().setContentArea(contentArea);
    }
}
