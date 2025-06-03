package com.mycompany.irr00_group_project.controller.components;

import javafx.fxml.FXML;

import javafx.scene.control.TextArea;

/**
 * Controller for the code editor area in the application.
 * This class manages the text area where users can write and edit code.
 */
public class CodeEditorAreaController {
    @FXML
    private TextArea codeEditor;

    @FXML
    public void initialize() {
        setupCodeEditor();
    }

    private void setupCodeEditor() {
        codeEditor.setWrapText(true);
        codeEditor.setPrefHeight(400);
    }

    public String getCode() {
        return codeEditor.getText();
    }

    public void setCode(String code) {
        codeEditor.setText(code);
    }

    public void clearCode() {
        codeEditor.clear();
    }

}
