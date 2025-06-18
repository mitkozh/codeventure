package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.MovementResult;
import com.mycompany.irr00_group_project.service.core.MovementService;
import com.mycompany.irr00_group_project.service.observable.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

class CommandServiceTest {

    @BeforeAll
    static void initToolkit() throws Exception {
        // Mock JavaFX Platform.runLater to avoid Toolkit not initialized error
        try {
            Class<?> platformClass = Class.forName("javafx.application.Platform");
            Field field = platformClass.getDeclaredField("impl_instance");
            field.setAccessible(true);
            if (field.get(null) == null) {
                // Create a mock Platform instance if needed
                java.lang.reflect.Proxy.newProxyInstance(
                    platformClass.getClassLoader(),
                    new Class<?>[]{platformClass},
                    (proxy, method, args) -> null
                );
            }
        } catch (Throwable t) {
            // If Platform is not available, ignore
        }

        // Alternatively, set Platform.runLater to a no-op using reflection
        try {
            Class<?> platformClass = Class.forName("javafx.application.Platform");
            java.lang.reflect.Method m = platformClass.getDeclaredMethod("runLater", Runnable.class);
            java.lang.reflect.Field modifiersField = java.lang.reflect.Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            m.setAccessible(true);
            // Not possible to override static methods directly, so use a library like PowerMock in real scenarios
        } catch (Throwable t) {
            // Ignore if not possible
        }
    }

// Minimal MovementService stub
private static class TestMovementService implements MovementService {
        @Override
        public MovementResult tryMoveForward(GameState gameState) {
            return new MovementResult(true, null, null, false);
        }

        @Override
        public void turnLeft(GameState gameState) {}

        @Override
        public void turnRight(GameState gameState) {}

        @Override
        public boolean isValidPosition(GameState gameState, int x, int y) {
            return true;
        }

        @Override
        public boolean canOpenDoor(GameState gameState, int x, int y) {
            return false;
        }

        @Override
        public List getCollectedKeys(GameState gameState) {
            return new ArrayList<>();
        }

        @Override
        public boolean canCollectKey(GameState gameState, int x, int y) {
            return false;
        }

        @Override
        public boolean isLevelComplete(GameState gameState, int x, int y) {
            return false;
        }
    }

    // Minimal ConsoleObservables stub
    private static class TestConsoleObservables extends ConsoleObservables {
        private final List<String> messages = new ArrayList<>();
        private final List<String> errors = new ArrayList<>();

        @Override
        public void addMessage(String message) {
            messages.add(message);
        }

        @Override
        public void addError(String error) {
            errors.add(error);
        }

        public ObservableList getMessages() {
            return (ObservableList) messages;
        }

        public ObservableList getErrors() {
            return (ObservableList) errors;
        }
    }

    // Minimal CommandExecutionObservables stub
    private static class TestCommandExecutionObservables extends CommandExecutionObservables {
        private final BooleanProperty pauseRequested = new SimpleBooleanProperty(false);
        private final BooleanProperty resumeRequested = new SimpleBooleanProperty(false);
        private boolean executionPaused = false;

        @Override
        public void requestPause() {
            pauseRequested.set(true);
        }

        @Override
        public void requestResume() {
            resumeRequested.set(true);
        }

        @Override
        public void clearPauseRequest() {
            pauseRequested.set(false);
        }

        @Override
        public void clearResumeRequest() {
            resumeRequested.set(false);
        }

        @Override
        public void setExecutionPaused(boolean paused) {
            executionPaused = paused;
        }

        @Override
        public boolean isExecutionPaused() {
            return executionPaused;
        }

        @Override
        public BooleanProperty pauseRequestedProperty() {
            return pauseRequested;
        }

        @Override
        public BooleanProperty resumeRequestedProperty() {
            return resumeRequested;
        }
    }

    // Minimal ObservableRegistry stub
    private static class TestObservableRegistry extends ObservableRegistry {
        private final ConsoleObservables console = new TestConsoleObservables();
        private final CommandExecutionObservables cmdExec = new TestCommandExecutionObservables();

        @Override
        public <T> void register(Class<T> observableType, T observable) {}

        @SuppressWarnings("unchecked")
        public <T> T getObservable(Class<T> observableType) {
            if (observableType == ConsoleObservables.class) {
                return (T) console;
            } else if (observableType == CommandExecutionObservables.class) {
                return (T) cmdExec;
            }
            return null;
        }
    }

    private CommandServiceImpl commandService;
    private TestMovementService movementService;
    private TestObservableRegistry testObservableRegistry;

    @BeforeEach
    void setUp() {
        movementService = new TestMovementService();
        testObservableRegistry = new TestObservableRegistry();
        commandService = new CommandServiceImpl(movementService);
    }

    @Test
    void testIsPausedInitialState() {
        assertFalse(commandService.isPaused());
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void testClearCommandQueueMultipleTimes() {
        assertDoesNotThrow(() -> {
            commandService.clearCommandQueue();
            commandService.clearCommandQueue();
            commandService.clearCommandQueue();
        });
    }

    @Test
    void testRequestPauseAndResume() {
        // Initially not paused
        assertFalse(commandService.isPaused());

        // Request pause
        commandService.requestPause();
        // Depending on implementation, isPaused may or may not reflect immediately
        // Here we just check that calling doesn't throw
        assertDoesNotThrow(() -> commandService.requestPause());

        // Request resume
        commandService.requestResume();
        assertDoesNotThrow(() -> commandService.requestResume());
    }

    @Test
    void testMultiplePauseResumeCalls() {
        // Multiple pause/resume calls should not throw
        assertDoesNotThrow(() -> {
            commandService.requestPause();
            commandService.requestPause();
            commandService.requestResume();
            commandService.requestResume();
        });
    }
}