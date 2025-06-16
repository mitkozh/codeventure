package com.mycompany.irr00_group_project.controller.components;

import com.mycompany.irr00_group_project.gui.components.CodeEditorArea;
import com.mycompany.irr00_group_project.utils.Constants;

import javafx.scene.control.TextArea;

/**
 * Controller for the code editor area in the application.
 * This class manages the text area where users can write and edit code.
 */
public class CodeEditorAreaController {
    private TextArea codeEditor;
    private CodeEditorArea view;


    public CodeEditorAreaController(CodeEditorArea view) {
        this.view = view;
    }

    /**
     * Initializes the code editor area.
     */
    public void initialize() {
        this.codeEditor = view.getCodeEditor();
        setupCodeEditor();
    }

    private void setupCodeEditor() {
        codeEditor.setText(Constants.DEFAULT_CODE);
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
