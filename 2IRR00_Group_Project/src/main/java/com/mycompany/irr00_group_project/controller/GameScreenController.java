package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.controller.components.CodeEditorAreaController;
import com.mycompany.irr00_group_project.controller.components.ConsoleOutputController;
import com.mycompany.irr00_group_project.controller.components.GameGridController;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.InGameSettingsScreen;
import com.mycompany.irr00_group_project.view.screen.SettingsScreen;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;

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
    @FXML private Parent rootPane;

    private boolean isExecuting = false;
    private Task<Void> executionTask;
    private GameState gameState;
    private String levelFile;


    @FXML
    public void initialize() {
        if (levelFile == null) {
            levelFile = "level1.txt"; // default option for if the level file is null
        }
        gameState = new GameState(levelFile);
        loadLevel(levelFile);
    }

    private void loadLevel(String levelFile) {
        gameState.loadFromFile(levelFile);
        gameGridController.loadLevelFromGameState(gameState);
        consoleOutputController.clear();
        // Extract level number from filename
        String levelNum = levelFile.replaceAll("\\D+", "");
        levelTitle.setText("Level " + levelNum);
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

        loadLevel(levelFile);
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
           InGameSettingsScreen settings = new InGameSettingsScreen();
            Parent settingsView = settings.getView();
            InGameSettingsController controller = settings.getController();
            controller.setPreviousScreen(rootPane);
            NavigationManager.getInstance().navigateTo(settingsView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //setter for the level file
    public void setLevelFile(String levelFile) {
        this.levelFile = levelFile;
    }

}
