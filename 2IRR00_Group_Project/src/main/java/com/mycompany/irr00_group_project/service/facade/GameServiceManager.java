package com.mycompany.irr00_group_project.service.facade;

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
class GameServiceManager {
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

    /**
     * Provides access to the movement service.
     *
     * @return an instance of MovementService
     */
    public MovementService getMovementService() {
        return movementService;
    }
    
    /**
     * Provides access to the game play service.
     *
     * @return an instance of GamePlayService
     */

    public GamePlayService getGamePlayService() {
        return gamePlayService;
    }

    /**
     * Provides access to the level service.
     *
     * @return an instance of LevelService
     */
    public LevelService getLevelService() {
        return levelService;
    }

    /**
     * Provides access to the shared jar service.
     *
     * @return an instance of SharedJarServiceImpl
     */
    public SharedJarServiceImpl getSharedJarService() {
        return sharedJarService;
    }

    /**
     * Provides access to the user code compilation service.
     *
     * @return an instance of UserCodeCompilationService
     */
    public UserCodeCompilationService getCompilationService() {
        return compilationService;
    }

    /**
     * Provides access to the user code execution service.
     *
     * @return an instance of UserCodeExecutionService
     */
    public UserCodeExecutionService getExecutionService() {
        return executionService;
    }

    /**
     * Provides access to the IPC service.
     *
     * @return an instance of IPCService
     */
    public IPCService getIpcService() {
        return ipcService;
    }
}