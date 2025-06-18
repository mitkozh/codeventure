package com.mycompany.irr00_group_project.service.facade;

import com.mycompany.irr00_group_project.service.core.impl.GamePlayServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.core.impl.MovementServiceImpl;
import com.mycompany.irr00_group_project.service.ipc.IPCService;
import com.mycompany.irr00_group_project.service.resources.impl.SharedJarServiceImpl;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeCompilationService;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeExecutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceManagerTest {

    private GameServiceManager gameServiceManager;

    @BeforeEach
    void setUp() {
        gameServiceManager = new GameServiceManager();
    }

    @Test
    void testConstructorInitializesServices() {
        assertNotNull(gameServiceManager.getMovementService(),
            "MovementService should be initialized");
        assertNotNull(gameServiceManager.getGamePlayService(),
            "GamePlayService should be initialized");
        assertNotNull(gameServiceManager.getLevelService(),
            "LevelService should be initialized");
        assertNotNull(gameServiceManager.getSharedJarService(),
            "SharedJarService should be initialized");
        assertNotNull(gameServiceManager.getCompilationService(),
            "CompilationService should be initialized");
        assertNotNull(gameServiceManager.getExecutionService(),
            "ExecutionService should be initialized");
        assertNotNull(gameServiceManager.getIpcService(), 
            "IPCService should be initialized");
    }

    @Test
    void testGetMovementServiceReturnsCorrectType() {
        assertTrue(gameServiceManager.getMovementService() instanceof MovementServiceImpl,
                "Should return MovementServiceImpl instance");
    }

    @Test
    void testGetGamePlayServiceReturnsCorrectType() {
        assertTrue(gameServiceManager.getGamePlayService() instanceof GamePlayServiceImpl,
                "Should return GamePlayServiceImpl instance");
    }

    @Test
    void testGetLevelServiceReturnsCorrectType() {
        assertTrue(gameServiceManager.getLevelService() instanceof LevelServiceImpl,
                "Should return LevelServiceImpl instance");
    }

    @Test
    void testGetSharedJarServiceReturnsCorrectType() {
        assertTrue(gameServiceManager.getSharedJarService() instanceof SharedJarServiceImpl,
                "Should return SharedJarServiceImpl instance");
    }

    @Test
    void testGetCompilationServiceReturnsCorrectType() {
        assertTrue(gameServiceManager.getCompilationService() instanceof UserCodeCompilationService,
                "Should return UserCodeCompilationService instance");
    }

    @Test
    void testGetExecutionServiceReturnsCorrectType() {
        assertTrue(gameServiceManager.getExecutionService() instanceof UserCodeExecutionService,
                "Should return UserCodeExecutionService instance");
    }

    @Test
    void testGetIpcServiceReturnsCorrectType() {
        assertTrue(gameServiceManager.getIpcService() instanceof IPCService,
                "Should return IPCService instance");
    }
}