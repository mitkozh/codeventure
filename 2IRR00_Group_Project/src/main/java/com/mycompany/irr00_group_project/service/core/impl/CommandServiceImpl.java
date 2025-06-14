package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.MovementResult;
import com.mycompany.irr00_group_project.service.core.CommandService;
import com.mycompany.irr00_group_project.service.core.MovementService;
import com.mycompany.irr00_group_project.service.observable.ConsoleObservables;
import com.mycompany.irr00_group_project.service.observable.GameStateObservables;
import com.mycompany.irr00_group_project.service.observable.ObservableProvider;
import com.mycompany.irr00_group_project.service.observable.ObservableRegistry;
import com.mycompany.irr00_group_project.service.observable.CommandExecutionObservables;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of the CommandService interface for handling IPC messages and
 * commands.
 * It uses the observable pattern to notify changes in the game state and
 * console output.
 */
public class CommandServiceImpl implements CommandService, ObservableProvider {
    private final Queue<Runnable> commandQueue = new LinkedList<>();
    private final MovementService movementService;
    private final ObservableRegistry observableRegistry = new ObservableRegistry();
    private boolean isProcessingQueue = false;
    private PauseTransition currentPause;

    /**
     * Constructor for CommandServiceImpl.
     *
     * @param movementService The MovementService instance to handle movement
     *                        commands.
     */
    public CommandServiceImpl(MovementService movementService) {
        this.movementService = movementService;
        initializeObservables();
        setupCommandExecutionListeners();
    }

    private void setupCommandExecutionListeners() {
        CommandExecutionObservables cmdExecObservables =
                getObservableOrThrow(CommandExecutionObservables.class);

        cmdExecObservables.pauseRequestedProperty()
                .addListener((obs, wasPauseRequested, isPauseRequested) -> {
                    if (isPauseRequested) {
                        handlePauseRequest();
                        cmdExecObservables.clearPauseRequest();
                    }
                });

        cmdExecObservables.resumeRequestedProperty()
                .addListener((obs, wasResumeRequested, isResumeRequested) -> {
                    if (isResumeRequested) {
                        handleResumeRequest();
                        cmdExecObservables.clearResumeRequest();
                    }
                });
    }

    private void handlePauseRequest() {
        CommandExecutionObservables commandExecutionObservables =
                getObservableOrThrow(CommandExecutionObservables.class);
        commandExecutionObservables.setExecutionPaused(true);

        if (currentPause != null
                && currentPause.getStatus() == javafx.animation.Animation.Status.RUNNING) {
            currentPause.pause();
        }
    }

    private void handleResumeRequest() {
        CommandExecutionObservables commandExecutionObservables =
                getObservableOrThrow(CommandExecutionObservables.class);
        commandExecutionObservables.setExecutionPaused(false);

        if (currentPause != null
                && currentPause.getStatus() == javafx.animation.Animation.Status.PAUSED) {
            currentPause.play();
        } else if (!commandQueue.isEmpty() && !isProcessingQueue) {
            processCommandQueue();
        }
    }

    private void initializeObservables() {
        observableRegistry.register(ConsoleObservables.class, new ConsoleObservables());
        observableRegistry.register(GameStateObservables.class, new GameStateObservables());
        observableRegistry.register(CommandExecutionObservables.class,
                new CommandExecutionObservables());
    }

    @Override
    public void requestPause() {
        CommandExecutionObservables commandExecutionObservables =
                getObservableOrThrow(CommandExecutionObservables.class);
        commandExecutionObservables.requestPause();
    }

    @Override
    public void requestResume() {
        CommandExecutionObservables commandExecutionObservables =
                getObservableOrThrow(CommandExecutionObservables.class);
        commandExecutionObservables.requestResume();
    }

    @Override
    public boolean isPaused() {
        CommandExecutionObservables commandExecutionObservables =
                getObservableOrThrow(CommandExecutionObservables.class);
        return commandExecutionObservables.isExecutionPaused();
    }

    @Override
    public ObservableRegistry getObservableRegistry() {
        return observableRegistry;
    }

