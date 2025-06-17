package com.mycompany.irr00_group_project.service.observable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ObservableRegistryTest {

    private ObservableRegistry observableRegistry;

    @BeforeEach
    void setUp() {
        observableRegistry = new ObservableRegistry();
    }

    @Test
    void testRegisterAndGetObservable() {
        ConsoleObservables consoleObservables = new ConsoleObservables();
        observableRegistry.register(ConsoleObservables.class, consoleObservables);

        Optional<ConsoleObservables> result = observableRegistry.get(ConsoleObservables.class);
        assertTrue(result.isPresent(), "Observable should be present");
        assertEquals(consoleObservables, result.get(),
            "Retrieved observable should match registered instance");
    }

    @Test
    void testGetNonExistentObservableReturnsEmpty() {
        Optional<ConsoleObservables> result = observableRegistry.get(ConsoleObservables.class);
        assertFalse(result.isPresent(), "Non-registered observable should return empty Optional");
    }

    @Test
    void testGetOrThrowReturnsRegisteredObservable() {
        ConsoleObservables consoleObservables = new ConsoleObservables();
        observableRegistry.register(ConsoleObservables.class, consoleObservables);

        ConsoleObservables result = observableRegistry.getOrThrow(ConsoleObservables.class);
        assertEquals(consoleObservables, result,
            "getOrThrow should return registered instance");
    }

    @Test
    void testGetOrThrowThrowsExceptionForNonExistent() {
        assertThrows(IllegalStateException.class,
            () -> observableRegistry.getOrThrow(ConsoleObservables.class),
            "getOrThrow should throw IllegalStateException"
            + " for non-registered observable");
    }

    @Test
    void testHasObservableReturnsCorrectStatus() {
        assertFalse(observableRegistry.has(ConsoleObservables.class),
            "has should return false for non-registered observable");

        ConsoleObservables consoleObservables = new ConsoleObservables();
        observableRegistry.register(ConsoleObservables.class, consoleObservables);
        assertTrue(observableRegistry.has(ConsoleObservables.class),
            "has should return true for registered observable");
    }

    @Test
    void testClearRemovesAllObservables() {
        ConsoleObservables consoleObservables = new ConsoleObservables();
        NavigationObservables navigationObservables = new NavigationObservables();
        observableRegistry.register(ConsoleObservables.class, consoleObservables);
        observableRegistry.register(NavigationObservables.class, navigationObservables);

        observableRegistry.clear();

        assertFalse(observableRegistry.has(ConsoleObservables.class),
            "Registry should not contain ConsoleObservables after clear");
        assertFalse(observableRegistry.has(NavigationObservables.class),
            "Registry should not contain NavigationObservables after clear");
    }
}