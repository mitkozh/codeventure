package com.mycompany.irr00_group_project.service.facade;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class GameScreenServiceFacadeTest {

    private GameScreenServiceFacade facade;
    
    /**
     *  Sets up the GameScreenServiceFacade instance before each test.
     */
    @BeforeEach
    void setUp() {
        resetSingleton();
        facade = GameScreenServiceFacade.getInstance();
    }

    private void resetSingleton() {
        try {
            Field instanceField = GameScreenServiceFacade.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset singleton", e);
        }
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        GameScreenServiceFacade instance1 = GameScreenServiceFacade.getInstance();
        GameScreenServiceFacade instance2 = GameScreenServiceFacade.getInstance();
        assertSame(instance1, instance2, "Should return the same singleton instance");
    }

    @Test
    void testGetCurrentLevelReturnsNullWhenNoLevelSelected() {
        LevelDTO result = facade.getCurrentLevel();
        assertNull(result, "Should return null when no level is selected");
    }

    @Test
    void testSetupObservableBindingsInitializesListeners() {
        Runnable onGridUpdate = () -> {};
        Runnable onLevelWon = () -> {};
        Runnable onLoss = () -> {};
        Consumer<String> onConsoleMessage = (msg) -> {};
        Consumer<String> onConsoleError = (msg) -> {};
        Runnable onExecutionStart = () -> {};
        Consumer<String> onExecutionComplete = (msg) -> {};
        Runnable onReturnToGame = () -> {};

        facade.setupObservableBindings(
                onGridUpdate, onLevelWon, onLoss,
                onConsoleMessage, onConsoleError,
                onExecutionStart, onExecutionComplete,
                onReturnToGame
        );

        try {
            Field listenersInitializedField = 
                GameScreenServiceFacade.class.getDeclaredField("listenersInitialized");
            listenersInitializedField.setAccessible(true);
            boolean listenersInitialized = (boolean) listenersInitializedField.get(facade);
            assertTrue(listenersInitialized, "Listeners should be initialized after setup");
        } catch (Exception e) {
            fail("Failed to access listenersInitialized field: " + e.getMessage());
        }
    }

    @Test
    void testStopExecutionDoesNotThrowException() {
        assertDoesNotThrow(() -> facade.stopExecution(),
                "Stopping execution should not throw an exception");
    }

    @Test
    void testIsExecutingReturnsBoolean() {
        Boolean result = facade.isExecuting();
        assertNotNull(result, "isExecuting should return a boolean value");
    }

    @Test
    void testRequestPauseDoesNotThrowException() {
        assertDoesNotThrow(() -> facade.requestPause(),
                "Requesting pause should not throw an exception");
    }

    @Test
    void testRequestResumeDoesNotThrowException() {
        assertDoesNotThrow(() -> facade.requestResume(),
                "Requesting resume should not throw an exception");
    }

    @Test
    void testClearCommandQueueDoesNotThrowException() {
        assertDoesNotThrow(() -> facade.clearCommandQueue(),
                "Clearing command queue should not throw an exception");
    }

    @Test
    void testIsReadyReturnsBoolean() {
        Boolean result = facade.isReady();
        assertNotNull(result, "isReady should return a boolean value");
    }
}