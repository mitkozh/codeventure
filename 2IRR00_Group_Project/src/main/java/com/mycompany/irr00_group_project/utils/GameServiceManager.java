package com.mycompany.irr00_group_project.utils;

import com.mycompany.irr00_group_project.service.core.GamePlayService;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.MovementService;
import com.mycompany.irr00_group_project.service.core.impl.GamePlayServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.MovementServiceImpl;
import com.mycompany.irr00_group_project.service.ipc.IPCService;
import com.mycompany.irr00_group_project.service.resources.impl.SharedJarServiceImpl;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeCompilationService;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeExecutionService;

/**
 * Manages the core game services, providing access to various functionalities.
 * This class is a central point for retrieving instances of the core services
 */
public class GameServiceManager {
    private final MovementService movementService;
    private final GamePlayService gamePlayService;
    private final LevelService levelService;
    private final SharedJarServiceImpl sharedJarService;
    private final UserCodeCompilationService compilationService;
    private final UserCodeExecutionService executionService;
    private final IPCService ipcService;

    /**
     * Constructor for GameServiceManager.
     * Initializes all core services used in the core game.
     */
    public GameServiceManager() {
        this.movementService = new MovementServiceImpl();
        this.gamePlayService = new GamePlayServiceImpl();
        this.levelService = LevelServiceImpl.getInstance();
        this.sharedJarService = new SharedJarServiceImpl();
        this.compilationService = new UserCodeCompilationService();
        this.executionService = new UserCodeExecutionService();
        this.ipcService = new IPCService();
    }

    public MovementService getMovementService() {
        return movementService;
    }

    public GamePlayService getGamePlayService() {
        return gamePlayService;
    }

    public LevelService getLevelService() {
        return levelService;
    }

    public SharedJarServiceImpl getSharedJarService() {
        return sharedJarService;
    }

    public UserCodeCompilationService getCompilationService() {
        return compilationService;
    }

    public UserCodeExecutionService getExecutionService() {
        return executionService;
    }

    public IPCService getIpcService() {
        return ipcService;
    }
}