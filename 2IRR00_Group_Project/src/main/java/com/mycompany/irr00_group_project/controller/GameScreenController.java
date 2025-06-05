package com.mycompany.irr00_group_project.controller;

import java.io.File;
import java.io.IOException;

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
import com.mycompany.irr00_group_project.view.screen.LevelSelectionScreen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

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
    private String levelFile;

    private SharedJarServiceImpl sharedJarService;
    private UserCodeCompilationService compilationService;
    private UserCodeExecutionService executionService;
    private IPCService ipcService;
    private File resolvedSharedJarPath;

    private final Queue<Runnable> commandQueue = new LinkedList<>();
    private boolean isProcessingQueue = false;

    /**
     * Initialization of game screen.
     */
    @FXML
    public void initialize() {
        gameState = new GameState(levelFile);
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
            consoleOutputController.logError("CRITICAL: Error resolving shared.jar: " 
                + e.getMessage());
            e.printStackTrace();
            runCodeButton.setDisable(true);
        }

        loadLevel(levelFile);
        codeEditorController.setCode(Constants.DEFAULT_CODE);
        stopExecutionButton.setDisable(true);
    }

    private void loadLevel(String levelFile) {
        if (gameState == null) {
            gameState = new GameState(levelFile);
        }
        gameState.loadFromFile(levelFile);
        gameGridController.loadLevelFromGameState(gameState);
        consoleOutputController.appendMessage("Loaded level: " + levelFile);
        levelTitle.setText("Level: "
                + levelFile.replace(".txt", ""));
    }

    /**
     * fxml method to run the code of the user.
     */
    @FXML
    public void runCode(ActionEvent event) {
        if (isExecuting) {
            return;
        }

        if (!isSharedJarValid()) {
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
        compileAndExecuteCode(code);
    }

    private boolean isSharedJarValid() {
        if (resolvedSharedJarPath == null || !resolvedSharedJarPath.exists()) {
            consoleOutputController.logError("Cannot run code: shared.jar is not available.");
            return false;
        }
        return true;
    }

    private void compileAndExecuteCode(String code) {
        CompilationResult result = compilationService.compile(code, 
            resolvedSharedJarPath.getAbsolutePath());

        if (!isCompilationSuccessful(result)) {
            return;
        }

        try {
            executeUserCode(result);
        } catch (IOException e) {
            handleExecutionError(e);
        }
    }

    private boolean isCompilationSuccessful(CompilationResult result) {
        if (!result.isSuccess() || result.getCompiledClasses() == null) {
            consoleOutputController.logError(result.getFormattedDiagnostics());
            finishExecution("Compilation failed.");
            return false;
        }

        if (!result.getFormattedDiagnostics().isEmpty()
                && !result.getFormattedDiagnostics()
                        .startsWith("Compilation successful")) {
            consoleOutputController.appendMessage(
                    result.getFormattedDiagnostics());
        } else {
            consoleOutputController.appendMessage("Compilation successful.");
        }
        return true;
    }

    private void executeUserCode(CompilationResult result) throws IOException {
        consoleOutputController.appendMessage("Starting user code process...");
        Process userProcess = executionService.startUserCodeProcess(result.getCompiledClasses(),
                resolvedSharedJarPath.getAbsolutePath());

        ipcService.startIPCListeners(userProcess,
                this::handleIPCMessage,
                this::handleIPCError);

        userProcess.onExit().thenAccept(process -> {
            Platform.runLater(() -> {
                if (process.exitValue() == 0) {
                    finishExecution("User code "
                        + "execution finished successfully.");
                } else {
                    finishExecution(
                            "User code execution finished with errors (Exit code: " 
                                + process.exitValue() + ").");
                }
                executionService.cleanupTemporaryFiles();
            });
        });
    }

    private void handleExecutionError(IOException e) {
        consoleOutputController.logError("Error running user code: " + e.getMessage());
        e.printStackTrace();
        finishExecution("Execution failed due to I/O error.");
        executionService.cleanupTemporaryFiles();
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
        processIPCCommand(parts);
    }

    private void processIPCCommand(String[] parts) {
        String cmdType = parts[1];
        String arg = (parts.length > 2) ? parts[2] : null;

        Runnable command = null;
        switch (cmdType) {
            case "MOVE_FORWARD":
                command = this::handleMoveForward;
                break;
            case "TURN_LEFT":
                command = this::handleTurnLeft;
                break;
            case "TURN_RIGHT":
                command = this::handleTurnRight;
                break;
            case "LOG_MESSAGE":
                command = () -> handleLogMessage(arg);
                break;
            case "ERROR":
                command = () -> handleError(arg);
                break;
            case "EXECUTION_COMPLETE":
                command = () -> consoleOutputController.appendMessage("User script signaled completion.");
                break;
            default:
                command = () -> consoleOutputController.logError("Unknown IPC command: " + cmdType);
        }
        if (command != null) {
            commandQueue.add(command);
            processCommandQueue();
        }
    }

    private void processCommandQueue() {
        if (isProcessingQueue || commandQueue.isEmpty()) {
            return;
        }
        isProcessingQueue = true;
        Runnable command = commandQueue.poll();
        if (command != null) {
            command.run();
        }
        
        PauseTransition pause = new PauseTransition(Duration.millis(300)); // adjust delay as needed
        pause.setOnFinished(event -> {
            isProcessingQueue = false;
            if (!commandQueue.isEmpty()) {
                processCommandQueue();
            }
        });
        pause.play();
    }

    private void handleMoveForward() {
        if (gameState.getSprite() != null) {
            gameState.getSprite().moveForward();
        }
        gameGridController.updateSpritePosition();
    }

    private void handleTurnLeft() {
        if (gameState.getSprite() != null) {
            gameState.getSprite().turnLeft();
        }
        gameGridController.updateSpritePosition();
    }

    private void handleTurnRight() {
        if (gameState.getSprite() != null) {
            gameState.getSprite().turnRight();
        }
        gameGridController.updateSpritePosition();
    }

    private void handleLogMessage(String arg) {
        if (arg != null) {
            consoleOutputController.appendMessage("UserScript: " + arg);
        }
    }

    private void handleError(String arg) {
        if (arg != null) {
            consoleOutputController.logError("UserScript Error: " + arg);
        }
    }

    /**
     * fxml method to stop executing the code of the user.
     */
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

    /**
     * fxml method to reset the level.
     */
    @FXML
    public void resetLevel(ActionEvent event) {
        if (isExecuting) {
            consoleOutputController.logError("Stop execution first before resetting.");
            return;
        }
        setExecutionState(false);
        loadLevel(levelFile);
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

    /**
     * fxml method to open the in-game settings.
     * @param actionEvent .
     */
    public void onSettingsClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/mycompany/irr00_group_project/view/screen/SettingsScreen.fxml"));
            Parent settingsView = loader.load();
            // Apply CSS for in-game settings look
            settingsView.getStylesheets().add(
                getClass().getResource("/com/mycompany/"
                + "irr00_group_project/assets/css/"
                + "settingsMenuStyle.css").toExternalForm()
            );
            SettingsController controller = loader.getController();
            controller.setOnExit(() -> NavigationManager.getInstance().navigateTo(rootPane));
            controller.setOnGoBack(() -> {
            try {
                NavigationManager.getInstance().navigateTo(new LevelSelectionScreen().getView());
            } catch (Exception e) {
                e.printStackTrace();
                consoleOutputController.logError("Error returning to level selection: " + e.getMessage());
            }
            });


            NavigationManager.getInstance().navigateTo(settingsView);
        } catch (Exception e) {
            e.printStackTrace();
            consoleOutputController.logError("Error opening settings: " + e.getMessage());
        }
    }

    public void setLevelFile(String levelFile) {
        this.levelFile = levelFile;
    }
}