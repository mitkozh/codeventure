package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.GameState;

import java.util.function.Consumer;

/**
 * Service for managing the lifecycle of user code execution.
 * This includes compiling, executing, stopping, and checking the status of user code.
 */
public interface UserCodeLifecycleService {
    /**
     * Compiles and executes user code.
     * @param userCode The code to execute
     * @param gameState Current game state
     * @param onMessage Callback for execution messages
     * @param onError Callback for execution errors
     * @param onComplete Callback when execution completes
     */
    void executeCode(String userCode, GameState gameState,
                     Consumer<String> onMessage,
                     Consumer<String> onError,
                     Runnable onComplete);

    /**
     * Stops any currently running execution.
     */
    void stopExecution();

    /**
     * Checks if code is currently executing.
     */
    boolean isExecuting();

    /**
     * Checks if the execution environment is ready.
     */
    boolean isReady();

    /**
     * Gets any initialization errors.
     */
    String getInitializationError();
}
