package com.mycompany.irr00_group_project.service.observable;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * GameStateObservables provides observable properties for the game state,
 * It allows other components to observe and react to changes in the game state.
 */
public class GameStateObservables {
    private final BooleanProperty gridNeedsUpdate = new SimpleBooleanProperty(false);
    private final BooleanProperty levelWon = new SimpleBooleanProperty(false);
    private final BooleanProperty levelLost = new SimpleBooleanProperty(false);
    private final IntegerProperty playerSteps = new SimpleIntegerProperty(0);

    public BooleanProperty gridNeedsUpdateProperty() {
        return gridNeedsUpdate;
    }

    public BooleanProperty levelWonProperty() {
        return levelWon;
    }

    public BooleanProperty levelLostProperty() {
        return levelLost;
    }

    public IntegerProperty playerStepsProperty() {
        return playerSteps;
    }

    public void setGridForUpdate() {
        gridNeedsUpdate.set(true);
    }

    public void clearGridUpdateFlag() {
        gridNeedsUpdate.set(false);
    }

    public void setLevelWon(boolean won) {
        levelWon.set(won);
    }

    public void setLevelLost(boolean lost) {
        levelLost.set(lost);
    }

    public void setPlayerSteps(int steps) {
        playerSteps.set(steps);
    }

    /**
     * Resets the game state flags to their initial values.
     */
    public void resetGameFlags() {
        levelWon.set(false);
        levelLost.set(false);
        gridNeedsUpdate.set(false);
    }
}