package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.controller.components.CodeEditorAreaController;
import com.mycompany.irr00_group_project.controller.components.ConsoleOutputController;
import com.mycompany.irr00_group_project.controller.components.GameGridController;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.SettingsScreen;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * .
 */
public class GameScreenController {
    @FXML private Label levelTitle;
    @FXML private Button runCodeButton;
    @FXML private Button stopExecutionButton;
    @FXML private Button resetLevelButton;


    @FXML private GameGridController gameGridController;
    @FXML private CodeEditorAreaController codeEditorController;
    @FXML private ConsoleOutputController consoleOutputController;

    private boolean isExecuting = false;
    private Task<Void> executionTask;
    private GameState gameState;


    @FXML
    public void initialize() {
        gameState = new GameState();
        loadLevel("level1.txt");
    }

    private void loadLevel(String levelFile) {
        gameState.loadFromFile(levelFile);
        gameGridController.loadLevelFromGameState(gameState);
        consoleOutputController.clear();
        levelTitle.setText("Level 1");
    }

    @FXML
    public void runCode(ActionEvent event) {
        if (isExecuting) return;

        String code = codeEditorController.getCode();
        if (code.trim().isEmpty()) {
            consoleOutputController.logError("No code to execute");
            return;
        }

        startExecution();

        executionTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //code execution logic
                return null;
            }

            @Override
            protected void succeeded() {
                finishExecution("Execution completed successfully");
            }

            @Override
            protected void failed() {
                finishExecution("Execution failed: " + getException().getMessage());
            }
        };

        Thread executionThread = new Thread(executionTask);
        executionThread.setDaemon(true);
        executionThread.start();
    }

    @FXML
    public void stopExecution(ActionEvent event) {
        if (executionTask != null && !executionTask.isDone()) {
            executionTask.cancel(true);
            finishExecution("Execution stopped by user");
        }
    }

    @FXML
    public void resetLevel(ActionEvent event) {
        if (isExecuting) {
            stopExecution(event);
        }

        loadLevel("level1.txt");
        consoleOutputController.appendMessage("Level reset");
    }

    private void startExecution() {
        isExecuting = true;
        runCodeButton.setDisable(true);
        stopExecutionButton.setDisable(false);
    }

    private void finishExecution(String message) {
        isExecuting = false;
        runCodeButton.setDisable(false);
        stopExecutionButton.setDisable(true);
        consoleOutputController.appendMessage(message);
    }

    public void onSettingsClick(ActionEvent actionEvent) {
        try {
            SettingsScreen settings = new SettingsScreen();
            NavigationManager.getInstance().navigateTo(settings.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
