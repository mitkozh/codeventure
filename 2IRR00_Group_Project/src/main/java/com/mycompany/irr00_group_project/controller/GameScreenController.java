package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.controller.components.CodeEditorAreaController;
import com.mycompany.irr00_group_project.controller.components.ConsoleOutputController;
import com.mycompany.irr00_group_project.controller.components.GameGridController;
import com.mycompany.irr00_group_project.gui.screen.GameScreen;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.model.enums.GameResult;
import com.mycompany.irr00_group_project.service.facade.GameScreenServiceFacade;
import com.mycompany.irr00_group_project.utils.Constants;
import com.mycompany.irr00_group_project.utils.StringUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the main game screen, handling user interactions and game
 * logic.
 */
public class GameScreenController {
    private Label levelTitle;
    private Button runCodeButton;
    private Button stopExecutionButton;
    private Button resetLevelButton;

    private GameGridController gameGridController;
    private CodeEditorAreaController codeEditorController;
    private ConsoleOutputController consoleOutputController;
    private Parent rootPane;
    private GameScreen view;

    private GameState gameState;
    private GameScreenServiceFacade serviceFacade;
    private LevelDTO levelDTO;

    /**
     * Constructs a controller for the game screen.
     * @param view The GameScreen view component
     */
    public GameScreenController(GameScreen view) {
        this.view = view;
    }

    /**
     * Initialization of game screen.
     */
    public void initialize() {
        setUpViewControllerBindings();
        this.serviceFacade = GameScreenServiceFacade.getInstance();
        setupObservableBindings();
        stopExecutionButton.setDisable(true);
        getCurrentLevel();
    }

    /**
     * Sets up view-controller bindings for UI components.
     */
    private void setUpViewControllerBindings() {
        this.levelTitle = view.getLevelTitle();
        this.runCodeButton = view.getRunCodeButton();
        this.stopExecutionButton = view.getStopExecutionButton();
        this.resetLevelButton = view.getResetLevelButton();
        this.rootPane = view.getRootPane();

        this.gameGridController = view.getGameGridDisplay().getController();
        this.codeEditorController = view.getCodeEditorArea().getController();
        this.consoleOutputController = view.getConsoleOutputArea().getController();
    }

    /**
     * Loads the current level data.
     */
    private void getCurrentLevel() {
        LevelDTO currentLevel = serviceFacade.getCurrentLevel();
        if (currentLevel != null) {
            levelDTO = currentLevel;
            loadLevel(currentLevel);
        }
    }

    /**
     * Sets up observable bindings for game events.
     */
    private void setupObservableBindings() {
        serviceFacade.setupObservableBindings(
                this::handleGridUpdate,
                this::handleLevelWon,
                this::handleLoss,
                this::handleConsoleMessage,
                this::handleConsoleError,
                this::handleExecutionStart,
                this::handleExecutionComplete,
                this::handleReturnToGame
        );
    }

    /**
     * Handles grid updates by re-rendering.
     */
    private void handleGridUpdate() {
        gameGridController.renderGridAndSprite();
    }

    /**
     * Handles console message output.
     * @param message The message to display
     */
    private void handleConsoleMessage(String message) {
        if (!StringUtils.isNullOrEmpty(message)) {
            consoleOutputController.appendMessage(message);
        }
    }

    /**
     * Handles console error output.
     * @param error The error message to display
     */
    private void handleConsoleError(String error) {
        if (!StringUtils.isNullOrEmpty(error)) {
            consoleOutputController.logError(error);
        }
    }

    /**
     * Handles execution start events.
     */
    private void handleExecutionStart() {
        setExecutionState(true);
    }

    /**
     * Handles execution completion events.
     * @param message The completion message
     */
    private void handleExecutionComplete(String message) {
        finishExecution(message);
    }

    /**
     * Handles return to game events.
     */
    private void handleReturnToGame() {
        serviceFacade.requestResume();
    }

    /**
     * Loads a level into the game.
     * @param level The level data to load
     */
    private void loadLevel(LevelDTO level) {
        LevelData levelData = serviceFacade.getLevelData(level);
        gameState = new GameState(levelData);
        gameGridController.loadLevelFromGameState(gameState);
        levelTitle.setText("Level: " + levelDTO.getLevelNumber());
    }

    /**
     * Executes user code when run button is clicked.
     * @param event The action event
     * fxml method to run the code of the user.
     */
    public void runCode(ActionEvent event) {
        if (!gameState.isGamePlaying()) {
            return;
        }

        String code = Constants.INITIAL_IMPORTS_CODE + codeEditorController.getCode();
        consoleOutputController.clear();
        setExecutionState(true);
        serviceFacade.executeCode(code, gameState);
    }

    /**
     * Updates UI state during execution.
     * @param executing True if code is executing
     */
    private void setExecutionState(boolean executing) {
        Platform.runLater(() -> {
            runCodeButton.setDisable(executing || !serviceFacade.isReady());
            stopExecutionButton.setDisable(!executing);
            resetLevelButton.setDisable(executing);
        });
    }

    /**
     * fxml method to stop executing the code of the user.
     */
    public void stopExecutionOnClick(ActionEvent event) {
        serviceFacade.stopExecution();
        setExecutionState(false);
    }

    /**
     * method to reset the level.
     */
    public void resetLevelOnClick(ActionEvent event) {
        resetLevel();
    }

    /**
     * Resets the current level to its initial state.
     */
    private void resetLevel() {
        if (serviceFacade.isExecuting()) {
            consoleOutputController.logError("Stop execution first before resetting.");
            return;
        }
        serviceFacade.stopExecution();
        serviceFacade.clearCommandQueue();
        setExecutionState(false);
        loadLevel(levelDTO);
    }

    /**
     * Handles execution completion and displays the result message.
     * @param message The completion message to display
     */
    private void finishExecution(String message) {
        setExecutionState(false);
        Platform.runLater(() -> {
            if (message != null && !message.trim().isEmpty()) {
                consoleOutputController.appendMessage(message);
            }
        });
    }

    /**
     * method to open the in-game settings.
     *
     * @param actionEvent .
     */
    public void onSettingsClick(ActionEvent actionEvent) {
        serviceFacade.requestPause();
        serviceFacade.navigateToSettings();
    }

    /**
     * method to open the help screen.
     *
     * @param actionEvent .
     */
    public void onHelpClick(ActionEvent actionEvent) {
        serviceFacade.requestPause();
        serviceFacade.navigateToHelp();
    }

    /**
     * Handles level completion when player wins.
     */
    private void handleLevelWon() {
        gameState.setGameResult(GameResult.WON);
        serviceFacade.stopExecution();
        int playerSteps = gameState.getPlayerSteps();
        levelDTO = serviceFacade.handleLevelCompletion(this.levelDTO, playerSteps,
                gameState.getLevelData());
        serviceFacade.completeLevelAndSave(levelDTO);
        loadLevel(levelDTO);
        serviceFacade.navigateToWinScreen();
    }

    /**
     * Handles game loss condition.
     */
    private void handleLoss() {
        gameState.setGameResult(GameResult.LOST);
        serviceFacade.stopExecution();
        resetLevel();
        serviceFacade.navigateToLossScreen();
    }
}