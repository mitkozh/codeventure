package com.mycompany.irr00_group_project.controller.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller for the console output area in the application.
 * This class manages the text area where console messages are displayed.
 */
public class ConsoleOutputController {

    @FXML
    private TextArea consoleOutput;

    @FXML
    public void initialize() {
        setupConsole();
    }

    private void setupConsole() {
        consoleOutput.setEditable(false);
    }

    /**
     * Appends a message to the console output.
     *
     * @param message The message to append.
     */
    public void appendMessage(String message) {
        Platform.runLater(() -> {
            consoleOutput.appendText(message + "\n");
            consoleOutput.setScrollTop(Double.MAX_VALUE);
        });
    }

    public void clear() {
        Platform.runLater(() -> consoleOutput.clear());
    }

    public void logError(String error) {
        appendMessage("ERROR: " + error);
    }
}