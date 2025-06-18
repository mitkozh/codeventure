package com.mycompany.irr00_group_project.gui.components;

import com.mycompany.irr00_group_project.controller.components.ConsoleOutputController;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * A graphical component that displays console output in a scrollable, read-only text area.
 * Handles the visual presentation of program output, errors, and other messages.
 */
public class ConsoleOutputArea {

    private ConsoleOutputController controller;
    private VBox rootContainer;
    private VBox consoleOutputContainer;
    private TextArea consoleOutput;

    public ConsoleOutputArea() {
        controller = new ConsoleOutputController(this);
        createUI();
    }

    private void createUI() {
        rootContainer = new VBox();
        rootContainer.getStyleClass().add("console-container");

        consoleOutputContainer = new VBox();
        consoleOutputContainer.getStyleClass().add("console-output-container");
        VBox.setVgrow(consoleOutputContainer, javafx.scene.layout.Priority.ALWAYS);

        consoleOutputContainer.setPadding(new Insets(15.0, 15.0, 15.0, 15.0));

        consoleOutput = new TextArea();
        consoleOutput.getStyleClass().add("console-output-area");
        consoleOutput.setEditable(false);
        consoleOutput.setWrapText(true);
        VBox.setVgrow(consoleOutput, javafx.scene.layout.Priority.ALWAYS);

        consoleOutputContainer.getChildren().add(consoleOutput);
        rootContainer.getChildren().add(consoleOutputContainer);
        controller.initialize();
    }

    public Parent getView() {
        return rootContainer;
    }

    public VBox getRootContainer() {
        return rootContainer;
    }

    public VBox getConsoleOutputContainer() {
        return consoleOutputContainer;
    }

    public TextArea getConsoleOutput() {
        return consoleOutput;
    }

    public ConsoleOutputController getController() {
        return controller;
    }
}