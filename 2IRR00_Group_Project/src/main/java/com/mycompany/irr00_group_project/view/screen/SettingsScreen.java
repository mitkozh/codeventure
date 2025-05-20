package com.mycompany.irr00_group_project.view.screen;

/**
 * The SettingsScreen class is responsible for displaying the settings screen of
 * the application. Loading of the FXML and CSS files is handled by the
 * AbstractScreen class.
 */
public class SettingsScreen extends AbstractScreen {

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/SettingsScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/settingsMenuStyle.css";
    }
}
