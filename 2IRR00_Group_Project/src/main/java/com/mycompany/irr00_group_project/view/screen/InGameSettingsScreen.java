package com.mycompany.irr00_group_project.view.screen;

import java.io.IOException;

import com.mycompany.irr00_group_project.App;
import com.mycompany.irr00_group_project.controller.SettingsController;
import com.mycompany.irr00_group_project.service.core.impl.AudioManagerServiceImpl;

import javafx.scene.Parent;

/**
 * The SettingsScreen class is responsible for displaying the settings screen of
 * the application. Loading of the FXML and CSS files is handled by the
 * AbstractScreen class.
 */
public class InGameSettingsScreen extends AbstractScreen {

    @Override
    public Parent getView() throws IOException {
        loadFxml();
        // Inject AudioManagerService after loading FXML
        SettingsController controller = fxmlLoader.getController();
        controller.setAudioManagerService(App.getAudioManagerService());
        applyCssToRoot();
        return root;
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/InGameSettingsScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/settingsMenuStyle.css";
    }
}
