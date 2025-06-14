package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.HelpController;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * The HelpScreen class is responsible for displaying the help
 * screen of the application which assists users by providing game instructions.
 * Loading of the FXML and CSS files is
 * handled by the AbstractScreen class.
 */
public class HelpScreen extends AbstractScreen {

    private Runnable onExit;

    /**
     * Constructor for HelpScreen.
     * It initializes the screen with an exit action.
     *
     * @param onExit Runnable action to be executed when exiting the help screen.
     */
    public HelpScreen(Runnable onExit) {
        this.onExit = onExit;
    }

    public HelpScreen() {

    }

    @Override
    public Parent getView() throws IOException {
        super.getView();
        HelpController settingsController = fxmlLoader.getController();
        settingsController.setOnExit(onExit);
        return root;
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/gui/screen/HelpScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/helpStyle.css";
    }
}