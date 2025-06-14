package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * CommandExecutionObservables is used to manage the state of command execution in the game.
 */
public class CommandExecutionObservables {
    private final BooleanProperty executionPaused = new SimpleBooleanProperty(false);
    private final BooleanProperty pauseRequested = new SimpleBooleanProperty(false);
    private final BooleanProperty resumeRequested = new SimpleBooleanProperty(false);

    public BooleanProperty executionPausedProperty() {
        return executionPaused;
    }

    public BooleanProperty pauseRequestedProperty() {
        return pauseRequested;
    }

    public BooleanProperty resumeRequestedProperty() {
        return resumeRequested;
    }

    public void requestPause() {
        pauseRequested.set(true);
    }

    public void requestResume() {
        resumeRequested.set(true);
    }

    public void setExecutionPaused(boolean paused) {
        executionPaused.set(paused);
    }

    public boolean isExecutionPaused() {
        return executionPaused.get();
    }

    public void clearPauseRequest() {
        pauseRequested.set(false);
    }

    public void clearResumeRequest() {
        resumeRequested.set(false);
    }
}