package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.controller.components.CodeEditorAreaController;
import com.mycompany.irr00_group_project.controller.components.ConsoleOutputController;
import com.mycompany.irr00_group_project.controller.components.GameGridController;
import com.mycompany.irr00_group_project.model.core.CompilationResult;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.service.ipc.IPCService;
import com.mycompany.irr00_group_project.service.resources.impl.SharedJarServiceImpl;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeCompilationService;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeExecutionService;
import com.mycompany.irr00_group_project.utils.Constants;
import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.screen.InGameSettingsScreen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;

/**
 * Controller for the main game screen, handling user interactions and game
 * logic.
 */
public class GameScreenController {
    @FXML
    private Label levelTitle;
    @FXML
    private Button runCodeButton;
    @FXML
    private Button stopExecutionButton;
    @FXML
    private Button resetLevelButton;

    @FXML
    private GameGridController gameGridController;
    @FXML
    private CodeEditorAreaController codeEditorController;
    @FXML
    private ConsoleOutputController consoleOutputController;
    @FXML
    private Parent rootPane;

    private boolean isExecuting = false;
    private GameState gameState;

    private SharedJarServiceImpl sharedJarService;
    private UserCodeCompilationService compilationService;
    private UserCodeExecutionService executionService;
    private IPCService ipcService;
    private File resolvedSharedJarPath;

    @FXML
    public void initialize() {
        gameState = new GameState();
        sharedJarService = new SharedJarServiceImpl();
        compilationService = new UserCodeCompilationService();
        executionService = new UserCodeExecutionService();
        ipcService = new IPCService();

        try {
            resolvedSharedJarPath = sharedJarService.getResolvedSharedJarFile();
            if (resolvedSharedJarPath == null || !resolvedSharedJarPath.exists()) {
                consoleOutputController.logError("CRITICAL:"
                        + " shared.jar not found or accessible!");
                runCodeButton.setDisable(true);
            } else {
                consoleOutputController
                        .appendMessage("Resolved shared.jar to: "
                                + resolvedSharedJarPath.getAbsolutePath());
            }
        } catch (IOException e) {
            consoleOutputController.logError("CRITICAL: Error resolving shared.jar: " + e.getMessage());
            e.printStackTrace();
            runCodeButton.setDisable(true);
        }

        loadLevel("level1.txt");
        codeEditorController.setCode(Constants.DEFAULT_CODE);
        stopExecutionButton.setDisable(true);
    }

    private void loadLevel(String levelFile) {
        if (gameState == null) {
            gameState = new GameState();
        }
        gameState.loadFromFile(levelFile);
        gameGridController.loadLevelFromGameState(gameState);
        consoleOutputController.appendMessage("Loaded level: " + levelFile);
        levelTitle.setText("Level: "
                + levelFile.replace(".txt", ""));
    }

    @FXML
    public void runCode(ActionEvent event) {
        if (isExecuting)
            return;

        if (resolvedSharedJarPath == null || !resolvedSharedJarPath.exists()) {
            consoleOutputController.logError("Cannot run code: shared.jar is not available.");
            return;
        }

        String code = codeEditorController.getCode();
        if (code.trim().isEmpty()) {
            consoleOutputController.logError("No code to execute");
            return;
        }
        consoleOutputController.clear();
        consoleOutputController.appendMessage("Compiling user code...");
        setExecutionState(true);

        CompilationResult result = compilationService.compile(code, resolvedSharedJarPath.getAbsolutePath());

        if (!result.isSuccess() || result.getCompiledClasses() == null) {
            consoleOutputController.logError(result.getFormattedDiagnostics());
            finishExecution("Compilation failed.");
            return;
        }

        if (!result.getFormattedDiagnostics().isEmpty()
                && !result.getFormattedDiagnostics()
                        .startsWith("Compilation successful")) {
            consoleOutputController.appendMessage(
                    result.getFormattedDiagnostics());
        } else {
            consoleOutputController.appendMessage("Compilation successful.");
        }

        try {
            consoleOutputController.appendMessage("Starting user code process...");
            Process userProcess = executionService.startUserCodeProcess(result.getCompiledClasses(),
                    resolvedSharedJarPath.getAbsolutePath());

            ipcService.startIPCListeners(userProcess,
                    this::handleIPCMessage,
                    this::handleIPCError);

            userProcess.onExit().thenAccept(process -> {
                Platform.runLater(() -> {
                    if (process.exitValue() == 0) {
                        finishExecution("User code execution finished successfully.");
                    } else {
                        finishExecution(
                                "User code execution finished with errors (Exit code: " + process.exitValue() + ").");
                    }
                    executionService.cleanupTemporaryFiles();
                });
            });

        } catch (IOException e) {
            consoleOutputController.logError("Error running user code: " + e.getMessage());
            e.printStackTrace();
            finishExecution("Execution failed due to I/O error.");
            executionService.cleanupTemporaryFiles();
        }
    }

