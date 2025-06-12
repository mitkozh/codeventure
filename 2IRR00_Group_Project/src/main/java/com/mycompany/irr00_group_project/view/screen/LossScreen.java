
package com.mycompany.irr00_group_project.view.screen;

import com.mycompany.irr00_group_project.controller.LossScreenController;
import com.mycompany.irr00_group_project.controller.SettingsController;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * 
 * LossScreen is a class that represents the loss screen in the game.
 */
public class LossScreen extends AbstractScreen {
    private Runnable onRestart;

    /**
     * Constructor for LossScreen.
     *
     * @param onRestart Runnable to execute when the restart action is triggered.
     */
    public LossScreen(Runnable onRestart) {
        this.onRestart = onRestart;
    }

    @Override
    public Parent getView() throws IOException {
        super.getView();
        LossScreenController controller = fxmlLoader.getController();
        controller.setOnRestart(onRestart);
        return root;
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/LossScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/popupScreenStyle.css";
    }
}
