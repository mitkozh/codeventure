package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.GameState;

/**
 * Service for managing the lifecycle of user code execution.
 * This includes compiling, executing, stopping, and checking the status of user code.
 */
public interface UserCodeLifecycleService {
    /**
     * Compiles and executes user code.
     * @param userCode The code to execute
     * @param gameState Current game state
     */
    void executeCode(String userCode, GameState gameState);

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
