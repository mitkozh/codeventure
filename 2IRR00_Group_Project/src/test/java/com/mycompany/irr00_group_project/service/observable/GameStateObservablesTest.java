package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateObservablesTest {

    private GameStateObservables gameStateObservables;

    @BeforeEach
    void setUp() {
        gameStateObservables = new GameStateObservables();
    }

    @Test
    void testSetGridForUpdateSetsProperty() {
        gameStateObservables.setGridForUpdate();

        BooleanProperty gridNeedsUpdate = gameStateObservables.gridNeedsUpdateProperty();
        assertTrue(gridNeedsUpdate.get(), "GridNeedsUpdate property should be true");
    }

    @Test
    void testClearGridUpdateFlagResetsProperty() {
        gameStateObservables.setGridForUpdate();
        gameStateObservables.clearGridUpdateFlag();

        BooleanProperty gridNeedsUpdate = gameStateObservables.gridNeedsUpdateProperty();
        assertFalse(gridNeedsUpdate.get(), "GridNeedsUpdate property should be false after clear");
    }

    @Test
    void testSetLevelWonUpdatesProperty() {
        gameStateObservables.setLevelWon(true);

        BooleanProperty levelWon = gameStateObservables.levelWonProperty();
        assertTrue(levelWon.get(), "LevelWon property should be true");
    }

    @Test
    void testSetLevelLostUpdatesProperty() {
        gameStateObservables.setLevelLost(true);

        BooleanProperty levelLost = gameStateObservables.levelLostProperty();
        assertTrue(levelLost.get(), "LevelLost property should be true");
    }

    @Test
    void testSetPlayerStepsUpdatesProperty() {
        int steps = 42;
        gameStateObservables.setPlayerSteps(steps);

        IntegerProperty playerSteps = gameStateObservables.playerStepsProperty();
        assertEquals(steps, playerSteps.get(),
            "PlayerSteps property should match set value");
    }

    @Test
    void testResetGameFlagsResetsProperties() {
        gameStateObservables.setGridForUpdate();
        gameStateObservables.setLevelWon(true);
        gameStateObservables.setLevelLost(true);
        gameStateObservables.setPlayerSteps(42);
        gameStateObservables.resetGameFlags();

        assertFalse(gameStateObservables.gridNeedsUpdateProperty().get(),
            "GridNeedsUpdate should be false after reset");
        assertFalse(gameStateObservables.levelWonProperty().get(),
            "LevelWon should be false after reset");
        assertFalse(gameStateObservables.levelLostProperty().get(),
            "LevelLost should be false after reset");
        assertEquals(42, gameStateObservables.playerStepsProperty().get(),
            "PlayerSteps should not be reset");
    }

    @Test
    void testInitialState() {
        assertFalse(gameStateObservables.gridNeedsUpdateProperty().get(),
            "Initial GridNeedsUpdate should be false");
        assertFalse(gameStateObservables.levelWonProperty().get(),
            "Initial LevelWon should be false");
        assertFalse(gameStateObservables.levelLostProperty().get(),
            "Initial LevelLost should be false");
        assertEquals(0, gameStateObservables.playerStepsProperty().get(),
            "Initial PlayerSteps should be 0");
    }
}