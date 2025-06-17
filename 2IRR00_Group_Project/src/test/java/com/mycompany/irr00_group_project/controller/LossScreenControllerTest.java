package com.mycompany.irr00_group_project.controller;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.service.navigator.LossScreenNavigatorManager;

import javafx.event.ActionEvent;

/**
 * JUnit test class for LossScreenController.
 */
public class LossScreenControllerTest {
    private LossScreenController controller;
    private DummyNavigatorManager dummyNavigatorManager;

    //Dummy implementation to track method calls
    static class DummyNavigatorManager extends LossScreenNavigatorManager {
        boolean levelSelectionCalled = false;
        boolean backCalled = false;

        @Override
        public void navigateToLevelSelection() {
            levelSelectionCalled = true;
        }

        @Override
        public void navigateBack() {
            backCalled = true;
        }
    }

    /**
     * Sets up the LossScreenController and injects a dummy navigator manager before each test.
     */
    @BeforeEach
    void setUp() throws Exception {
        controller = new LossScreenController();
        dummyNavigatorManager = new DummyNavigatorManager();
        // Inject dummyNavigatorManager using reflection
        Field field = LossScreenController.class.getDeclaredField("navigatorManager");
        field.setAccessible(true);
        field.set(controller, dummyNavigatorManager);
    }

    @Test
    void testInitialize() {
        try {
            // Call the initialize method
            controller.initialize();

            // Verify that the navigatorManager is not null
            Field field = LossScreenController.class.getDeclaredField("navigatorManager");
            field.setAccessible(true);
            LossScreenNavigatorManager navigatorManager = 
                (LossScreenNavigatorManager) field.get(controller);
            assertTrue(navigatorManager != null, "Expected navigatorManager to be initialized");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Initialization failed: " + e.getMessage());
        }
    }

    @Test
    void testHandleLevelSelectionButton() {
        try {
            // Create a dummy ActionEvent
            ActionEvent event = new ActionEvent();

            // Call the method to test
            controller.handleLevelSelectionButton(event);

            // Verify that the navigateToLevelSelection method was called
            assertTrue(dummyNavigatorManager.levelSelectionCalled, 
                "Expected navigateToLevelSelection to be called");
        } catch (Exception e) {
            fail("handleLevelSelectionButton failed: " + e.getMessage());
        }
    }

    @Test
    void testHandleGoBack() {
        try {
            // Create a dummy ActionEvent
            ActionEvent event = new ActionEvent();

            // Call the method to test
            controller.handleGoBack(event);

            // Verify that the navigateBack method was called
            assertTrue(dummyNavigatorManager.backCalled, "Expected navigateBack to be called");
        } catch (Exception e) {
            fail("handleGoBack failed: " + e.getMessage());
        }
    }
}