    private void setExecutionState(boolean executing) {
        isExecuting = executing;
        Platform.runLater(() -> {
            runCodeButton.setDisable(executing);
            stopExecutionButton.setDisable(!executing);
            resetLevelButton.setDisable(executing); // Disable reset while running
        });
    }

    private void handleIPCMessage(String message) {
        Platform.runLater(() -> {
            if (message.startsWith("CMD:")) {
                handleIPCCommand(message);
            } else {
                consoleOutputController.appendMessage("UserOutput: " + message);
            }
        });
    }

    private void handleIPCError(String error) {
        Platform.runLater(() -> consoleOutputController.logError(error));
    }

    private void handleIPCCommand(String command) {
        consoleOutputController.appendMessage("IPC Command: " + command);
        String[] parts = command.split(":", 3);
        if (parts.length < 2) {
            consoleOutputController.logError("Malformed IPC command: " + command);
            return;
        }
        String cmdType = parts[1];
        String arg = (parts.length > 2) ? parts[2] : null;

        switch (cmdType) {
            case "MOVE_FORWARD":
                if (gameState.getSprite() != null) {
                    gameState.getSprite().moveForward();
                }

                gameGridController.updateSpritePosition();
                break;
            case "TURN_LEFT":
                if (gameState.getSprite() != null) {
                    gameState.getSprite().turnLeft();
                }
                gameGridController.updateSpritePosition();
                break;
            case "TURN_RIGHT":
                if (gameState.getSprite() != null) {
                    gameState.getSprite().turnRight();
                }
                gameGridController.updateSpritePosition();
                break;
            case "LOG_MESSAGE":
                if (arg != null)
                    consoleOutputController.appendMessage("UserScript: " + arg);
                break;
            case "ERROR":
                if (arg != null)
                    consoleOutputController.logError("UserScript Error: " + arg);
                break;
            case "EXECUTION_COMPLETE":
                consoleOutputController.appendMessage("User script signaled completion.");
                break;
            default:
                consoleOutputController.logError("Unknown IPC command: " + command);
        }
    }

    @FXML
    public void stopExecution(ActionEvent event) {
        if (!isExecuting) {
            return;
        }
        consoleOutputController.logError("User initiated stop.");
        ipcService.stopListeners();
        executionService.stopCurrentProcess();
        if (isExecuting) {
            finishExecution("Execution stopped by user.");
            executionService.cleanupTemporaryFiles();
        }
    }

    @FXML
    public void resetLevel(ActionEvent event) {
        if (isExecuting) {
            consoleOutputController.logError("Cannot reset while code is executing. Stop execution first.");
            return;
        }
        setExecutionState(false);
        loadLevel("level1.txt");
        consoleOutputController.appendMessage("Level reset.");
    }

    private void finishExecution(String message) {
        setExecutionState(false);
        Platform.runLater(() -> {
            if (message != null && !message.trim().isEmpty()) {
                consoleOutputController.appendMessage(message);
            }
        });
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
            consoleOutputController.logError("Error opening settings: " + e.getMessage());
        }
    }
}