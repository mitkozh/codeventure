package com.mycompany.irr00_group_project.view.screen;

import java.io.IOException;

import javafx.stage.Stage;

/**
 * The SettingsScreen class is responsible for displaying the settings screen of the application. 
 * It extends the AbstractScreen class, which provides common functionality for loading FXML files,
 * applying CSS styles, and setting up the stage.
 */
public class SettingsScreen extends AbstractScreen {

    public void display(Stage primaryStage) throws IOException {
        super.display(primaryStage);
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/SettingsScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Codeventure - Settings";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/settingsMenuStyle.css";
    }
}
