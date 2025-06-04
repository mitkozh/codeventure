package com.mycompany.irr00_group_project.view.screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.mycompany.irr00_group_project.controller.GameScreenController;

import com.mycompany.irr00_group_project.utils.StringUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Class for implementing game screen.
 */
public class GameScreen extends AbstractScreen {
    private String levelFile;
    private GameScreenController controller;

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
        loadFxmlCustom();
        applyCssToRoot();
        return root;
    }

    private void loadFxmlCustom() throws IOException {
        controller = new GameScreenController();
        String path = getFxmlPath();
        if (StringUtils.isNullOrWhiteSpace(path)) {
            throw new IllegalArgumentException("FXML path is null or empty");
        }
        URL fxmlUrl = getClass().getResource(path);
        if (fxmlUrl == null) {
            throw new FileNotFoundException("FXML file not found: " + path);
        }
        controller.setLevelFile(levelFile);
        fxmlLoader = new FXMLLoader(fxmlUrl);
        fxmlLoader.setController(controller);
        this.root = fxmlLoader.load();
    }

}
