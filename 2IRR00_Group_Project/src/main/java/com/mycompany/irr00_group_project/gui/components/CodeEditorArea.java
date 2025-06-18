package com.mycompany.irr00_group_project.gui.components;

import com.mycompany.irr00_group_project.controller.components.CodeEditorAreaController;
import com.mycompany.irr00_group_project.utils.Constants;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * Component for the code editor area.
 * Provides a text area for code input with default styling and behavior.
 */
public class CodeEditorArea {

    private CodeEditorAreaController controller;
    private VBox rootContainer;
    private VBox codeEditorContainer;
    private TextArea codeEditor;

    public CodeEditorArea() {
        controller = new CodeEditorAreaController(this);
        createUI();
    }

    private void createUI() {
        rootContainer = new VBox(10);
        rootContainer.getStyleClass().add("code-editor-panel");

        codeEditorContainer = new VBox();
        codeEditorContainer.getStyleClass().add("code-editor-container");
        VBox.setVgrow(codeEditorContainer, javafx.scene.layout.Priority.ALWAYS);

        codeEditorContainer.setPadding(new Insets(15.0, 15.0, 15.0, 15.0));

        codeEditor = new TextArea();
        codeEditor.getStyleClass().add("code-editor");
        codeEditor.setText(Constants.DEFAULT_CODE);
        codeEditor.setWrapText(true);
        VBox.setVgrow(codeEditor, javafx.scene.layout.Priority.ALWAYS);

        codeEditorContainer.getChildren().add(codeEditor);
        rootContainer.getChildren().add(codeEditorContainer);
        controller.initialize();
    }

    public Parent getView() {
        return rootContainer;
    }

    public VBox getRootContainer() {
        return rootContainer;
    }

    public VBox getCodeEditorContainer() {
        return codeEditorContainer;
    }

    public TextArea getCodeEditor() {
        return codeEditor;
    }

    public CodeEditorAreaController getController() {
        return controller;
    }
}