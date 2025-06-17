package com.mycompany.irr00_group_project.controller;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.service.navigator.MainMenuScreenNavigatorManager;

import javafx.event.ActionEvent;

/**
 * JUnit test class for MainMenuController.
 * This class tests the functionality of the MainMenuController,
 * focusing on the navigation methods
 * and ensuring that the correct methods in the navigator manager are called.
 */
public class MainMenuControllerTest {

    private MainMenuController controller;
    private DummyNavigatorManager dummyNavigatorManager;

    // Dummy implementation to track method calls
    static class DummyNavigatorManager extends MainMenuScreenNavigatorManager {
        boolean levelSelectionCalled = false;
        boolean settingsCalled = false;
        boolean helpCalled = false;

        @Override
        public void navigateToLevelSelection() {
            levelSelectionCalled = true;
        }

        @Override
        public void navigateToSettings() {
            settingsCalled = true;
        }

        @Override
        public void navigateToHelp() {
            helpCalled = true;
        }
    }

    /**
     * Sets up the MainMenuController and injects a dummy navigator manager before each test.
     */
    @BeforeEach
    void setUp() throws Exception {
        controller = new MainMenuController();
        dummyNavigatorManager = new DummyNavigatorManager();
        // Inject dummyNavigatorManager using reflection
        Field field = MainMenuController.class.getDeclaredField("navigatorManager");
        field.setAccessible(true);
        field.set(controller, dummyNavigatorManager);
    }

    @Test
    void testOnLevelSelectClick() {
        controller.onLevelSelectClick(new ActionEvent());
        assertTrue(dummyNavigatorManager.levelSelectionCalled, 
            "navigateToLevelSelection should be called");
    }

    @Test
    void testOnSettingsClick() {
        controller.onSettingsClick(new ActionEvent());
        assertTrue(dummyNavigatorManager.settingsCalled, 
            "navigateToSettings should be called");
    }

    @Test
    void testOnHelpClick() {
        controller.onHelpClick(new ActionEvent());
        assertTrue(dummyNavigatorManager.helpCalled, 
            "navigateToHelp should be called");
    }

    @Test
    void testOnExitGameClickDoesNotThrow() {
        // Platform.exit() will try to exit the JVM, 
        // so we just check that the method can be called without exception.
        try {
            controller.onExitGameClick(new ActionEvent());
        } catch (Exception e) {
            fail("onExitGameClick should not throw an exception");
        }
    }
}
