package com.mycompany.irr00_group_project.view.screen;

/**
 * The LevelSelectionScreen class is responsible for displaying the level
 * selection screen of the application. Loading of the FXML and CSS files is
 * handled by the AbstractScreen class.
 */
public class LevelSelectionScreen extends AbstractScreen {

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/LevelSelectionScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/levelSelectionStyle.css";
    }
}
