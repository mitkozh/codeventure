
package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.LossScreenController;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * 
 * LossScreen is a class that represents the loss screen in the game.
 */
public class LossScreen extends AbstractScreen {

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/gui/screen/LossScreen.fxml";
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/popupScreenStyle.css";
    }
}
