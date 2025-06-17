package com.mycompany.irr00_group_project.controller;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.navigator.WinScreenNavigatorManager;

import javafx.event.ActionEvent;

/**
 * JUnit test class for WinScreenController.
 */

public class WinScreenControllerTest {

    private WinScreenController controller;
    private DummyNavigatorManager dummyNavigatorManager;
    
    // Dummy implementation to track method calls
    static class DummyNavigatorManager extends WinScreenNavigatorManager {
        boolean levelSelectionCalled = false;
        boolean nextLevelCalled = false;
        boolean sameGameScreenCalled = false;
        boolean restartCalled = false;

        @Override
        public void navigateToLevelSelection() {
            levelSelectionCalled = true;
        }

        @Override
        public void navigateToNextLevel(LevelDTO nextLevel) {
            // Ensure nextLevel is not null to avoid NullPointerException in test
            if (nextLevel != null) {
                nextLevelCalled = true;
            } else {
                throw new IllegalArgumentException("nextLevel is null");
            }
        }

        @Override
        public void navigateBack() {
            restartCalled = true;
        }
    }

    /**
     * Sets up the WinScreenController and injects a dummy navigator manager before each test.
     */
    @BeforeEach
    void setUp() throws Exception {
        controller = new WinScreenController();
        dummyNavigatorManager = new DummyNavigatorManager();
        LevelDTO dummyLevel = new LevelDTO(1, 3, false); // 3 stars for testing


        // Inject dummyNavigatorManager using reflection
        Field navField = WinScreenController.class.getDeclaredField("navigatorManager");
        navField.setAccessible(true);
        navField.set(controller, dummyNavigatorManager);
    }

    @Test
    void testInitialize() {
        try {
            // Call the initialize method
            controller.initialize();

            // Verify that the navigatorManager is not null
            Field field = WinScreenController.class.getDeclaredField("navigatorManager");
            field.setAccessible(true);
            WinScreenNavigatorManager navigatorManager = 
                (WinScreenNavigatorManager) field.get(controller);
            assertTrue(navigatorManager != null, "Expected navigatorManager to be initialized");
        } catch (Exception e) {
            fail("Initialization failed: " + e.getMessage());
        }
    }

    @Test
    void testHandleLevelSelectionButtonAction() {
        try {
            // Create a dummy ActionEvent
            ActionEvent event = new ActionEvent();

            // Call the method to test
            controller.handleLevelSelectionButtonAction(event);

            // Verify that the navigateToLevelSelection method was called
            assertTrue(dummyNavigatorManager.levelSelectionCalled, 
                "Expected navigateToLevelSelection to be called");
        } catch (Exception e) {
            fail("handleLevelSelectionButton failed: " + e.getMessage());
        }
    }

    @Test
    void testHandleRestartButtonAction() {
        try {
            // Create a dummy ActionEvent
            ActionEvent event = new ActionEvent();

            // Call the method to test
            controller.handleRestartButtonAction(event);

            // Verify that the restart action is handled correctly
            // This would typically involve checking if the level is restarted
            // For this test, we assume it just calls the next level navigation
            assertTrue(dummyNavigatorManager.restartCalled, 
                "Expected navigateBack to be called on restart");
        } catch (Exception e) {
            fail("handleRestartButtonAction failed: " + e.getMessage());
        }
    }
}