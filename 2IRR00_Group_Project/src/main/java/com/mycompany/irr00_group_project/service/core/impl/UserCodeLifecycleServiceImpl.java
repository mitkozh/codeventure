package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.model.core.CompilationResult;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.service.core.CommandService;
import com.mycompany.irr00_group_project.service.core.UserCodeLifecycleService;
import com.mycompany.irr00_group_project.service.ipc.IPCService;
import com.mycompany.irr00_group_project.service.observable.ConsoleObservables;
import com.mycompany.irr00_group_project.service.observable.ExecutionObservables;
import com.mycompany.irr00_group_project.service.observable.ObservableProvider;
import com.mycompany.irr00_group_project.service.observable.ObservableRegistry;
import com.mycompany.irr00_group_project.service.resources.SharedJarService;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeCompilationService;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeExecutionService;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;

/**
 * Implementation of UserCodeLifecycleService that manages
 * the lifecycle of user code execution and compilation.
 */
public class UserCodeLifecycleServiceImpl implements UserCodeLifecycleService, ObservableProvider {
    private final CommandService commandService;
    private final SharedJarService sharedJarService;
    private final UserCodeCompilationService compilationService;
    private final UserCodeExecutionService executionService;
    private final IPCService ipcService;
    private final ObservableRegistry observableRegistry;
    private File resolvedSharedJarPath;
    private boolean isExecuting = false;
    private String initializationError;

    /**
     * Constructor for UserCodeLifecycleServiceImpl.
     *
     * @param commandService     The CommandService instance to handle IPC messages
     *                           and commands.
     * @param sharedJarService   The SharedJarService instance to manage shared
     *                           resources.
     * @param compilationService The UserCodeCompilationService instance to compile
     *                           user code.
     * @param executionService   The UserCodeExecutionService instance to execute
     *                           user code.
     * @param ipcService         The IPCService instance to handle inter-process
     *                           communication.
     */
    public UserCodeLifecycleServiceImpl(CommandService commandService,
            SharedJarService sharedJarService,
            UserCodeCompilationService compilationService,
            UserCodeExecutionService executionService,
            IPCService ipcService) {
        this.commandService = commandService;
        this.sharedJarService = sharedJarService;
        this.compilationService = compilationService;
        this.executionService = executionService;
        this.ipcService = ipcService;
        this.observableRegistry = new ObservableRegistry();
        initializeObservables();
        initializeEnvironment();
    }

    private void initializeObservables() {
        observableRegistry.register(ConsoleObservables.class, new ConsoleObservables());
        observableRegistry.register(ExecutionObservables.class, new ExecutionObservables());
    }

    @Override
    public ObservableRegistry getObservableRegistry() {
        return observableRegistry;
    }

    private void initializeEnvironment() {
        try {
            resolvedSharedJarPath = sharedJarService
                    .getResolvedSharedJarFile();
            if (resolvedSharedJarPath == null || !resolvedSharedJarPath.exists()) {
                initializationError = "shared.jar not found or accessible!";
            }
        } catch (IOException e) {
            initializationError = "Error resolving shared.jar: " + e.getMessage();
        }
    }

    @Override
    public void executeCode(String userCode, GameState gameState) {
        if (isExecuting || !isReady()) {
            return;
        }

        if (userCode.trim().isEmpty()) {
            getObservableOrThrow(ConsoleObservables.class).addError("No code to execute");
            return;
        }

        isExecuting = true;
        ExecutionObservables executionObs = getObservableOrThrow(ExecutionObservables.class);
        ConsoleObservables consoleObs = getObservableOrThrow(ConsoleObservables.class);

        executionObs.setExecutionStarted();
        consoleObs.addMessage("Compiling user code...");

        CompilationResult result = compilationService
                .compile(userCode, resolvedSharedJarPath.getAbsolutePath());

        if (!isCompilationSuccessful(result)) {
            return;
        }

        try {
            executeUserCode(result, gameState);
        } catch (IOException e) {
            handleExecutionError(e);
        }
    }

    private boolean isCompilationSuccessful(CompilationResult result) {
        ConsoleObservables consoleObs = getObservableOrThrow(ConsoleObservables.class);
        ExecutionObservables executionObs = getObservableOrThrow(ExecutionObservables.class);

        if (!result.isSuccess() || result.getCompiledClasses() == null) {
            consoleObs.addError(result.getFormattedDiagnostics());
            finishExecution("Compilation failed.");
            return false;
        }

        if (!result.getFormattedDiagnostics().isEmpty()
                && !result.getFormattedDiagnostics().startsWith("Compilation successful")) {
            consoleObs.addMessage(result.getFormattedDiagnostics());
        } else {
            consoleObs.addMessage("Compilation successful.");
        }
        return true;
    }

    private void executeUserCode(CompilationResult result, GameState gameState) throws IOException {
        Process userProcess = executionService
                .startUserCodeProcess(result.getCompiledClasses(),
                        resolvedSharedJarPath.getAbsolutePath());

        ipcService.startIPCListeners(userProcess,
                (message) -> commandService.handleIPCMessage(message, gameState),
                (error) -> commandService.handleIPCError(error, gameState));

        userProcess.onExit().thenAccept(process -> {
            Platform.runLater(() -> {
                if (process.exitValue() == 0) {
                    finishExecution("User code execution finished successfully.");
                } else {
                    commandService.clearCommandQueue();
                    finishExecution("User code execution finished with errors (Exit code: "
                            + process.exitValue() + ").");
                }
                executionService.cleanupTemporaryFiles();
            });
        });
    }

    private void handleExecutionError(IOException e) {
        ConsoleObservables consoleObs = getObservableOrThrow(ConsoleObservables.class);
        consoleObs.addError("Error running user code: " + e.getMessage());
        finishExecution("Execution failed due to I/O error.");
        executionService.cleanupTemporaryFiles();
    }

    private void finishExecution(String message) {
        isExecuting = false;
        ExecutionObservables executionObs = getObservableOrThrow(ExecutionObservables.class);
        executionObs.setExecutionCompleted(message);
    }

    @Override
    public void stopExecution() {
        if (!isExecuting) {
            return;
        }
        ipcService.stopListeners();
        executionService.stopCurrentProcess();
        if (isExecuting) {
            executionService.cleanupTemporaryFiles();
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