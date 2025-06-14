package com.mycompany.irr00_group_project.controller;

import com.mycompany.irr00_group_project.controller.components.CodeEditorAreaController;
import com.mycompany.irr00_group_project.controller.components.ConsoleOutputController;
import com.mycompany.irr00_group_project.controller.components.GameGridController;
import com.mycompany.irr00_group_project.gui.screen.GameScreen;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.model.enums.GameResult;
import com.mycompany.irr00_group_project.service.core.CommandService;
import com.mycompany.irr00_group_project.service.core.UserCodeLifecycleService;
import com.mycompany.irr00_group_project.service.core.impl.CommandServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.UserCodeLifecycleServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.GameScreenNavigatorManager;
import com.mycompany.irr00_group_project.service.observable.ConsoleObservables;
import com.mycompany.irr00_group_project.service.observable.NavigationObservables;
import com.mycompany.irr00_group_project.service.navigator.NavigationService;
import com.mycompany.irr00_group_project.service.observable.ExecutionObservables;
import com.mycompany.irr00_group_project.service.observable.LevelSelectionObservables;
import com.mycompany.irr00_group_project.service.observable.ObservableProvider;
import com.mycompany.irr00_group_project.service.observable.GameStateObservables;
import com.mycompany.irr00_group_project.utils.Constants;
import com.mycompany.irr00_group_project.utils.GameServiceManager;
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
    private GameServiceManager gameServiceManager;
    private CommandService commandService;
    private GameScreenNavigatorManager navigatorManager;
    private UserCodeLifecycleService userCodeLifecycleService;
    private LevelDTO levelDTO;

    public GameScreenController(GameScreen view) {
        this.view = view;
    }

    /**
     * Initialization of game screen.
     */
    public void initialize() {
        setUpViewControllerBindings();
        this.gameServiceManager = new GameServiceManager();
        this.navigatorManager = new GameScreenNavigatorManager(rootPane);
        this.commandService = new CommandServiceImpl(gameServiceManager.getMovementService());
        this.userCodeLifecycleService = new UserCodeLifecycleServiceImpl(commandService,
                gameServiceManager.getSharedJarService(),
                gameServiceManager.getCompilationService(),
                gameServiceManager.getExecutionService(),
                gameServiceManager.getIpcService());
        setupObservableBindings();
        stopExecutionButton.setDisable(true);
        getCurrentLevel();
    }

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

    private void getCurrentLevel() {
        if (gameServiceManager.getLevelService() instanceof LevelServiceImpl serviceImpl) {
            LevelSelectionObservables levelObs = serviceImpl
                    .getObservableOrThrow(LevelSelectionObservables.class);
            LevelDTO currentLevel = levelObs.getSelectedLevel();
            if (currentLevel != null) {
                levelDTO = currentLevel;
                loadLevel(currentLevel);
            }
        }
    }

    private void setupObservableBindings() {
        setupCommandServiceObservables();
        setupUserCodeLifecycleServiceObservables();
        setupNavigationObservables();
    }

    private void setupNavigationObservables() {
        NavigationService navService = NavigationService.getInstance();
        navService.getObservable(NavigationObservables.class).ifPresent(nav -> {
            nav.returnedToGameProperty().addListener((obs, wasReturned, isReturned) -> {
                if (isReturned) {
                    commandService.requestResume();
                    nav.clearReturnedToGame();
                }
            });
        });
    }

    private void setupUserCodeLifecycleServiceObservables() {
        if (userCodeLifecycleService instanceof ObservableProvider provider) {
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
            provider.getObservable(ExecutionObservables.class).ifPresent(execution -> {
                execution.executionStartedProperty().addListener((obs, wasStarted, isStarted) -> {
                    if (isStarted) {
                        setExecutionState(true);
                    }
                });

                execution.executionCompletedProperty()
                        .addListener((obs, wasCompleted, isCompleted) -> {
                            if (isCompleted) {
                                String message = execution.completionMessageProperty().get();
                                finishExecution(message);
                                execution.resetFlags();
                            }
                        });
            });
        }
    }

    private void setupCommandServiceObservables() {
        if (commandService instanceof ObservableProvider provider) {
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

    private void loadLevel(LevelDTO level) {
        LevelData levelDataByLevelDTO = gameServiceManager.getLevelService()
                .getLevelDataByLevelDTO(level);
        gameState = new GameState(levelDataByLevelDTO);
        gameGridController.loadLevelFromGameState(gameState);
        levelTitle.setText("Level: "
                + levelDTO.getLevelNumber());
    }

    /**
     * fxml method to run the code of the user.
     */
    public void runCode(ActionEvent event) {
        if (!gameState.isGamePlaying()) {
            return;
        }

        String code = Constants.INITIAL_IMPORTS_CODE + codeEditorController.getCode();
        consoleOutputController.clear();
        setExecutionState(true);
        userCodeLifecycleService.executeCode(code, gameState);
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
    public void stopExecutionOnClick(ActionEvent event) {
        userCodeLifecycleService.stopExecution();
        setExecutionState(false);
    }

    /**
     * method to reset the level.
     */
    public void resetLevelOnClick(ActionEvent event) {
        resetLevel();
    }

    private void resetLevel() {
        if (userCodeLifecycleService.isExecuting()) {
            consoleOutputController.logError("Stop execution first before resetting.");
            return;
        }
        userCodeLifecycleService.stopExecution();
        commandService.clearCommandQueue();
        setExecutionState(false);
        loadLevel(levelDTO);
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
     * method to open the in-game settings.
     *
     * @param actionEvent .
     */
    public void onSettingsClick(ActionEvent actionEvent) {
        commandService.requestPause();
        navigatorManager.navigateToSettings();
    }

    /**
     * method to open the help screen.
     *
     * @param actionEvent .
     */
    public void onHelpClick(ActionEvent actionEvent) {
        commandService.requestPause();
        navigatorManager.navigateToHelp();
    }

    private void handleLevelWon() {
        gameState.setGameResult(GameResult.WON);
        userCodeLifecycleService.stopExecution();
        int playerSteps = gameState.getPlayerSteps();
        levelDTO = gameServiceManager.getGamePlayService()
                .handleLevelCompletion(this.levelDTO,
                        playerSteps, gameState.getLevelData());
        gameServiceManager.getLevelService().completeLevelAndSave(levelDTO);
        loadLevel(levelDTO);
        navigatorManager.navigateToWinScreen();
    }

    private void handleLoss() {
        gameState.setGameResult(GameResult.LOST);
        userCodeLifecycleService.stopExecution();
        loadLevel(levelDTO);
        navigatorManager.navigateToLossScreen();
    }
}
