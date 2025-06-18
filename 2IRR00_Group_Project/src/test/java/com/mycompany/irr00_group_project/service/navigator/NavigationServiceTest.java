package com.mycompany.irr00_group_project.service.navigator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NavigationServiceTest {

    private NavigationService navigationService;
    
    /**
     *  Sets up the NavigationService instance before each test.
     */
    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = NavigationService.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        navigationService = NavigationService.getInstance();
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        NavigationService instance1 = NavigationService.getInstance();
        NavigationService instance2 = NavigationService.getInstance();
        assertSame(instance1, instance2, "Should return the same singleton instance");
    }

    @Test
    void testGetObservableRegistryReturnsSameInstance() {
        assertSame(navigationService.getObservableRegistry(), 
            navigationService.getObservableRegistry(),
            "getObservableRegistry should always return the same instance");
    }

    @Test
    void testNotifyReturnedToGameDoesNotThrowWhenObservablePresent() {
        assertDoesNotThrow(() -> navigationService.notifyReturnedToGame(),
            "notifyReturnedToGame should not throw when NavigationObservables is registered");
    }

    @Test
    void testSingletonInstanceIsThreadSafe() throws Exception {
        NavigationService[] instances = new NavigationService[2];
        Thread t1 = new Thread(() -> instances[0] = NavigationService.getInstance());
        Thread t2 = new Thread(() -> instances[1] = NavigationService.getInstance());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertSame(instances[0], instances[1],
            "Both threads should get the same singleton instance");
    }
}