package com.mycompany.irr00_group_project.view.screen;

/**
 * The HelpScreen class is responsible for displaying the help
 * screen of the application which assists users by providing game instructions. 
 * Loading of the FXML and CSS files is
 * handled by the AbstractScreen class.
 */
public class HelpScreen extends AbstractScreen {
    
    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/HelpScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/helpStyle.css";
    }
}

