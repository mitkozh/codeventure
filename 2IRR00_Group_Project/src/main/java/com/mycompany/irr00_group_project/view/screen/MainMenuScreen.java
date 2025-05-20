package com.mycompany.irr00_group_project.view.screen;

/**
 * The MainMenuScreen class is responsible for displaying the main menu screen
 * of the application. Loading of the FXML and CSS files is handled by the
 * AbstractScreen class.
 */
public class MainMenuScreen extends AbstractScreen {
    
    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/MainMenuScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/mainMenuStyle.css";
    }
}
