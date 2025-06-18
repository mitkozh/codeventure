package com.mycompany.irr00_group_project.controller.components;

import com.mycompany.irr00_group_project.gui.components.ConsoleOutputArea;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Controller for the console output area in the application.
 * This class manages the text area where console messages are displayed.
 */
public class ConsoleOutputController {

    private ConsoleOutputArea view;
    private TextArea consoleOutput;

    /**
     * Constructs a controller for the console output view.
     * @param view The ConsoleOutputArea view component
     */
    public ConsoleOutputController(ConsoleOutputArea view) {
        this.view = view;
    }

    /**
     * Initializes the console output area.
     */
    public void initialize() {
        this.consoleOutput = view.getConsoleOutput();
        setupConsole();
    }

    /**
     * Configures console output properties.
     */
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

    /**
     * Clears all messages from the console.
     */
    public void clear() {
        Platform.runLater(() -> consoleOutput.clear());
    }

    /**
     * Logs an error message to the console.
     * @param error The error message to log
     */
    public void logError(String error) {
        appendMessage("ERROR: " + error);
    }
}