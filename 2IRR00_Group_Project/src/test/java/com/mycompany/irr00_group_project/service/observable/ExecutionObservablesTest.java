package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionObservablesTest {

    private ExecutionObservables executionObservables;

    @BeforeEach
    void setUp() {
        executionObservables = new ExecutionObservables();
    }

    @Test
    void testSetExecutionCompletedUpdatesProperties() {
        String message = "Execution completed successfully";
        executionObservables.setExecutionCompleted(message);

        BooleanProperty executionCompleted = executionObservables.executionCompletedProperty();
        StringProperty completionMessage = executionObservables.completionMessageProperty();
        assertTrue(executionCompleted.get(), "ExecutionCompleted property should be true");
        assertEquals(message, completionMessage.get(),
            "CompletionMessage property should match set message");
    }

    @Test
    void testSetExecutionStartedUpdatesProperties() {
        executionObservables.setExecutionStarted();

        BooleanProperty executionStarted = executionObservables.executionStartedProperty();
        BooleanProperty executionCompleted = executionObservables.executionCompletedProperty();
        assertTrue(executionStarted.get(), "ExecutionStarted property should be true");
        assertFalse(executionCompleted.get(), "ExecutionCompleted property should be false");
    }

    @Test
    void testResetFlagsClearsProperties() {
        executionObservables.setExecutionCompleted("Test message");
        executionObservables.setExecutionStarted();
        executionObservables.resetFlags();

        BooleanProperty executionCompleted = executionObservables.executionCompletedProperty();
        BooleanProperty executionStarted = executionObservables.executionStartedProperty();
        StringProperty completionMessage = executionObservables.completionMessageProperty();
        assertFalse(executionCompleted.get(),
            "ExecutionCompleted should be false after reset");
        assertFalse(executionStarted.get(),
            "ExecutionStarted should be false after reset");
        assertEquals("", completionMessage.get(),
            "CompletionMessage should be empty after reset");
    }

    @Test
    void testInitialState() {
        assertFalse(executionObservables.executionCompletedProperty().get(),
            "Initial ExecutionCompleted should be false");
        assertFalse(executionObservables.executionStartedProperty().get(),
            "Initial ExecutionStarted should be false");
        assertEquals("",
            executionObservables.completionMessageProperty().get(),
            "Initial CompletionMessage should be empty");
    }
}