package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ExecutionObservables provides observable properties for code execution
 * events.
 * It allows other components to observe and react to changes in execution
 * state.
 */
public class ExecutionObservables {
    private final BooleanProperty executionCompleted = new SimpleBooleanProperty(false);
    private final StringProperty completionMessage = new SimpleStringProperty("");
    private final BooleanProperty executionStarted = new SimpleBooleanProperty(false);

    public BooleanProperty executionCompletedProperty() {
        return executionCompleted;
    }

    public StringProperty completionMessageProperty() {
        return completionMessage;
    }

    public BooleanProperty executionStartedProperty() {
        return executionStarted;
    }

    /**
     * Sets the execution as completed with a message.
     *
     * @param message The message to set upon completion.
     */
    public void setExecutionCompleted(String message) {
        completionMessage.set(message);
        executionCompleted.set(true);
    }

    /**
     * Sets the execution as completed without a message.
     */
    public void setExecutionStarted() {
        executionStarted.set(true);
        executionCompleted.set(false);
    }

    /**
     * Sets the execution as not completed.
     */
    public void resetFlags() {
        executionCompleted.set(false);
        executionStarted.set(false);
        completionMessage.set("");
    }
}