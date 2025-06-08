package com.mycompany.irr00_group_project.view.screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.mycompany.irr00_group_project.controller.GameScreenController;

import com.mycompany.irr00_group_project.utils.StringUtils;
import javafx.fxml.FXMLLoader;

/**
 * Class for implementing game screen.
 */
public class GameScreen extends AbstractScreen {
    private final int levelNumber;
    private String levelFile;
    private GameScreenController controller;

    /**
     * Constructor for the GameScreen class.
     * It initializes the screen with a specific level file and level number.
     *
     * @param levelFile   The file path of the level to be loaded.
     * @param levelNumber The number of the level to be displayed.
     */
    public GameScreen(String levelFile, int levelNumber) {
        this.levelFile = levelFile;
        this.levelNumber = levelNumber;
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
    protected void loadFxml() throws IOException {
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
        controller.setLevelNumber(levelNumber);
        fxmlLoader = new FXMLLoader(fxmlUrl);
        fxmlLoader.setController(controller);
        this.root = fxmlLoader.load();
    }

}
