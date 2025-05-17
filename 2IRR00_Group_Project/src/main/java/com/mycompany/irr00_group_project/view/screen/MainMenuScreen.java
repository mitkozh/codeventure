package com.mycompany.irr00_group_project.view.screen;

import javafx.stage.Stage;
import java.io.IOException;

/**
 * The MainMenuScreen class is responsible for displaying the main menu screen of the application. 
 * It extends the AbstractScreen class, which provides common functionality for loading FXML files,
 * applying CSS styles, and setting up the stage.
 */
public class MainMenuScreen extends AbstractScreen {
    /**
     * Displays the main menu screen.
     *
     * @param primaryStage the primary stage for this application
     * @throws IOException if an I/O error occurs while loading the FXML file or font
     */
    public void display(Stage primaryStage) throws IOException {
        super.display(primaryStage);
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/MainMenuScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Codeventure";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/mainMenuStyle.css";
    }
}
