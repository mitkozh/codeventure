package com.mycompany.irr00_group_project.view.screen;

import java.io.IOException;

import com.mycompany.irr00_group_project.controller.SettingsController;
import javafx.scene.Parent;

/**
 * The SettingsScreen class is responsible for displaying the settings screen of
 * the application. Loading of the FXML and CSS files is handled by the
 * AbstractScreen class.
 */
public class SettingsScreen extends AbstractScreen {
    Runnable onExit;
    Runnable onBack;
    String backButtonContent = "GO TO MENU";

    /**
     * Constructor for the SettingsScreen class.
     * It passes an onExit and onBack action.
     *
     * @param onExit Runnable action to be executed when exiting the settings screen.
     * @param onBack Runnable action to be executed when going back from the settings screen.
     */
    public SettingsScreen(Runnable onExit, Runnable onBack, String content) {
        this.onExit = onExit;
        this.onBack = onBack;
        this.backButtonContent = content;
    }

    /**
     * Default constructor for the SettingsScreen class.
     * It initializes the screen without any specific actions on exit or back.
     */
    public SettingsScreen() {
    }

    @Override
    public Parent getView() throws IOException {
        super.getView();
        SettingsController settingsController = fxmlLoader.getController();
        settingsController.setOnExit(onExit);
        settingsController.setOnGoBack(onBack);
        settingsController.setBackToMenuButtonContent(backButtonContent);
        return root;
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/SettingsScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/settingsMenuStyle.css";
    }
}
