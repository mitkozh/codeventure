package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NavigationObservablesTest {

    private NavigationObservables navigationObservables;

    @BeforeEach
    void setUp() {
        navigationObservables = new NavigationObservables();
    }

    @Test
    void testSetReturnedToGameUpdatesProperty() {
        navigationObservables.setReturnedToGame(true);

        BooleanProperty returnedToGame = navigationObservables.returnedToGameProperty();
        assertTrue(returnedToGame.get(), "ReturnedToGame property should be true");
    }

    @Test
    void testClearReturnedToGameResetsProperty() {
        navigationObservables.setReturnedToGame(true);
        navigationObservables.clearReturnedToGame();

        BooleanProperty returnedToGame = navigationObservables.returnedToGameProperty();
        assertFalse(returnedToGame.get(),
            "ReturnedToGame property should be false after clear");
    }

    @Test
    void testSetLastNavigatedToUpdatesProperty() {
        String destination = "TestScreen";
        navigationObservables.setLastNavigatedTo(destination);

        StringProperty lastNavigatedTo = navigationObservables.lastNavigatedToProperty();
        assertEquals(destination, lastNavigatedTo.get(),
            "LastNavigatedTo property should match the set destination");
    }

    @Test
    void testInitialState() {
        assertFalse(navigationObservables.returnedToGameProperty().get(),
            "Initial ReturnedToGame should be false");
        assertEquals("",
            navigationObservables.lastNavigatedToProperty().get(),
            "Initial LastNavigatedTo should be empty");
    }
}