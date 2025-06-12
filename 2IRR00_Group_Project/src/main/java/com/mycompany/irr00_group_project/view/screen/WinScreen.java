package com.mycompany.irr00_group_project.view.screen;

import com.mycompany.irr00_group_project.controller.WinScreenController;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * This class represents the Win Screen of the game.
 */
public class WinScreen extends AbstractScreen {
    private Runnable onRestart;

    /**
     * Constructor for WinScreen.
     *
     * @param onRestart Runnable to execute when the restart action is triggered.
     */
    public WinScreen(Runnable onRestart) {
        this.onRestart = onRestart;
    }

    @Override
    public Parent getView() throws IOException {
        super.getView();
        WinScreenController controller = fxmlLoader.getController();
        controller.setOnRestart(onRestart);
        return root;
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/WinScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/popupScreenStyle.css";
    }
}
