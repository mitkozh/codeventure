package com.mycompany.irr00_group_project.controller;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.service.navigator.HelpScreenNavigatorManager;

import javafx.event.ActionEvent;

/**
 * JUnit test class for HelpScreenController.
 * This class tests the functionality of the HelpScreenController,
 * focusing on the navigation methods and ensuring that the correct
 * methods in the navigator manager are called.
 */
public class HelpScreenControllerTest {

    private HelpScreenController controller;
    private DummyNavigatorManager dummyNavigatorManager;

    // Dummy implementation to track method calls
    static class DummyNavigatorManager extends HelpScreenNavigatorManager {
        boolean backCalled = false;

        @Override
        public void navigateBackAndNotify() {
            backCalled = true;
        }
    }

    /**
     * Sets up the HelpScreenController and injects a dummy navigator manager before each test.
     */
    @BeforeEach
    void setUp() throws Exception {
        controller = new HelpScreenController();
        dummyNavigatorManager = new DummyNavigatorManager();
        // Inject dummyNavigatorManager using reflection
        Field field = HelpScreenController.class.getDeclaredField("navigatorManager");
        field.setAccessible(true);
        field.set(controller, dummyNavigatorManager);
    }

    @Test
    void testInitialize() {
        try {
            // Call the initialize method
            controller.initialize();

            // Verify that the navigatorManager is not null
            Field field = HelpScreenController.class.getDeclaredField("navigatorManager");
            field.setAccessible(true);
            HelpScreenNavigatorManager navigatorManager = 
                (HelpScreenNavigatorManager) field.get(controller);
            assertTrue(navigatorManager != null, "Expected navigatorManager to be initialized");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Initialization failed: " + e.getMessage());
        }
    }

    @Test
    void testBackToMenu() {
        // Create a dummy ActionEvent
        ActionEvent actionEvent = new ActionEvent();

        // Call the backToMenu method
        controller.backToMenu(actionEvent);

        // Verify that the navigateBackAndNotify method was called
        assertTrue(dummyNavigatorManager.backCalled, "Expected navigateBackAndNotify to be called");
    }

}
