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

    /**
     * Constructs a controller for the code editor view.
     * @param view The CodeEditorArea view component
     */
    public CodeEditorAreaController(CodeEditorArea view) {
        this.view = view;
    }

    /**
     * Initializes the code editor area with default settings.
     */
    public void initialize() {
        this.codeEditor = view.getCodeEditor();
        setupCodeEditor();
    }

    /**
     * Configures the code editor with default properties.
     */
    private void setupCodeEditor() {
        codeEditor.setText(Constants.DEFAULT_CODE);
        codeEditor.setWrapText(true);
        codeEditor.setPrefHeight(400);
    }

    /**
     * Gets the current code from the editor.
     * @return The text content of the code editor
     */
    public String getCode() {
        return codeEditor.getText();
    }

    /**
     * Sets the code in the editor.
     * @param code The code text to set
     */
    public void setCode(String code) {
        codeEditor.setText(code);
    }

    /**
     * Clears all code from the editor.
     */
    public void clearCode() {
        codeEditor.clear();
    }

}
