package com.mycompany.irr00_group_project.view.screen;

import com.mycompany.irr00_group_project.controller.GameScreenController;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.utils.StringUtils;
import javafx.fxml.FXMLLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Class for implementing game screen.
 */
public class GameScreen extends AbstractScreen {
    private final LevelDTO levelNumber;
    private GameScreenController controller;

    /**
     * Constructor for the GameScreen class.
     * It initializes the screen with a specific level file and level number.
     *
     * @param levelDTO The data transfer object containing the level
     *                 number and other details.
     */
    public GameScreen(LevelDTO levelDTO) {
        this.levelNumber = levelDTO;
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
        controller.setLevelDTO(levelNumber);
        fxmlLoader = new FXMLLoader(fxmlUrl);
        fxmlLoader.setController(controller);
        this.root = fxmlLoader.load();
    }

}
