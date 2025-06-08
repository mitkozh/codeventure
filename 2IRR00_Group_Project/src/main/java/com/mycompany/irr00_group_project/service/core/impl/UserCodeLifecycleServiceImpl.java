package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.model.core.CompilationResult;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.service.core.CommandService;
import com.mycompany.irr00_group_project.service.core.UserCodeLifecycleService;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Implementation of UserCodeLifecycleService that manages
 * the lifecycle of user code execution and compilation.
 */
public class UserCodeLifecycleServiceImpl implements UserCodeLifecycleService {
    private final GameServiceManager gameServiceManager;
    private final CommandService commandService;
    private File resolvedSharedJarPath;
    private boolean isExecuting = false;
    private String initializationError;

    /**
     * Constructor for UserCodeLifecycleServiceImpl.
     *
     * @param gameServiceManager The GameServiceManager instance to manage game services.
     * @param commandService The CommandService instance to handle IPC messages and commands.
     */
    public UserCodeLifecycleServiceImpl(GameServiceManager gameServiceManager,
                                        CommandService commandService) {
        this.gameServiceManager = gameServiceManager;
        this.commandService = commandService;
        initializeEnvironment();
    }

    private void initializeEnvironment() {
        try {
            resolvedSharedJarPath = gameServiceManager.getSharedJarService()
                    .getResolvedSharedJarFile();
            if (resolvedSharedJarPath == null || !resolvedSharedJarPath.exists()) {
                initializationError = "shared.jar not found or accessible!";
            }
        } catch (IOException e) {
            initializationError = "Error resolving shared.jar: " + e.getMessage();
        }
    }

    @Override
    public void executeCode(String userCode, GameState gameState,
                            Consumer<String> onMessage,
                            Consumer<String> onError,
                            Runnable onComplete) {
        if (isExecuting || !isReady()) {
            return;
        }

        if (userCode.trim().isEmpty()) {
            onError.accept("No code to execute");
            return;
        }

        isExecuting = true;
        onMessage.accept("Compiling user code...");

        CompilationResult result = gameServiceManager.getCompilationService()
                .compile(userCode, resolvedSharedJarPath.getAbsolutePath());

        if (!isCompilationSuccessful(result, onMessage, onError, onComplete)) {
            return;
        }

        try {
            executeUserCode(result, gameState, onMessage, onError, onComplete);
        } catch (IOException e) {
            handleExecutionError(e, onError, onComplete);
        }
    }

    private boolean isCompilationSuccessful(CompilationResult result,
                                            Consumer<String> onMessage,
                                            Consumer<String> onError,
                                            Runnable onComplete) {
        if (!result.isSuccess() || result.getCompiledClasses() == null) {
            onError.accept(result.getFormattedDiagnostics());
            finishExecution("Compilation failed.", onComplete);
            return false;
        }

        if (!result.getFormattedDiagnostics().isEmpty()
                && !result.getFormattedDiagnostics().startsWith("Compilation successful")) {
            onMessage.accept(result.getFormattedDiagnostics());
        } else {
            onMessage.accept("Compilation successful.");
        }
        return true;
    }

    private void executeUserCode(CompilationResult result, GameState gameState,
                                 Consumer<String> onMessage,
                                 Consumer<String> onError,
                                 Runnable onComplete) throws IOException {
        Process userProcess = gameServiceManager.getExecutionService()
                .startUserCodeProcess(result.getCompiledClasses(),
                        resolvedSharedJarPath.getAbsolutePath());

        gameServiceManager.getIpcService().startIPCListeners(userProcess,
                (message) -> commandService.handleIPCMessage(message, gameState),
                (error) -> commandService.handleIPCError(error, gameState));

        userProcess.onExit().thenAccept(process -> {
            Platform.runLater(() -> {
                if (process.exitValue() == 0) {
                    finishExecution("User code execution finished successfully.", onComplete);
                } else {
                    finishExecution("User code execution finished with errors (Exit code: "
                            + process.exitValue() + ").", onComplete);
                }
                gameServiceManager.getExecutionService().cleanupTemporaryFiles();
            });
        });
    }

    private void handleExecutionError(IOException e, Consumer<String> onError,
                                      Runnable onComplete) {
        onError.accept("Error running user code: " + e.getMessage());
        finishExecution("Execution failed due to I/O error.", onComplete);
        gameServiceManager.getExecutionService().cleanupTemporaryFiles();
    }

    private void finishExecution(String message, Runnable onComplete) {
        isExecuting = false;
        if (onComplete != null) {
            onComplete.run();
        }
    }

    @Override
    public void stopExecution() {
        if (!isExecuting) {
            return;
        }
        gameServiceManager.getIpcService().stopListeners();
        gameServiceManager.getExecutionService().stopCurrentProcess();
        if (isExecuting) {
            gameServiceManager.getExecutionService().cleanupTemporaryFiles();
            isExecuting = false;
        }
    }

    @Override
    public boolean isExecuting() {
        return isExecuting;
    }

    @Override
    public boolean isReady() {
        return initializationError == null;
    }

    @Override
    public String getInitializationError() {
        return initializationError;
    }
}
