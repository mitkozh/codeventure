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

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/GameScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/gameStyle.css";
    }

}
