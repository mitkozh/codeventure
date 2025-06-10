package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.GameState;

/**
 * Interface for handling IPC (Inter-Process Communication) messages and errors.
 */
public interface CommandService {
    /**
     * Handles an IPC message.
     *
     * @param message The IPC message to handle.
     * @param state   The current game state.
     */
    void handleIPCMessage(String message, GameState state);

    /**
     * Handles an IPC error.
     *
     * @param error The IPC error to handle.
     * @param state The current game state.
     */
    void handleIPCError(String error, GameState state);

}
