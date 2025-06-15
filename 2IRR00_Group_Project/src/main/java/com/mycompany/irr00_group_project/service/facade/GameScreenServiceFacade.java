package com.mycompany.irr00_group_project.service.facade;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.CommandService;
import com.mycompany.irr00_group_project.service.core.UserCodeLifecycleService;
import com.mycompany.irr00_group_project.service.core.impl.CommandServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.UserCodeLifecycleServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.GameScreenNavigatorManager;
import com.mycompany.irr00_group_project.service.navigator.NavigationService;
import com.mycompany.irr00_group_project.service.observable.ConsoleObservables;
import com.mycompany.irr00_group_project.service.observable.ExecutionObservables;
import com.mycompany.irr00_group_project.service.observable.NavigationObservables;
import com.mycompany.irr00_group_project.service.observable.GameStateObservables;
import com.mycompany.irr00_group_project.service.observable.LevelSelectionObservables;
import com.mycompany.irr00_group_project.service.observable.ObservableProvider;

/**
 * Facade for game screen services.
 */
public class GameScreenServiceFacade {
    private final GameServiceManager gameServiceManager;
    private final GameScreenNavigatorManager navigatorManager;
    private final CommandService commandService;
    private final UserCodeLifecycleService userCodeLifecycleService;

    /**
     * Constructor for GameScreenServiceFacade.
     */
    public GameScreenServiceFacade() {
        this.gameServiceManager = new GameServiceManager();
        this.navigatorManager = new GameScreenNavigatorManager();
        this.commandService = new CommandServiceImpl(gameServiceManager.getMovementService());
        this.userCodeLifecycleService = new UserCodeLifecycleServiceImpl(commandService,
                gameServiceManager.getSharedJarService(),
                gameServiceManager.getCompilationService(),
                gameServiceManager.getExecutionService(),
                gameServiceManager.getIpcService());
    }

    /**
     * Gets the current level from the game service manager.
     * 
     * @return The current LevelDTO if available, otherwise null.
     */
    public LevelDTO getCurrentLevel() {
        if (gameServiceManager.getLevelService() instanceof LevelServiceImpl serviceImpl) {
            LevelSelectionObservables levelObs = serviceImpl
                    .getObservableOrThrow(LevelSelectionObservables.class);
            return levelObs.getSelectedLevel();
        }
        return null;
    }

    public LevelData getLevelData(LevelDTO levelDTO) {
        return gameServiceManager.getLevelService().getLevelDataByLevelDTO(levelDTO);
    }

    public void executeCode(String code, GameState gameState) {
        userCodeLifecycleService.executeCode(code, gameState);
    }

    public void stopExecution() {
        userCodeLifecycleService.stopExecution();
    }

    public boolean isExecuting() {
        return userCodeLifecycleService.isExecuting();
    }

    public boolean isReady() {
        return userCodeLifecycleService.isReady();
    }

    public void requestPause() {
        commandService.requestPause();
    }

    public void requestResume() {
        commandService.requestResume();
    }

    public void clearCommandQueue() {
        commandService.clearCommandQueue();
    }

    public void navigateToSettings() {
        navigatorManager.navigateToSettings();
    }

    public void navigateToHelp() {
        navigatorManager.navigateToHelp();
    }

    public void navigateToWinScreen() {
        navigatorManager.navigateToWinScreen();
    }

    public void navigateToLossScreen() {
        navigatorManager.navigateToLossScreen();
    }

    /**
     * Handles the completion of a level.
     *
     * @param levelDTO    The LevelDTO representing the current level.
     * @param playerSteps The number of steps taken by the player.
     * @param levelData   The LevelData associated with the level.
     * @return The updated LevelDTO after handling completion.
     */
    public LevelDTO handleLevelCompletion(LevelDTO levelDTO, int playerSteps, LevelData levelData) {
        return gameServiceManager.getGamePlayService()
                .handleLevelCompletion(levelDTO, playerSteps, levelData);
    }

