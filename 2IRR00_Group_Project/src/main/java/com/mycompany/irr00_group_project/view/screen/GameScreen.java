package com.mycompany.irr00_group_project.view.screen;

import java.io.IOException;

import com.mycompany.irr00_group_project.controller.GameScreenController;

import javafx.scene.Parent;

/**
 * .
 */
public class GameScreen extends AbstractScreen {
    private String levelFile;

    public GameScreen(String levelFile) {
        this.levelFile = levelFile;
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/GameScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/gameStyle.css";
    }

    @Override
    public Parent getView() throws IOException {
        loadFxml();
        GameScreenController controller = getController();
        controller.setLevelFile(levelFile); // Pass the level file to the controller
        applyCssToRoot();
        return root;
    }

    public GameScreenController getController() {
        return (GameScreenController) fxmlLoader.getController();
    }
}
