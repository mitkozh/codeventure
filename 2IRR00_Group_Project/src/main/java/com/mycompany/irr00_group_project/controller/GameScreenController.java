package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.controller.components.CodeEditorAreaController;
import com.mycompany.irr00_group_project.controller.components.ConsoleOutputController;
import com.mycompany.irr00_group_project.controller.components.GameGridController;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.model.enums.GameResult;
import com.mycompany.irr00_group_project.service.core.CommandService;
import com.mycompany.irr00_group_project.service.core.UserCodeLifecycleService;
import com.mycompany.irr00_group_project.service.core.impl.CommandServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.GameServiceManager;
import com.mycompany.irr00_group_project.service.core.impl.UserCodeLifecycleServiceImpl;
import com.mycompany.irr00_group_project.service.observable.ConsoleObservables;
import com.mycompany.irr00_group_project.service.observable.GameStateObservables;
import com.mycompany.irr00_group_project.service.observable.ObservableProvider;
import com.mycompany.irr00_group_project.utils.Constants;
import com.mycompany.irr00_group_project.utils.GameScreenNavigatorManager;
import com.mycompany.irr00_group_project.utils.StringUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


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

    private GameState gameState;
    private String levelFile;
    private GameServiceManager gameServiceManager;
    private CommandService commandService;
    private GameScreenNavigatorManager navigatorManager;
    private UserCodeLifecycleService userCodeLifecycleService;
    private int levelNumber;

    /**
     * Initialization of game screen.
     */
    @FXML
    public void initialize() {
        gameState = new GameState(levelFile);
        this.gameServiceManager =
                new GameServiceManager();
        this.navigatorManager =
                new GameScreenNavigatorManager(rootPane);
        this.commandService =
                new CommandServiceImpl(gameServiceManager.getMovementService());
        this.userCodeLifecycleService =
                new UserCodeLifecycleServiceImpl(gameServiceManager, commandService);
        setupObservableBindings();
        setupUserCodeLifecycleService();
        loadLevel(levelFile);
        stopExecutionButton.setDisable(true);
    }

    private void setupUserCodeLifecycleService() {
        if (!userCodeLifecycleService.isReady()) {
            consoleOutputController.logError(
                    userCodeLifecycleService.getInitializationError());
            runCodeButton.setDisable(true);
        }
    }

    private void setupObservableBindings() {
        if (commandService instanceof ObservableProvider provider) {
            provider.getObservable(ConsoleObservables.class).ifPresent(console -> {
                console.lastMessageProperty().addListener((obs, oldMsg, newMsg) -> {
                    if (!StringUtils.isNullOrEmpty(newMsg)) {
                        consoleOutputController.appendMessage(newMsg);
                    }
                });
                console.lastErrorProperty().addListener((obs, oldErr, newErr) -> {
                    if (!StringUtils.isNullOrEmpty(newErr)) {
                        consoleOutputController.logError(newErr);
                    }
                });
            });

            provider.getObservable(GameStateObservables.class).ifPresent(gameState -> {
                gameState.gridNeedsUpdateProperty().addListener((obs, wasNeeded, isNeeded) -> {
                    if (isNeeded) {
                        gameGridController.renderGridAndSprite();
                        gameState.clearGridUpdateFlag();
                    }
                });

                gameState.levelWonProperty().addListener((obs, wasWon, isWon) -> {
                    if (isWon) {
                        handleLevelWon();
                        gameState.resetGameFlags();
                    }
                });

                gameState.levelLostProperty().addListener((obs, wasLost, isLost) -> {
                    if (isLost) {
                        handleLoss();
                        gameState.resetGameFlags();
                    }
                });
            });
        }
    }

    private void loadLevel(String levelFile) {
        gameState = new GameState(levelFile);
        gameState.loadFromFile(levelFile);
        gameGridController.loadLevelFromGameState(gameState);
        levelTitle.setText("Level: "
                + levelFile.replace(".txt", ""));
    }

    /**
     * fxml method to run the code of the user.
     */
    @FXML
    public void runCode(ActionEvent event) {
        if (!gameState.isGamePlaying()) {
            return;
        }

        String code = Constants.INITIAL_IMPORTS_CODE + codeEditorController.getCode();
        consoleOutputController.clear();
        setExecutionState(true);
        userCodeLifecycleService.executeCode(code, gameState,
                consoleOutputController::appendMessage,
                consoleOutputController::logError,
                () -> setExecutionState(false)
        );
    }

    private void setExecutionState(boolean executing) {
        Platform.runLater(() -> {
            runCodeButton.setDisable(executing || !userCodeLifecycleService.isReady());
            stopExecutionButton.setDisable(!executing);
            resetLevelButton.setDisable(executing);
        });
    }

    /**
     * fxml method to stop executing the code of the user.
     */
    @FXML
    public void stopExecutionOnClick(ActionEvent event) {
        userCodeLifecycleService.stopExecution();
        setExecutionState(false);
    }


    /**
     * fxml method to reset the level.
     */
    @FXML
    public void resetLevelOnClick(ActionEvent event) {
        resetLevel();
    }

    private void resetLevel() {
        if (userCodeLifecycleService.isExecuting()) {
            consoleOutputController.logError("Stop execution first before resetting.");
            return;
        }
        setExecutionState(false);
        loadLevel(levelFile);
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
     *
     * @param actionEvent .
     */
    public void onSettingsClick(ActionEvent actionEvent) {
        navigatorManager.navigateToSettings();
    }

    private void handleLevelWon() {
        gameState.setGameResult(GameResult.WON);
        int playerSteps = gameState.getPlayerSteps();

        LevelDTO levelDTO = gameServiceManager.getGamePlayService()
                .handleLevelCompletion(levelNumber,
                playerSteps, gameState.getLevelData());
        gameServiceManager.getLevelService().completeLevelAndSave(levelDTO);
        int stars = gameServiceManager.getGamePlayService().calculateStars(gameState.getLevelData(),
                playerSteps);
        finishExecution(String.format("Level completed!"
                        + " Steps: %d, Stars: %d/3",
                playerSteps, stars));
        userCodeLifecycleService.stopExecution();

    }

    private void handleLoss() {
        gameState.setGameResult(GameResult.LOST);
        finishExecution("You lost! Try again.");
        userCodeLifecycleService.stopExecution();
        resetLevel();
    }

    public void setLevelFile(String levelFile) {
        this.levelFile = levelFile;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
}