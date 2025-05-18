package com.mycompany.irr00_group_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * .
 */
public class SettingsController {


    //todo: implement real logic
    private String currentCharacter = "Robot";

    @FXML
    private ComboBox<String> characterComboBox;

    @FXML
    private void initialize() {
        characterComboBox.setValue(currentCharacter);
    }

    public void handleClose(ActionEvent actionEvent) {
    }

    public void backToMenu(ActionEvent actionEvent) {

    }
}
