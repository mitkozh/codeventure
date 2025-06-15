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

import java.util.function.Consumer;

/**
 * Facade for game screen services.
 */
public class GameScreenServiceFacade {
    private static GameScreenServiceFacade instance;
    private final GameServiceManager gameServiceManager;
    private final GameScreenNavigatorManager navigatorManager;
    private final CommandService commandService;
    private final UserCodeLifecycleService userCodeLifecycleService;
    
    private Runnable currentOnGridUpdate;
    private Runnable currentOnLevelWon;
    private Runnable currentOnLoss;
    private Consumer<String> currentOnConsoleMessage;
    private Consumer<String> currentOnConsoleError;
    private Runnable currentOnExecutionStart;
    private Consumer<String> currentOnExecutionComplete;
    private Runnable currentOnReturnToGame;
    private boolean listenersInitialized;

    /**
     * Constructor for GameScreenServiceFacade.
     */
    private GameScreenServiceFacade() {
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
     * Gets the singleton instance of GameScreenServiceFacade.
     * 
     * @return The singleton instance of GameScreenServiceFacade.
     */
    public static synchronized GameScreenServiceFacade getInstance() {
        if (instance == null) {
            instance = new GameScreenServiceFacade();
        }
        return instance;
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
            Consumer<String> onConsoleMessage,
            Consumer<String> onConsoleError,
            Runnable onExecutionStart,
            Consumer<String> onExecutionComplete,
            Runnable onReturnToGame) {

        this.currentOnGridUpdate = onGridUpdate;
        this.currentOnLevelWon = onLevelWon;
        this.currentOnLoss = onLoss;
        this.currentOnConsoleMessage = onConsoleMessage;
        this.currentOnConsoleError = onConsoleError;
        this.currentOnExecutionStart = onExecutionStart;
        this.currentOnExecutionComplete = onExecutionComplete;
        this.currentOnReturnToGame = onReturnToGame;

        if (!listenersInitialized) {
            setupCommandServiceObservables();
            setupUserCodeLifecycleServiceObservables();
            setupNavigationObservables();
            listenersInitialized = true;
        }
    }

    private void setupCommandServiceObservables() {
        if (commandService instanceof ObservableProvider provider) {
            provider.getObservable(GameStateObservables.class).ifPresent(gameState -> {
                gameState.gridNeedsUpdateProperty().addListener((obs, wasNeeded, isNeeded) -> {
                    if (isNeeded) {
                        currentOnGridUpdate.run();
                        gameState.clearGridUpdateFlag();
                    }
                });

                gameState.levelWonProperty().addListener((obs, wasWon, isWon) -> {
                    if (isWon) {
                        currentOnLevelWon.run();
                        gameState.resetGameFlags();
                    }
                });

                gameState.levelLostProperty().addListener((obs, wasLost, isLost) -> {
                    if (isLost) {
                        currentOnLoss.run();
                        gameState.resetGameFlags();
                    }
                });
            });
        }
    }

    private void setupUserCodeLifecycleServiceObservables() {

        if (userCodeLifecycleService instanceof ObservableProvider provider) {
            provider.getObservable(ConsoleObservables.class).ifPresent(console -> {
                console.lastMessageProperty().addListener((obs, oldMsg, newMsg) -> {
                    currentOnConsoleMessage.accept(newMsg);
                });
                console.lastErrorProperty().addListener((obs, oldErr, newErr) -> {
                    currentOnConsoleError.accept(newErr);
                });
            });

            provider.getObservable(ExecutionObservables.class).ifPresent(execution -> {
                execution.executionStartedProperty().addListener((obs, wasStarted, isStarted) -> {
                    if (isStarted) {
                        currentOnExecutionStart.run();
                    }
                });

                execution.executionCompletedProperty()
                        .addListener((obs, wasCompleted, isCompleted) -> {
                            if (isCompleted) {
                                String message = execution.completionMessageProperty().get();
                                currentOnExecutionComplete.accept(message);
                                execution.resetFlags();
                            }
                        });
            });
        }
    }

    private void setupNavigationObservables() {
        NavigationService navService = NavigationService.getInstance();
        navService.getObservable(NavigationObservables.class).ifPresent(nav -> {
            nav.returnedToGameProperty().addListener((obs, wasReturned, isReturned) -> {
                if (isReturned) {
                    currentOnReturnToGame.run();
                    nav.clearReturnedToGame();
                }
            });
        });
    }
}