    @Override
    public void handleIPCMessage(String message, GameState gameState) {
        Platform.runLater(() -> {
            ConsoleObservables console = getObservableOrThrow(ConsoleObservables.class);
            if (message.startsWith("CMD:")) {
                handleIPCCommand(message, gameState);
            } else {
                console.addMessage("UserOutput: " + message);
            }
        });
    }

    @Override
    public void handleIPCError(String error, GameState gameState) {
        Platform.runLater(() -> {
            ConsoleObservables console = getObservableOrThrow(ConsoleObservables.class);
            console.addError(error);
        });
    }

    private void handleIPCCommand(String command, GameState gameState) {
        ConsoleObservables console = getObservableOrThrow(ConsoleObservables.class);
        String[] parts = command.split(":", 3);
        if (parts.length < 2) {
            console.addError("Command error: " + command);
            return;
        }
        processIPCCommand(parts, gameState);
    }

    private void processIPCCommand(String[] parts, GameState gameState) {
        String cmdType = parts[1];
        String arg = (parts.length > 2) ? parts[2] : null;
        Runnable command = switch (cmdType) {
            case "MOVE_FORWARD" -> () -> handleMoveForward(gameState);
            case "TURN_LEFT" -> () -> handleTurnLeft(gameState);
            case "TURN_RIGHT" -> () -> handleTurnRight(gameState);
            case "LOG_MESSAGE" -> () -> handleLogMessage(arg);
            case "ERROR" -> () -> handleError(arg);
            case "EXECUTION_COMPLETE" -> () -> handleExecutionComplete(gameState);
            default -> () -> {
                ConsoleObservables console = getObservableOrThrow(ConsoleObservables.class);
                console.addError("Unknown IPC command: " + cmdType);
            };
        };
        commandQueue.add(command);
        processCommandQueue();
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

        currentPause = new PauseTransition(Duration.millis(300));
        currentPause.setOnFinished(event -> {
            isProcessingQueue = false;
            if (!commandQueue.isEmpty()) {
                processCommandQueue();
            }
        });
        currentPause.play();
    }

    private void handleMoveForward(GameState gameState) {
        if (!gameState.isGamePlaying()) {
            return;
        }
        MovementResult movementResult = movementService.tryMoveForward(gameState);
        GameStateObservables gameStateObs = getObservableOrThrow(GameStateObservables.class);

        if (movementResult.isSuccessful()) {
            gameState.incrementPlayerSteps();
            gameStateObs.setPlayerSteps(gameState.getPlayerSteps());
            gameStateObs.setGridForUpdate();
            if (movementResult.isLevelCompleted() && commandQueue.size() == 1) {
                gameStateObs.setLevelWon(true);
            }
        } else {
            gameStateObs.setLevelLost(true);
        }
    }

    private void handleTurnLeft(GameState gameState) {
        if (!gameState.isGamePlaying()) {
            return;
        }
        movementService.turnLeft(gameState);
        GameStateObservables gameStateObs = getObservableOrThrow(GameStateObservables.class);
        gameStateObs.setGridForUpdate();
    }

    private void handleExecutionComplete(GameState gameState) {
        if (gameState.isGamePlaying()) {
            GameStateObservables gameStateObs = getObservableOrThrow(GameStateObservables.class);
            gameStateObs.setLevelLost(true);
        }
    }

    private void handleTurnRight(GameState gameState) {
        if (!gameState.isGamePlaying()) {
            return;
        }
        movementService.turnRight(gameState);
        GameStateObservables gameStateObs = getObservableOrThrow(GameStateObservables.class);
        gameStateObs.setGridForUpdate();
    }

    private void handleLogMessage(String arg) {
        if (arg != null) {
            ConsoleObservables console = getObservableOrThrow(ConsoleObservables.class);
            console.addMessage("User code: " + arg);
        }
    }

    private void handleError(String arg) {
        if (arg != null) {
            ConsoleObservables console = getObservableOrThrow(ConsoleObservables.class);
            console.addError("User code: " + arg);
        }
    }

    @Override
    public void clearCommandQueue() {
        commandQueue.clear();
        isProcessingQueue = false;
    }
}