    public void completeLevelAndSave(LevelDTO levelDTO) {
        gameServiceManager.getLevelService().completeLevelAndSave(levelDTO);
    }

    /**
     * Sets up observable bindings for various game events.
     *
     * @param onGridUpdate        Callback for when the grid needs to be updated.
     * @param onLevelWon          Callback for when the level is won.
     * @param onLoss              Callback for when the player loses.
     * @param onConsoleMessage    Callback for console messages.
     * @param onConsoleError      Callback for console errors.
     * @param onExecutionStart    Callback for when execution starts.
     * @param onExecutionComplete Callback for when execution completes.
     * @param onReturnToGame      Callback for when returning to the game.
     */
    public void setupObservableBindings(
            Runnable onGridUpdate,
            Runnable onLevelWon,
            Runnable onLoss,
            java.util.function.Consumer<String> onConsoleMessage,
            java.util.function.Consumer<String> onConsoleError,
            Runnable onExecutionStart,
            java.util.function.Consumer<String> onExecutionComplete,
            Runnable onReturnToGame) {

        setupCommandServiceObservables(onGridUpdate, onLevelWon, onLoss);
        setupUserCodeLifecycleServiceObservables(onConsoleMessage, onConsoleError,
                onExecutionStart, onExecutionComplete);
        setupNavigationObservables(onReturnToGame);
    }

    private void setupCommandServiceObservables(Runnable onGridUpdate, Runnable onLevelWon,
            Runnable onLoss) {
        if (commandService instanceof ObservableProvider provider) {
            provider.getObservable(GameStateObservables.class).ifPresent(gameState -> {
                gameState.gridNeedsUpdateProperty().addListener((obs, wasNeeded, isNeeded) -> {
                    if (isNeeded) {
                        onGridUpdate.run();
                        gameState.clearGridUpdateFlag();
                    }
                });

                gameState.levelWonProperty().addListener((obs, wasWon, isWon) -> {
                    if (isWon) {
                        onLevelWon.run();
                        gameState.resetGameFlags();
                    }
                });

                gameState.levelLostProperty().addListener((obs, wasLost, isLost) -> {
                    if (isLost) {
                        onLoss.run();
                        gameState.resetGameFlags();
                    }
                });
            });
        }
    }

    private void setupUserCodeLifecycleServiceObservables(
            java.util.function.Consumer<String> onConsoleMessage,
            java.util.function.Consumer<String> onConsoleError,
            Runnable onExecutionStart,
            java.util.function.Consumer<String> onExecutionComplete) {

        if (userCodeLifecycleService instanceof ObservableProvider provider) {
            provider.getObservable(ConsoleObservables.class).ifPresent(console -> {
                console.lastMessageProperty().addListener((obs, oldMsg, newMsg) -> {
                    onConsoleMessage.accept(newMsg);
                });
                console.lastErrorProperty().addListener((obs, oldErr, newErr) -> {
                    onConsoleError.accept(newErr);
                });
            });

            provider.getObservable(ExecutionObservables.class).ifPresent(execution -> {
                execution.executionStartedProperty().addListener((obs, wasStarted, isStarted) -> {
                    if (isStarted) {
                        onExecutionStart.run();
                    }
                });

                execution.executionCompletedProperty()
                        .addListener((obs, wasCompleted, isCompleted) -> {
                            if (isCompleted) {
                                String message = execution.completionMessageProperty().get();
                                onExecutionComplete.accept(message);
                                execution.resetFlags();
                            }
                        });
            });
        }
    }

    private void setupNavigationObservables(Runnable onReturnToGame) {
        NavigationService navService = NavigationService.getInstance();
        navService.getObservable(NavigationObservables.class).ifPresent(nav -> {
            nav.returnedToGameProperty().addListener((obs, wasReturned, isReturned) -> {
                if (isReturned) {
                    onReturnToGame.run();
                    nav.clearReturnedToGame();
                }
            });
        });
    }
}